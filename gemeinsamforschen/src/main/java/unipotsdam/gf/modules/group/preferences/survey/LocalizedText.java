package unipotsdam.gf.modules.group.preferences.survey;

import org.codehaus.jackson.annotate.JsonProperty;

public class LocalizedText {
    private String defaultText;
    private String de;

    public LocalizedText() {
    }

    public LocalizedText(String defaultText, String de) {
        this.defaultText = defaultText;
        this.de = de;
    }

    @JsonProperty("default")
    public String getDefaultText() {
        return defaultText;
    }

    @JsonProperty("default")
    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }
}
