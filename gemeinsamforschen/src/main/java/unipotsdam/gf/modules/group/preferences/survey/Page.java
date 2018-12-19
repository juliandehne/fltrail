package unipotsdam.gf.modules.group.preferences.survey;

import com.sun.xml.internal.txw2.annotation.XmlElement;

import java.util.ArrayList;
import java.util.List;

@XmlElement
public class Page {
    private String name;
    private java.util.List<Question> questions;

    public Page() {
        this.questions = new ArrayList<>();
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
