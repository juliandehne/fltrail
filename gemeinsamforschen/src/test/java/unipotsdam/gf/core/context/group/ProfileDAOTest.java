package unipotsdam.gf.core.context.group;

import ch.vorburger.exec.ManagedProcessException;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.Before;
import org.junit.Test;
import unipotsdam.gf.config.GFApplicationBinder;
import unipotsdam.gf.config.GFApplicationBinderFactory;
import unipotsdam.gf.core.database.UpdateDB;

import java.io.IOException;
import java.sql.SQLException;

public class ProfileDAOTest {
    @Before
    public void setUp() throws IOException, SQLException, ManagedProcessException {
        final ServiceLocator locator = ServiceLocatorUtilities.bind(GFApplicationBinderFactory.instance());
        locator.inject(this);
        
    }

    @Test
    public void testCreate() {

    }

    @Test
    public void testDelete() {

    }


}
