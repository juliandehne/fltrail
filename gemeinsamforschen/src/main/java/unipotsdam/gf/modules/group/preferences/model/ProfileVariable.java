package unipotsdam.gf.modules.group.preferences.model;

import java.util.List;

public class ProfileVariable {
    private String variable;
    private java.util.List<String> subVariables;

    public ProfileVariable(String variable, List<String> subVariables) {
        this.variable = variable;
        this.subVariables = subVariables;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public List<String> getSubVariables() {
        return subVariables;
    }

    public void setSubVariables(List<String> subVariables) {
        this.subVariables = subVariables;
    }
}
