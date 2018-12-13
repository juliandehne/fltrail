package unipotsdam.gf.modules.group.preferences.survey;

import java.util.List;

public class Page {
    private String name;
    private java.util.List<Question> questions;

    public Page() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
