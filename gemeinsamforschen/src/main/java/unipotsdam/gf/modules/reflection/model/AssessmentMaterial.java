package unipotsdam.gf.modules.reflection.model;

import unipotsdam.gf.modules.submission.model.FullSubmission;

import java.util.ArrayList;
import java.util.List;

public class AssessmentMaterial {

    private List<ReflectionQuestionWithAnswer> reflectionQuestionWithAnswerList = new ArrayList<>();

    private List<FullSubmission> portfolioEntries = new ArrayList<>();

    public AssessmentMaterial() {
    }

    public AssessmentMaterial(List<ReflectionQuestionWithAnswer> reflectionQuestionWithAnswerList, List<FullSubmission> portfolioEntries) {
        this.reflectionQuestionWithAnswerList = reflectionQuestionWithAnswerList;
        this.portfolioEntries = portfolioEntries;
    }

    public List<ReflectionQuestionWithAnswer> getReflectionQuestionWithAnswerList() {
        return reflectionQuestionWithAnswerList;
    }

    public void setReflectionQuestionWithAnswerList(List<ReflectionQuestionWithAnswer> reflectionQuestionWithAnswerList) {
        this.reflectionQuestionWithAnswerList = reflectionQuestionWithAnswerList;
    }

    public List<FullSubmission> getPortfolioEntries() {
        return portfolioEntries;
    }

    public void setPortfolioEntries(List<FullSubmission> portfolioEntries) {
        this.portfolioEntries = portfolioEntries;
    }
}
