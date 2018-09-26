package unipotsdam.gf.modules.researchreport;

import java.io.File;

public class ResearchReport {
    // TODO add properties
    private String title;
    private ResearchQuestion researchQuestion;
    private String id;
    private String method;
    private String research;
    private Bibliography bibliography;
    private String researchResult;
    private String evaluation;
    private File picture;
    private Timeplanning timeplan;

    public ResearchReport(String title, ResearchQuestion researchQuestion, String method, String research, Bibliography bibliography, String researchResult, String evaluation, File picture, Timeplanning timeplan) {
        this.title = title;
        this.researchQuestion = researchQuestion;
        this.method = method;
        this.research = research;
        this.bibliography = bibliography;
        this.researchResult = researchResult;
        this.evaluation = evaluation;
        this.picture = picture;
        this.timeplan = timeplan;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ResearchQuestion getResearchQuestion() {
        return researchQuestion;
    }

    public void setResearchQuestion(ResearchQuestion researchQuestion) {
        this.researchQuestion = researchQuestion;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getResearch() {
        return research;
    }

    public void setResearch(String research) {
        this.research = research;
    }

    public Bibliography getBibliography() {
        return bibliography;
    }

    public void setBibliography(Bibliography bibliography) {
        this.bibliography = bibliography;
    }

    public String getResearchResult() {
        return researchResult;
    }

    public void setResearchResult(String researchResult) {
        this.researchResult = researchResult;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public File getPicture() {
        return picture;
    }

    public void setPicture(File picture) {
        this.picture = picture;
    }

    public Timeplanning getTimeplan() {
        return timeplan;
    }

    public void setTimeplan(Timeplanning timeplan) {
        this.timeplan = timeplan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
