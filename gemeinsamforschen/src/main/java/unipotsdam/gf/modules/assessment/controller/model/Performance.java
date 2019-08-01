package unipotsdam.gf.modules.assessment.controller.model;

import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import java.util.List;
import java.util.Map;

public class Performance {
    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Integer> getQuizAnswer() {
        return quizAnswer;
    }

    public void setQuizAnswer(List<Integer> quizAnswer) {
        this.quizAnswer = quizAnswer;
    }

    public void setWorkRating(Map<String, Double> workRating) {
        this.workRating = workRating;
    }


    private Map<FileRole, Double> contributionRating;

    @Override
    public String toString() {
        return "Performance{" +
                "project=" + project +
                ", user=" + user +
                ", quizAnswer=" + quizAnswer +
                ", workRating=" + workRating +
                ", contributionRating=" + contributionRating +
                '}';
    }

    private Project project;
    private User user;
    private List<Integer> quizAnswer;
    private Map<String, Double> workRating;

    public void setContributionRating(Map<FileRole, Double> contributionRating) {
        this.contributionRating = contributionRating;
    }

    public Performance(){}

}
