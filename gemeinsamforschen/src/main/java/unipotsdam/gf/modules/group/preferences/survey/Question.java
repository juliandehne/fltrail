package unipotsdam.gf.modules.group.preferences.survey;

import org.codehaus.jackson.annotate.JsonProperty;

public class Question {
    private String type;
    private String name;
    private LocalizedText title;
    private LocalizedText minRateDescription;
    private LocalizedText maxRateDescription;

    public Question() {
        this.type = "rating";
        this.minRateDescription = new LocalizedText("I don't agree at all", "I fully agree");
        this.maxRateDescription = new LocalizedText("Ich stimme überhaupt nicht zu", "Ich stimmve voll zu");
        /*minRateDescription.setDefaultText("I don't agree at all");
        maxRateDescription.setDefaultText("I fully agree");
        minRateDescription.setDe("Ich stimme überhaupt nicht zu");
        maxRateDescription.setDe("Ich stimmve voll zu");*/
    }

    public String getType() {
        return type;
    }

   /* public void setType(String type) {
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

    @JsonProperty("minRateDescription")
    public LocalizedText getMinRateDescription() {
        return minRateDescription;
    }

   /* @JsonProperty("minRateDescription")
    public void setMinRateDescription(LocalizedText minRateDescription) {
        this.minRateDescription = minRateDescription;
    }*/

    @JsonProperty("maxRateDescription")
    public LocalizedText getMaxRateDescription() {
        return maxRateDescription;
    }

   /* @JsonProperty("maxRateDescription")
    public void setMaxRateDescription(LocalizedText maxRateDescription) {
        this.maxRateDescription = maxRateDescription;
    }*/
}
