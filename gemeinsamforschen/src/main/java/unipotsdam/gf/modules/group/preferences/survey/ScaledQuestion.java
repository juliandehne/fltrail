package unipotsdam.gf.modules.group.preferences.survey;

import org.codehaus.jackson.annotate.JsonProperty;

public class ScaledQuestion extends Question {
    private LocalizedText minRateDescription;
    private LocalizedText maxRateDescription;

    public ScaledQuestion(
            LocalizedText minRateDescription, LocalizedText maxRateDescription) {
        this.minRateDescription = minRateDescription;
        this.maxRateDescription = maxRateDescription;
    }

    public ScaledQuestion() {
        super();
        this.type = "rating";
        this.minRateDescription = new LocalizedText("I don't agree at all", "Ich stimme überhaupt nicht zu");
        this.maxRateDescription = new LocalizedText("I fully agree", "Ich stimme voll zu");
        /*minRateDescription.setDefaultText("I don't agree at all");
        maxRateDescription.setDefaultText("I fully agree");
        minRateDescription.setDe("Ich stimme überhaupt nicht zu");
        maxRateDescription.setDe("Ich stimmve voll zu");*/
    }

    public ScaledQuestion(LocalizedText titel) {
        super();
        this.type = "rating";
        this.minRateDescription = new LocalizedText("I don't agree at all", "Ich stimme überhaupt nicht zu");
        this.maxRateDescription = new LocalizedText("I fully agree", "Ich stimme voll zu");
        this.setTitle(titel);
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
