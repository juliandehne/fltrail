package unipotsdam.gf.modules.group.preferences.database;

import unipotsdam.gf.modules.user.User;

public class ProfileQuestionAnswer {
    private ProfileQuestion question;
    private Integer answerIndex;
    // multiple is not implemented
    private String selectedAnswer;
    private User user;

    public ProfileQuestionAnswer(
            ProfileQuestion question, Integer answerIndex, String selectedAnswer, User user) {
        this.question = question;
        this.answerIndex = answerIndex;
        this.selectedAnswer = selectedAnswer;
        this.user = user;
    }

    public ProfileQuestionAnswer() {
    }

    public ProfileQuestion getQuestion() {
        return question;
    }

    public void setQuestion(ProfileQuestion question) {
        this.question = question;
    }

    public Integer getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(Integer answerIndex) {
        this.answerIndex = answerIndex;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
