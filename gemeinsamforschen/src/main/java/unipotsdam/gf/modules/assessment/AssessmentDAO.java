package unipotsdam.gf.modules.assessment;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;

import javax.inject.Inject;

public class AssessmentDAO {

    PodamFactoryImpl podamFactory = new PodamFactoryImpl();

    @Inject
    private MysqlConnect connect;

    @Inject
    private UserDAO userDAO;

    public AssessmentProgress getProgress() {
       AssessmentProgress assessmentProgress =  podamFactory.manufacturePojo(AssessmentProgress.class);
       //assessmentProgress.setNumberOfGroupsWithoutPresentation(3);
       return assessmentProgress;
    }
}
