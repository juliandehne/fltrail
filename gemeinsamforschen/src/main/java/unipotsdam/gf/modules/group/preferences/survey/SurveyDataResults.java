package unipotsdam.gf.modules.group.preferences.survey;

import java.util.HashMap;

public class SurveyDataResults {
    HashMap<String,String> data;

    public SurveyDataResults() {
        data = new HashMap<>();
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
}
