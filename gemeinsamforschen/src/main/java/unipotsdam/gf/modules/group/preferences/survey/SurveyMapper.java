package unipotsdam.gf.modules.group.preferences.survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import unipotsdam.gf.config.GroupAlConfig;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.exceptions.WrongNumberOfParticipantsException;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.modules.communication.view.CommunicationView;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.database.ProfileQuestion;
import unipotsdam.gf.modules.group.preferences.groupal.GroupAlMatcher;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.user.UserProfile;
import unipotsdam.gf.process.phases.Phase;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SurveyMapper {

    public static final String NICKNAME1 = "NICKNAME1";
    private static final String NICKNAME2 = "NICKNAME2";
    public static final String EMAIL1 = "EMAIL1";
    private static final String EMAIL2 = "EMAIL2";
    private static final String DISCORDID = "DISCORDID";

    private Boolean isdebug = true;

    @Inject
    private ProfileDAO profileDAO;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private IPhases phases;

    @Inject
    private UserDAO userDAO;

    @Inject
    private Management management;

    @Inject
    private GroupAlMatcher groupAlMatcher;

    @Inject
    private GroupDAO groupDAO;

    private static final Logger log = LoggerFactory.getLogger(CommunicationView.class);

    /**
     * generate the backing data for the survey
     *
     * @param groupWorkContext
     * @param standalone       is false if run within fltrail general project
     * @return
     */
    public SurveyData getItemsFromDB(GroupWorkContext groupWorkContext, Boolean standalone, Project project)
            throws Exception {
        SurveyData surveyData = new SurveyData(); // the result obj

        // the persisted questions from the excel sheet (ITEMS for FL, based on FideS Team research)
        HashMap<Project, List<ProfileQuestion>> questionMap = profileDAO.getSelectedQuestions();
        List<ProfileQuestion> questions = questionMap.get(project);
        if (questions == null) {
            throw new Exception("items are not available in DB");
        }
        if (standalone) {
            // the general questions to create a profile (given that we are not running the survey as part of the normal
            // FL-Trail Mode
            LocalizedText nickname1 = new LocalizedText("Choose a nickname!", "Wählen Sie einen Nickname aus!");

            LocalizedText nickname2 = new LocalizedText("Repeat your nickname!", "Wiederholen Sie den Nickname!");

            LocalizedText email1 =
                    new LocalizedText("Enter a valid email!", "Geben Sie eine valide Emailadresse " + "ein!");
            LocalizedText email2 = new LocalizedText("Repeat your email!", "Wiederholen Sie die Emailadresse");


            // adapt title
            surveyData.setTitle(new LocalizedText(
                    "Thank you for participating in this survey about good group formation",
                    "Vielen Dank für ihre Bereitschaft zur Teilnahme an dieser Befragung"));

            Page generalDetails = new Page();
            generalDetails.setName("general1");
            addGeneralQuestion(NICKNAME1, nickname1, generalDetails);
            addGeneralQuestion(NICKNAME2, nickname2, generalDetails);
            addGeneralQuestion(EMAIL1, email1, generalDetails);
            addGeneralQuestion(EMAIL2, email2, generalDetails);

            String discordIdString = "(optional) Enter your discord ID!";
            switch (groupWorkContext) {
                case fl:
                    break;
                case dota:
                case overwatch:
                    LocalizedText discordQuestion =
                            new LocalizedText("" + discordIdString, "(optional) Geben Sie ihre Discord ID ein!");
                    addGeneralQuestion(DISCORDID, discordQuestion, generalDetails);
                    break;
            }
            surveyData.getPages().add(generalDetails);

            int i = 0;
            Page profileQuestionsPage = new Page();
            profileQuestionsPage.setName("page" + i);
            for (ProfileQuestion question : questions) {
                // just those things
                i++;
                if (i == 5) {
                    i = 0;
                    surveyData.getPages().add(profileQuestionsPage);
                    if (isdebug) {
                        return surveyData;
                    }
                    profileQuestionsPage = new Page();
                    profileQuestionsPage.setName("page" + i);
                }
                profileQuestionsPage.getQuestions().add(convertQuestion(question));
            }
            if (!profileQuestionsPage.getQuestions().isEmpty()) {
                surveyData.getPages().add(profileQuestionsPage);
            }
        }
        return surveyData;
    }

    private void addGeneralQuestion(String name, LocalizedText nickname1, Page generalDetails) {
        LocalizedText generalTextQuestion = nickname1;
        OpenQuestion openQuestion = new OpenQuestion();
        openQuestion.setName(name);
        openQuestion.setTitle(generalTextQuestion);
        generalDetails.getQuestions().add(openQuestion);
    }

    public static ScaledQuestion convertQuestion(ProfileQuestion question) {
        ScaledQuestion scaledQuestion = new ScaledQuestion();
        scaledQuestion.setName(question.getId() + "");
        scaledQuestion.setTitle(new LocalizedText(question.getQuestion_en(), question.getQuestion()));
        return scaledQuestion;
    }

    public void saveData(HashMap<String, String> data, String projectId, HttpServletRequest req)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, WrongNumberOfParticipantsException, JAXBException {
        log.trace("persisting survey data");
        User user;
        // it is test context
        if (req == null){
            user = createUserFromSurvey(data);
            // it is in survey context
        } else if (req.getAttribute("userEmail") == null) {
            req.getSession().setAttribute("userEmail", data.get(EMAIL1));
            user = createUserFromSurvey(data);
        }
        // it is in fl context
        else {
            user = userDAO.getUserByEmail(req.getAttribute("userEmail").toString());
        }
        // save
        management.register(user, new Project(projectId), null);

        UserProfile userProfile = new UserProfile(data, user, projectId);
        profileDAO.save(userProfile);

    }

    private User createUserFromSurvey(HashMap<String, String> data) {
        User user;
        String nickname = data.get(NICKNAME1);
        data.remove(NICKNAME1);
        String nickname2 = data.get(NICKNAME2);
        data.remove(NICKNAME2);

        String email = data.get(EMAIL1);
        data.remove(EMAIL1);
        String email2 = data.get(EMAIL2);
        data.remove(EMAIL2);

        String discord = data.get(DISCORDID);
        data.remove(DISCORDID);

        user = new User(email);
        user.setName(nickname);
        user.setDiscordid(discord);
        user.setPassword("egal");
        userDAO.persist(user);
        return user;
    }

    public String createNewProject(GroupWorkContext projectContext) {
        String randomId = UUID.randomUUID().toString();
        Project project = new Project(randomId);
        project.setGroupWorkContext(projectContext);
        projectDAO.persist(project);
        profileDAO.createNewSurveyProject(new Project(randomId));
        return randomId;
    }
}
