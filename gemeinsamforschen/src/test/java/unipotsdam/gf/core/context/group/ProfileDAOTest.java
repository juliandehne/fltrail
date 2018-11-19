package unipotsdam.gf.core.context.group;

import ch.vorburger.exec.ManagedProcessException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.core.database.TestGFApplicationBinder;
import unipotsdam.gf.core.database.UpdateDB;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.user.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ProfileDAOTest {
    @Before
    public void setUp() throws IOException, SQLException, ManagedProcessException {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(new TestGFApplicationBinder());
        //final ServiceLocator locator = ServiceLocatorUtilities.bind(new GFApplicationBinder());
        locator.inject(this);

        UpdateDB.updateTestDB();
    }

    @Test
    public void testCreate() {

    }

    @Test
    public void testDelete() {

    }


}
