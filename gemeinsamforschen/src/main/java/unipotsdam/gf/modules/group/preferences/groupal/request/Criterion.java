package unipotsdam.gf.modules.group.preferences.groupal.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Criterion {


    protected String name;

    protected float minValue;

    protected float maxValue;

    protected String isHomogeneous;

    protected float weight;


    //@XmlElement(name = "Value")
    private java.util.List<Value> values;

    public Criterion() {
        values = new ArrayList<>();
        minValue = 1f;
        maxValue = 5f;
        weight = 1f;

    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    @XmlAttribute
    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    @XmlAttribute
    public String getIsHomogeneous() {
        return isHomogeneous;
    }

    public void setIsHomogeneous(String isHomogeneous) {
        this.isHomogeneous = isHomogeneous;
    }

    @XmlAttribute
    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @XmlElement(name = "Value")
    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        Criterion criterion = (Criterion) o;
        return Objects.equals(getName(), criterion.getName());
    }
}
