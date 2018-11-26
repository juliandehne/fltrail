package unipotsdam.gf.modules.group.learninggoals;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dehne on 05.12.2017.
 */

@XmlRootElement(name = "PreferenceData")
public class PreferenceData {
    java.util.List<String> competences;
    java.util.List<String> researchQuestions;
    java.util.List<String> tagsSelected;


    public PreferenceData() {
        competences = new ArrayList<>();
        researchQuestions = new ArrayList<>();
        tagsSelected = new ArrayList<>();
    }

    public PreferenceData(
            List<String> competences, List<String> researchQuestions, List<String> tagsSelected) {
        this.competences = competences;
        this.researchQuestions = researchQuestions;
        this.tagsSelected = tagsSelected;
    }


    @XmlElementWrapper(name = "competences")
    public List<String> getCompetences() {
        return competences;
    }

    public void setCompetences(List<String> competences) {
        this.competences = competences;
    }


    @XmlElementWrapper(name = "researchQuestions")
    public List<String> getResearchQuestions() {
        return researchQuestions;
    }

    public void setResearchQuestions(List<String> researchQuestions) {
        this.researchQuestions = researchQuestions;
    }

    public List<String> getTagsSelected() {
        return tagsSelected;
    }

    public void setTagsSelected(List<String> tagsSelected) {
        this.tagsSelected = tagsSelected;
    }
}
