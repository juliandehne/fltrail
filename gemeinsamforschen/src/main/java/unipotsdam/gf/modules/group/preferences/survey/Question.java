package unipotsdam.gf.modules.group.preferences.survey;

import org.codehaus.jackson.annotate.JsonProperty;

public class Question {
    protected String type;
    private String name;
    private LocalizedText title;
    private Boolean isRequired;

    public Question() {
        this.isRequired = true;
    }

    public String getType() {
        return type;
    }

    /*public void setType(String type) {
        this.type = type;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("title")
    public LocalizedText getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(LocalizedText title) {
        this.title = title;
    }

    @JsonProperty("isRequired")
    public Boolean getIsRequired() {
        return isRequired;
    }

    @JsonProperty("isRequired")
    public void setIsRequired(Boolean required) {
        isRequired = required;
    }
}
