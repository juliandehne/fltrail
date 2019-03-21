package unipotsdam.gf.modules.group.preferences.survey;

import unipotsdam.gf.modules.project.Project;

public class SurveyProject extends Project {

    private Boolean isAutomated;

    public SurveyProject(String projectName, GroupWorkContext groupWorkContext) {
        super(projectName);
        setGroupWorkContext(groupWorkContext);
        setAutomated(GroupWorkContextUtil.isGamingOrAutomatedGroupFormation(groupWorkContext));
    }

    public Boolean getAutomated() {
        return isAutomated;
    }

    public void setAutomated(Boolean automated) {
        isAutomated = automated;
    }


}
