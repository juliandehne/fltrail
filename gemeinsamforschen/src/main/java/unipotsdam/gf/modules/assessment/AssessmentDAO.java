package unipotsdam.gf.modules.assessment;

import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;

import javax.inject.Inject;

public class AssessmentDAO {
    @Inject
    private MysqlConnect connect;

    @Inject
    private UserDAO userDAO;

    
}
