package unipotsdam.gf.modules.group.preferences.survey;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

public class SurveyData {
    private LocalizedText title;
    private ArrayList<Page> pages;

    @JsonProperty("title")
    public LocalizedText getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(LocalizedText title) {
        this.title = title;
    }

    public ArrayList<Page> getPages() {
        return pages;
    }

    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
    }
}
