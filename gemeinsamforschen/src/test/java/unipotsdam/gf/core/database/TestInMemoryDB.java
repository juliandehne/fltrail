package unipotsdam.gf.core.database;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.tasks.TaskDAO;
import unipotsdam.gf.modules.user.User;

import javax.inject.Inject;

public class TestInMemoryDB {

    @Inject
    Management management;

    @Inject
    IGroupFinding groupFinding;

    @Inject
    TaskDAO taskDAO;


    private PodamFactory factory = new PodamFactoryImpl();

    private User teacher;

    @Before
    public void setUp() {
        /*final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());*/
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        locator.inject(this);

    }


    @Test
    public void createCourse() {

        this.teacher = factory.manufacturePojo(User.class);
        teacher.setStudent(false);
        management.create(teacher, null);
        assert management.exists(teacher);
    }
}
