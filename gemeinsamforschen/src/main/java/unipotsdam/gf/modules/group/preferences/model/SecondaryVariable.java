package unipotsdam.gf.modules.group.preferences.model;

import java.util.List;

public class SecondaryVariable {
    private String name;
    private java.util.List<Item> items;
    private Boolean positive; // if this variable is positive, is the main variable positive

    // scaleType in inheritable: if main has a certain -> secondary has -> item has
    private ScaleType scaleType;

    public SecondaryVariable(String name, List<Item> items, Boolean positive) {
        this.name = name;
        this.items = items;
        this.positive = positive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Boolean getPositive() {
        return positive;
    }

    public void setPositive(Boolean positive) {
        this.positive = positive;
    }

    public ScaleType getScaleType() {
        return scaleType;
    }

    public void setScaleType(ScaleType scaleType) {
        this.scaleType = scaleType;
    }





}
