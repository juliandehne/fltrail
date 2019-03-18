package unipotsdam.gf.modules.group.preferences.survey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.exceptions.RocketChatDownException;
import unipotsdam.gf.exceptions.UserDoesNotExistInRocketChatException;
import unipotsdam.gf.modules.communication.view.CommunicationView;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.database.ProfileQuestion;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.user.UserProfile;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.process.SurveyProcess;
import unipotsdam.gf.session.GFContexts;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SurveyMapper {

    public static final String NICKNAME1 = "NICKNAME1";
    /*private static final String NICKNAME2 = "NICKNAME2";*/
    public static final String EMAIL1 = "EMAIL1";
    private static final String EMAIL2 = "EMAIL2";
    private static final String DISCORDID = "DISCORDID";


    private Boolean isdebug = false;

    @Inject
    private ProfileDAO profileDAO;

    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private UserDAO userDAO;

    @Inject
    private Management management;

    @Inject
    private ProjectCreationProcess projectCreationProcess;

    @Inject
    private GFContexts gfContexts;


    private static final Logger log = LoggerFactory.getLogger(CommunicationView.class);

    /**
     * generate the backing data for the survey
     *
     * @param groupWorkContext
     * @return
     */
    public SurveyData getItemsFromDB(GroupWorkContext groupWorkContext, Project project) throws Exception {
        SurveyData surveyData = new SurveyData(); // the result obj
        // the persisted questions from the excel sheet (ITEMS for FL, based on FideS Team research)
        HashMap<Project, List<ProfileQuestion>> questionMap = profileDAO.getSelectedQuestions(groupWorkContext);
        List<ProfileQuestion> questions = questionMap.get(project);

        if (questions == null) {
            throw new Exception("items are not available in DB");
        }
        if (GroupWorkContextUtil.isSurveyContext(groupWorkContext)) {
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
            //addGeneralQuestion(NICKNAME2, nickname2, generalDetails);
            addGeneralQuestion(EMAIL1, email1, generalDetails);
            addGeneralQuestion(EMAIL2, email2, generalDetails);

            String discordIdString = "(optional) Enter your discord ID!";
            if (GroupWorkContextUtil.isGamingOrAutomatedGroupFormation(groupWorkContext)) {
                LocalizedText discordQuestion =
                        new LocalizedText("" + discordIdString, "(optional) Geben Sie ihre Discord ID ein!");
                addGeneralQuestion(DISCORDID, discordQuestion, generalDetails);
            }
            surveyData.getPages().add(generalDetails);
        }
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
        return surveyData;
    }

    private void addGeneralQuestion(String name, LocalizedText nickname1, Page generalDetails) {
        OpenQuestion openQuestion = new OpenQuestion();
        openQuestion.setName(name);
        openQuestion.setTitle(nickname1);
        generalDetails.getQuestions().add(openQuestion);
    }

    public static ScaledQuestion convertQuestion(ProfileQuestion question) {
        ScaledQuestion scaledQuestion = new ScaledQuestion();
        scaledQuestion.setName(question.getId() + "");
        scaledQuestion.setTitle(new LocalizedText(question.getQuestion_en(), question.getQuestion()));
        return scaledQuestion;
    }

/*    public void saveEvaluation(HashMap<String, String> data, String projectId, HttpServletRequest req) {
        User user = userDAO.getUserByEmail(req.getSession().getAttribute("userEmail").toString());
        UserProfile userProfile = new UserProfile(data, user, projectId);
        profileDAO.save(userProfile, GroupWorkContext.evaluation);
    }*/

    public void saveData(HashMap<String, String> data, Project project, HttpServletRequest req) throws Exception {
        log.trace("persisting survey data");

        if(project.getGroupWorkContext() == null) {
            throw new Exception("groupworkcontext is not set");
        }

        // save the user in db
        User user = saveUser(data, project, req);
        // clean the data
        cleanData(data);
        // persist the answers
        UserProfile userProfile = new UserProfile(data, user, project.getName());
        profileDAO.save(userProfile, null);

    }

    /**
     * add survey user to project and save his responses
     * @param data
     * @param project
     * @param req
     * @return
     * @throws RocketChatDownException
     * @throws UserDoesNotExistInRocketChatException
     * @throws IOException
     */
    private User saveUser(HashMap<String, String> data, Project project, HttpServletRequest req)
            throws RocketChatDownException, UserDoesNotExistInRocketChatException, IOException {

        User user;
        if (GroupWorkContextUtil.isSurveyContext(project.getGroupWorkContext())) {
            user = createUserFromSurvey(data);
            management.register(user, project, null);
            gfContexts.updateUserWithEmail(req, user);
        }
        // it is in fl context
        else {
            user = gfContexts.getUserFromSession(req);
            projectCreationProcess.updateProjCreaProcTasks(project, user);
        }
        return user;
    }

    private void cleanData(HashMap<String, String> data) {
        ArrayList<String> toRemove = new ArrayList<>();
        for (String key : data.keySet()) {
            if (!key.matches("^[0-9]*$")) {
                toRemove.add(key);
            }
        }
        for (String key : toRemove) {
            data.remove(key);
        }
    }

    private User createUserFromSurvey(HashMap<String, String> data) {
        User user;
        String nickname = data.get(NICKNAME1);
        data.remove(NICKNAME1);
        String email = data.get(EMAIL1);
        data.remove(EMAIL1);
        String discord = data.get(DISCORDID);
        data.remove(DISCORDID);

        user = new User(email);
        user.setName(nickname);
        user.setDiscordid(discord);
        user.setPassword("egal");
        userDAO.persist(user);
        return user;
    }

    /**
     * after groups have been formed for a certain project, a new internal project is created for the next cohort
     * using the link
     * It is looked up based on the context set
     *
     * @param projectContext
     * @return
     */
    public SurveyProject createNewProject(GroupWorkContext projectContext) {
        String randomId = UUID.randomUUID().toString();
        SurveyProject project = new SurveyProject(randomId, projectContext);
        project.setGroupWorkContext(projectContext);
        project.setSurvey(true);
        projectDAO.persist(project);
        projectDAO.setGroupFormationMechanism(GroupFormationMechanism.UserProfilStrategy, project);
        profileDAO.createNewSurveyProject(project);

        return project;
    }

    public GroupWorkContext getGroupWorkContext(Project project) {
        return profileDAO.getGroupWorkContext(project);
    }
}
