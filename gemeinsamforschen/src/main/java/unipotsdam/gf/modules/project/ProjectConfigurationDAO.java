package unipotsdam.gf.modules.project;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.modules.assessment.AssessmentMechanism;
import unipotsdam.gf.modules.group.GroupFormationMechanism;
import unipotsdam.gf.modules.feedback.Category;

import javax.inject.Inject;
import java.util.HashMap;

public class ProjectConfigurationDAO {


    @Inject
    MysqlConnect connect;

    /**
     * @param projectConfiguration
     */
    public void persistProjectConfiguration(ProjectConfiguration projectConfiguration, Project project) {

        connect.connect();

        // persist Criteria
        HashMap<Category, Boolean> criteriaSelected = projectConfiguration.getCriteriaSelected();
        for (Category category : criteriaSelected.keySet()) {
            Boolean criteriumSelected = criteriaSelected.get(category);
            if (criteriumSelected != null && criteriumSelected) {
                String projectName = project.getName();
                String categoryName = category.name();
                String mysqlRequest = "insert INTO categoriesSelected (`projectName`,`categorySelected`) VALUES (?,?)";
                connect.issueInsertOrDeleteStatement(mysqlRequest, projectName, categoryName );
            }
        }

        // persist Phases
        HashMap<Phase, Boolean> phasesSelected = projectConfiguration.getPhasesSelected();
        for (Phase phase : phasesSelected.keySet()) {
            Boolean projectPhaseSelected = phasesSelected.get(phase);
            if (projectPhaseSelected != null && projectPhaseSelected) {
                String mysqlRequest = "insert INTO phasesSelected (`projectName`,`phaseSelected`) VALUES (?,?)";
                connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), phase.name());
            }
        }

        // persist GroupFinding
       GroupFormationMechanism groupFindingMechanism =
                projectConfiguration.getGroupMechanismSelected();

            if (groupFindingMechanism != null) {
                String mysqlRequest =
                        "insert INTO groupfindingMechanismSelected (`projectName`,`gfmSelected`) VALUES (?,?)";
                connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), groupFindingMechanism.name());
            }



        // persist assessmentMechanismSelected
        HashMap<AssessmentMechanism, Boolean> assessmentMechanismsSelected =
                projectConfiguration.getAssessmentMechanismSelected();
        for (AssessmentMechanism assessmentMechanism : assessmentMechanismsSelected.keySet()) {
            Boolean asmSelected = assessmentMechanismsSelected.get(assessmentMechanism);
            if (asmSelected != null && asmSelected) {
                String mysqlRequest =
                        "insert INTO assessmentMechanismSelected (`projectName`,`amSelected`) VALUES (?,?)";
                connect.issueInsertOrDeleteStatement(mysqlRequest, project.getName(), assessmentMechanism.name());
            }
        }
        connect.close();
    }

    public ProjectConfiguration loadProjectConfiguration(Project project) {
        connect.connect();


        HashMap<Phase, Boolean> projectPhasesSelected =
                getSelectionFromTable(connect, Phase.class, project, "phasesSelected");

        HashMap<Category, Boolean> categorySelected =
                getSelectionFromTable(connect, Category.class, project, "categoriesSelected");

        HashMap<AssessmentMechanism, Boolean> asmSelected =
                getSelectionFromTable(connect, AssessmentMechanism.class, project, "assessmentMechanismSelected");


        HashMap<GroupFormationMechanism, Boolean> groupfindingMechanismSelected =
                getSelectionFromTable(connect, GroupFormationMechanism.class, project, "groupfindingMechanismSelected");



        GroupFormationMechanism gfmSelected = null;

        for (GroupFormationMechanism groupFormationMechanism : groupfindingMechanismSelected.keySet()) {
            if (groupfindingMechanismSelected.get(groupFormationMechanism)) {
                gfmSelected = groupFormationMechanism;
            }
        }
        connect.close();

        ProjectConfiguration projectConfiguration = new ProjectConfiguration(projectPhasesSelected, categorySelected,
                asmSelected,gfmSelected);
        return projectConfiguration;
    }

    /**
     * this looks like magic but it is just a hack loading all the config tables by taking advantage
     * of the fact that they all have two columns with the second being the selected attribute and the first being the
     * project id
     *
     * @param connect
     * @param selectionclass
     * @param project
     * @param table
     * @param <T>
     * @return
     */
    private <T extends Enum<T>> HashMap<T, Boolean> getSelectionFromTable(
            MysqlConnect connect, Class<T> selectionclass, Project project, String table) {
        // get phasesSelected
        String id = project.getName();
        String mysqlRequest = "SELECT * FROM " + table + " where projectName = ?";
        VereinfachtesResultSet vereinfachtesResultSet = connect.issueSelectStatement(mysqlRequest, id);
        HashMap<T, Boolean> projectPhaseBoolean = new HashMap<>();
        while (!vereinfachtesResultSet.isLast()) {
            Boolean next = vereinfachtesResultSet.next();
            if (next) {
                String phaseSelected = vereinfachtesResultSet.getObject(2).toString();
                T phaseSelected1 = Enum.valueOf(selectionclass, phaseSelected);
                projectPhaseBoolean.put(phaseSelected1, true);
            } else {
                break;
            }
        }
        return projectPhaseBoolean;
    }
}
