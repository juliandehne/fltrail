package unipotsdam.gf.modules.quiz;

import unipotsdam.gf.modules.assessment.AssessmentDAO;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.user.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuizImpl {

    @Inject
    AssessmentDAO assessmentDAO;

    @Inject
    QuizDAO quizDAO;

    public void answerQuiz(Map<String, List<String>> questions, StudentIdentifier student) {
        for (String question : questions.keySet()) {
            Map<String, Boolean> whatAreAnswers =
                    assessmentDAO.getAnswers(student.getProjectName(), question);
            Map<String, Boolean> wasQuestionAnsweredCorrectly = new HashMap<>();
            Boolean correct = true;
            for (String studentAnswer : questions.get(question)) {
                if (!whatAreAnswers.get(studentAnswer)) {
                    correct = false;
                }
            }
            wasQuestionAnsweredCorrectly.put(question, correct);
            quizDAO.writeAnsweredQuiz(student, wasQuestionAnsweredCorrectly);
        }
    }

    private Map<User, Double> quizGrade(ArrayList<Performance> totalPerformance) {
        double[] allAssessments = new double[totalPerformance.size()];
        Map<User, Double> grading = new HashMap<>();

        for (int i = 0; i < totalPerformance.size(); i++) {
            for (Integer quiz : totalPerformance.get(i).getQuizAnswer()) {
                allAssessments[i] += quiz;
            }
            allAssessments[i] = allAssessments[i] / totalPerformance.get(i).getQuizAnswer().size();
        }
        for (int i = 0; i < totalPerformance.size(); i++) {
            grading.put(totalPerformance.get(i).getUser(), allAssessments[i]);
        }
        return grading;
    }
}
