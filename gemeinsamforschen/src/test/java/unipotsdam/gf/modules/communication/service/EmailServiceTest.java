package unipotsdam.gf.modules.communication.service;

import ch.vorburger.exec.ManagedProcessException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.config.GFApplicationBinderFactory;
import unipotsdam.gf.config.GeneralConfig;
import unipotsdam.gf.modules.communication.Messages;
import unipotsdam.gf.modules.group.preferences.survey.GroupWorkContext;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;

import javax.inject.Inject;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmailServiceTest {

    @Inject
    private EmailService emailService;


    @Inject
    private ProjectDAO projectDAO;

    @Inject
    private UserDAO userDAO;


    @Inject
    private Management management;

    private Project project;

    PodamFactory factory = new PodamFactoryImpl();


    @BeforeClass
    public static void init() throws IOException, SQLException, ManagedProcessException {
        //UpdateDB.updateTestDB();
    }

    @Before
    public void setUp() {

        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        final ServiceLocator locator = ServiceLocatorUtilities.bind(GFApplicationBinderFactory.instance());
        locator.inject(this);


    }

    @After
    public void tearDown() {

    }


    @Test
    public void testSingleMessage() {
        Project project = new Project();
        project.setName("Testprojekt");
        project.setGroupWorkContext(GroupWorkContext.dota_1);
        emailService.sendSingleMessage(Messages.SurveyGroupFormation(project, "julian.dehne@web.de"),
                new User(GeneralConfig.EMAIL_ADRESS));
    }

    @Test
    public void convertEmailToAscii() {
        String hello = "hello";
        String s = Messages.stringToAsciiEncode(hello);
        System.out.println(s);
    }

    @Test
    public void testSendEmailToAll() {

        User author = factory.manufacturePojo(User.class);
        userDAO.persist(author);

        Project project = new Project();
        project.setName("Testprojekt");
        project.setGroupWorkContext(GroupWorkContext.dota_1);
        project.setAuthorEmail(author.getEmail());
        projectDAO.persist(project);


        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            User user = factory.manufacturePojo(User.class);
            user.setEmail(GeneralConfig.EMAIL_ADRESS);
            management.create(user);
            management.register(user, project, null);
            users.add(user);
        }

        emailService.sendMessageToUsers(project, Messages.SurveyEvaluation(project));

        // clean up

        /*management.delete(project);
        //management.delete(author);

        for (User user : users) {
            management.delete(user);
        }*/


    }


}
