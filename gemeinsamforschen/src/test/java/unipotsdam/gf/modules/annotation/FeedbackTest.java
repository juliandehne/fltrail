package unipotsdam.gf.modules.annotation;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.modules.feedback.Category;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.submission.model.FullSubmission;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.process.tasks.FeedbackTaskData;
import unipotsdam.gf.process.tasks.TaskDAO;

import javax.inject.Inject;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FeedbackTest {

    @Inject
    TaskDAO taskDAO;

    @Before
    public void setUp() {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

    }

    @Test
    public void testGivingFeedbackTask() {
        User user = new User("vodkas@yolo.com");
        Project project = new Project("test3");
        FullSubmission fullSubmission = new FullSubmission("476000b6-1a6c-402e-abf5-f4a32d12dfa0");
        FeedbackTaskData feedbackTaskData = new FeedbackTaskData(user, fullSubmission, Category.RECHERCHE);

        taskDAO.persistFeedbackTask(project, feedbackTaskData);

    }
}
