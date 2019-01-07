package unipotsdam.gf.modules.group.preferences.groupal.request;

import unipotsdam.gf.modules.group.preferences.groupal.request.Criterion;

import javax.xml.bind.annotation.XmlAttribute;

public class UsedCriterion extends Criterion {
    private int valueCount;

    @XmlAttribute
    public int getValueCount() {
        return valueCount;
    }

    public void setValueCount(int valueCount) {
        this.valueCount = valueCount;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
