package unipotsdam.gf.modules.group.preferences.model;

import java.util.List;

public class MainVariable {
    private String name;
    private java.util.List<SecondaryVariable> secondaryVariableList;
    // or
    private java.util.List<Item> items;
    private ContextType contextType;
    private ScaleType scaleType;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setContextType(ContextType contextType) {
        this.contextType = contextType;
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }


    public ContextType getContextType() {
        return contextType;
    }

    public ScaleType getScaleType() {
        return scaleType;
    }

    public List<SecondaryVariable> getSecondaryVariableList() {
        return secondaryVariableList;
    }

    public void setSecondaryVariableList(
            List<SecondaryVariable> secondaryVariableList) {
        this.secondaryVariableList = secondaryVariableList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
