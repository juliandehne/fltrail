package unipotsdam.gf.modules.group.preferences.survey;

import unipotsdam.gf.modules.project.Project;

public class SurveyProject extends Project {

    public SurveyProject() {
    }

    public SurveyProject(String projectName, GroupWorkContext groupWorkContext) {
        super(projectName);
        setGroupWorkContext(groupWorkContext);
    }
}
