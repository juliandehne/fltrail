package unipotsdam.gf.modules.assessment.controller.service;

import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.*;

public class PeerAssessmentDummy implements IPeerAssessment {
    @Override
    public void addAssessmentDataToDB(Assessment assessment) {

    }

    @Override
    public Quiz getQuiz(String projectId, String groupId) {
        String[] correctAnswers = new String[2];
        correctAnswers[0] = "42";
        correctAnswers[1] = ""+projectId+" " + groupId;
        String[] wrongAnswers = {"a god created creature", "a sum of my mistakes"};
        if (false){ //projectId with groupId does not exist
            return null;
        }
        Quiz sampleQuiz = new Quiz("multiple","Who am I and if so, how many?", correctAnswers,wrongAnswers);
        return sampleQuiz;
    }

    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
        int[] quizAnswer = {1,1,1,0,0,0,1,0,0,1,1};
        int[] workRating = {1,5,3,4,1,5,5};
        Performance performance = new Performance(quizAnswer, "what a nice guy", workRating);
        Assessment assessment = new Assessment(student, performance);
        return assessment;
    }

    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {

    }

    @Override
    public Grades calculateAssessment(TotalPerformance totalPerformance) {
        Performance[] performanceOfAllStudents = totalPerformance.getPerformances();
        StudentIdentifier[] allStudents = totalPerformance.getStudentIdentifier();
        int[] allAssessements = new int[performanceOfAllStudents.length] ;
        Grades grades = new Grades();
        Grading[] grading = new Grading[performanceOfAllStudents.length];

        for (int i=0; i< performanceOfAllStudents.length;i++) {
            for (int j=0; j< performanceOfAllStudents[i].getQuizAnswer().length;j++) {
                allAssessements[i] += performanceOfAllStudents[i].getQuizAnswer()[j];
            }
            allAssessements[i] = allAssessements[i]/performanceOfAllStudents[i].getQuizAnswer().length;
        }
        for (int i=0; i<performanceOfAllStudents.length; i++){
            grading[i].setStudentIdentifier(allStudents[i]);
            grading[i].setGrade(allAssessements[i]);
        }

        grades.setGrading(grading);
        return grades;
    }

    @Override
    public int meanOfAssessement(String ProjectId) {
        return 0;
    }
}
