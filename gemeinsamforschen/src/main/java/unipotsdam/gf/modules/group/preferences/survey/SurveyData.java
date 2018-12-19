package unipotsdam.gf.modules.group.preferences.survey;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;

@XmlRootElement
public class SurveyData {
    private LocalizedText title;
    private ArrayList<Page> pages;

    public SurveyData() {
        this.pages = new ArrayList<>();
    }

    @JsonProperty("title")
    public LocalizedText getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(LocalizedText title) {
        this.title = title;
    }

    @JsonProperty("pages")
    public ArrayList<Page> getPages() {
        return pages;
    }

    @JsonProperty("pages")
    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
    }
}
