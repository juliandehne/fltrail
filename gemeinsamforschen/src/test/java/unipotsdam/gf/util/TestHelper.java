package unipotsdam.gf.util;

import unipotsdam.gf.core.database.InMemoryMySqlConnect;
import unipotsdam.gf.core.management.ManagementImpl;
import unipotsdam.gf.core.management.project.ProjectDAO;
import unipotsdam.gf.core.management.user.UserDAO;
import unipotsdam.gf.core.management.group.GroupDAO;

public class TestHelper {

    public static ManagementImpl getManagementImpl() {
        InMemoryMySqlConnect connect = new InMemoryMySqlConnect();
        return getManagementImpl(connect);
    }

    public static ManagementImpl getManagementImpl(InMemoryMySqlConnect connect) {
        UserDAO userDAO = new UserDAO(connect);
        GroupDAO groupDAO = new GroupDAO(connect);
        ProjectDAO projectDAO = new ProjectDAO(connect);

        return new ManagementImpl(userDAO, groupDAO, projectDAO, connect);
    }
}
