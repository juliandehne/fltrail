package unipotsdam.gf;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.config.GeneralConfig;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.database.ProfileQuestion;
import unipotsdam.gf.modules.group.preferences.excel.ItemWriter;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContextUtil;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import java.util.List;

class SurveyPreparationHelper {

    @Inject
    ProfileDAO profileDAO;

    @Inject
    UserDAO userDAO;

    @Inject
    ProjectDAO projectDAO;


    SurveyPreparationHelper() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
    }

    void prepareSurvey() throws Exception {


        // create dehne user
        try {
            User dehne = new User("julian.dehne@uni-potsdam.de");
            dehne.setPassword("egal");
            dehne.setRocketChatUsername("fltrailadmin");
            dehne.setStudent(false);
            dehne.setName("Julian Dehne");
            userDAO.persist(dehne);
        } catch (Exception ignored) {
        }

        // importing items
        //String itemExample = "groupfindingitems_beispiel.xls";
        //String itemExample = "groupfindingitems_selected.xls";

        String itemExample = GeneralConfig.GROUPFINDING_ITEM_FILE;
        ItemWriter itemWriter = new ItemWriter(itemExample);
        itemWriter.writeItems();

        GroupWorkContext[] values = GroupWorkContext.values();
        for (GroupWorkContext groupWorkContext : values) {

            if (GroupWorkContextUtil.isSurveyContext(groupWorkContext) && !GroupWorkContextUtil.isGamingOrAutomatedGroupFormation(groupWorkContext)) {

                // creating survey projects
                Project project = new Project(groupWorkContext.toString());
                project.setGroupWorkContext(groupWorkContext);
                project.setSurvey(true);
                projectDAO.persist(project);

                projectDAO.setGroupFormationMechanism(GroupFormationMechanism.UserProfilStrategy, project);
                // create project in db

                // the persisted questions from the excel sheet (ITEMS for FL, based on FideS Team research)
                List<ProfileQuestion> questions = profileDAO.getQuestions(groupWorkContext);

                persistSelectedItems(project, questions);
            }
        }
    }

    private void persistSelectedItems(Project someTest, List<ProfileQuestion> questions) {
        //Collections.shuffle(questions);
        //int toRemove = questions.size() - 30;
        //List<ProfileQuestion> q2 = questions.subList(questions.size() - toRemove, questions.size());
        profileDAO.addItemsToProject(someTest, questions);
    }
}
