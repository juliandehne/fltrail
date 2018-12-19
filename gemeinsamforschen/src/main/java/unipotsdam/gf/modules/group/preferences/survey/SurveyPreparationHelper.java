package unipotsdam.gf.modules.group.preferences.survey;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.database.ProfileQuestion;
import unipotsdam.gf.modules.group.preferences.excel.ItemWriter;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SurveyPreparationHelper {

    @Inject
    ProfileDAO profileDAO;

    @Inject
    UserDAO userDAO;

    @Inject
    ProjectDAO projectDAO;

    public SurveyPreparationHelper() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);
    }

    public void prepareSurvey() throws Exception {

        // create dehne user
        try {
            User dehne = new User("julian.dehne@uni-potsdam.de");
            dehne.setPassword("egal");
            dehne.setRocketChatUsername("fltrailadmin");
            dehne.setStudent(false);
            dehne.setName("Julian Dehne");
            userDAO.persist(dehne, null);
        } catch (Exception e) {
        }

        // importing items
        String itemExamle = "groupfindingitems_beispiel.xls";
        ItemWriter itemWriter = new ItemWriter(itemExamle);
        itemWriter.writeItems();

        // creating survey projects
        ArrayList<Project> surveyProjects = new ArrayList<Project>();
        Project d1_test = new Project("d1_test");
        d1_test.setSurvey(true);

        Project o1_test = new Project("o1_test");
        o1_test.setSurvey(true);

        Project fl_test = new Project("fl_test");
        fl_test.setSurvey(true);

        surveyProjects.add(o1_test);
        surveyProjects.add(d1_test);

        for (Project surveyProject : surveyProjects) {
            projectDAO.persist(surveyProject);
        }

        // the persisted questions from the excel sheet (ITEMS for FL, based on FideS Team research)
        List<ProfileQuestion> questions = profileDAO.getQuestions();

        // todo find out mathematically if that works, how many iterations are needed and
        persistselectedItems(d1_test, questions);
        persistselectedItems(o1_test, questions);
        persistselectedItems(fl_test, questions);

    }

    private void persistselectedItems(Project someTest, List<ProfileQuestion> questions) {
        //Collections.shuffle(questions);
        //int toRemove = questions.size() - 30;
        //List<ProfileQuestion> q2 = questions.subList(questions.size() - toRemove, questions.size());
        profileDAO.addItemsToProject(someTest, questions);
    }
}
