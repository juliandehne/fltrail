package unipotsdam.gf.modules.group.preferences.survey;

import org.codehaus.jackson.annotate.JsonProperty;

public class LocalizedText {
    private String en;
    private String de;

    public LocalizedText() {
    }

    public LocalizedText(String en, String de) {
        this.en = en;
        this.de = de;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }
}
