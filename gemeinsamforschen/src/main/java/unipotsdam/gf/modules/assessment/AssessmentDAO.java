package unipotsdam.gf.modules.assessment;

import uk.co.jemos.podam.api.PodamFactoryImpl;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.inject.Inject;

public class AssessmentDAO {

    PodamFactoryImpl podamFactory = new PodamFactoryImpl();

    @Inject
    private MysqlConnect connect;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private UserDAO userDAO;

    /**
     * get the progress for the docent so he can assess if he wants to proceed to grading
     * @param project
     * @return
     */
    public AssessmentProgress getProgress(Project project) {
        AssessmentProgress assessmentProgress =  new AssessmentProgress();
        //assessmentProgress.setNumberOfGroupsWithoutPresentation(3);

        int numberOfGroups = groupDAO.getGroupsByProjectName(project.getName()).size();

        // get number of submitted Presentation Files
        String fileRole = FileRole.PRESENTATION.name();
        int presentationCount = getNumberOfSubmittedFiles(project, fileRole);
        assessmentProgress.setNumberOfGroupsWithoutPresentation(numberOfGroups - presentationCount);

        // get number of submitted final reports
        String fileRole2 = FileRole.FINAL_REPORT.name();
        int reportCount = getNumberOfSubmittedFiles(project, fileRole2);
        assessmentProgress.setNumberOfGroupReportsMissing(numberOfGroups - reportCount);

        //
        assessmentProgress.setNumberOfGroupsWithoutExternalAssessment(numberOfGroups);
        // Todo IMPLEMENT

        assessmentProgress.setNumberOfStudentsWithoutInternalAsssessment(userDAO.getUsersByProjectName(project
                .getName()).size());
        // TODO IMPLEMENT

        return assessmentProgress;
    }

    public int getNumberOfSubmittedFiles(Project project, String fileRole) {
        int presentationCount;
        connect.connect();
        String query = "SELECT COUNT(*) from largefilestorage where projectName = ? and fileRole = '" +fileRole +"'" ;
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(query, project.getName());
        if (vereinfachtesResultSet == null) {
            return  0;
        }
        vereinfachtesResultSet.next();
        presentationCount = vereinfachtesResultSet.getInt("COUNT(*)");
        connect.close();
        return presentationCount;
    }


}
