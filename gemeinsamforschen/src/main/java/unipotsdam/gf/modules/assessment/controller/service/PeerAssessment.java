package unipotsdam.gf.modules.assessment.controller.service;

import sun.misc.Perf;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.QuizAnswer;
import unipotsdam.gf.modules.assessment.controller.model.*;

import java.lang.reflect.Array;
import java.util.*;

public class PeerAssessment implements IPeerAssessment {
    @Override
    public void addAssessmentDataToDB(Assessment assessment) {

    }

    @Override//returns one quiz
    public Quiz getQuiz(String projectId, String quizId, String author) {
        return new QuizDBCommunication().getQuizByProjectQuizId(projectId, quizId, author);
    }

    @Override //returns all quizzes in the course
    public ArrayList<Quiz> getQuiz(String projectId) {
        return new QuizDBCommunication().getQuizByProjectId(projectId);
    }

    @Override
    public Assessment getAssessmentDataFromDB(StudentIdentifier student) {
        return new AssessmentDBCommunication().getAssessment(student);
    }

    @Override
    public void createQuiz(StudentAndQuiz studentAndQuiz) {
        new QuizDBCommunication().createQuiz(studentAndQuiz.getQuiz(), studentAndQuiz.getStudentIdentifier().getStudentId(), studentAndQuiz.getStudentIdentifier().getProjectId());
    }

    @Override
    public void deleteQuiz(String quizId) {
        new QuizDBCommunication().deleteQuiz(quizId);
    }

    @Override
    public List<Grading> calculateAssessment(ArrayList<Performance> totalPerformance) {
        List<Grading> quizMean = quizGrade(totalPerformance);
        List<Grading> workRateMean = workRateGrade(totalPerformance);
        Grading[] grading = new Grading[totalPerformance.size()];
        for (int i = 0; i < quizMean.size(); i++) {
            double grade = (quizMean.get(i).getGrade() + workRateMean.get(i).getGrade()) / 2.0;
            grading[i] = new Grading(totalPerformance.get(i).getStudentIdentifier(), grade);
        }
        return Arrays.asList(grading);
    }

    private List<Grading> quizGrade(ArrayList<Performance> totalPerformance) {
        double[] allAssessments = new double[totalPerformance.size()];
        Grading[] grading = new Grading[totalPerformance.size()];

        for (int i = 0; i < totalPerformance.size(); i++) {
            for (int j = 0; j < totalPerformance.get(i).getQuizAnswer().length; j++) {
                allAssessments[i] += totalPerformance.get(i).getQuizAnswer()[j];
            }
            allAssessments[i] = 6.0 - 5.0 * allAssessments[i] / totalPerformance.get(i).getQuizAnswer().length;
        }
        for (int i = 0; i < totalPerformance.size(); i++) {
            Grading shuttle = new Grading(totalPerformance.get(i).getStudentIdentifier(), allAssessments[i]);
            grading[i] = shuttle;
        }
        return Arrays.asList(grading);
    }

    private List<Grading> workRateGrade(ArrayList<Performance> totalPerformance) {
        double[] allGrades = new double[totalPerformance.size()];
        Grading[] grading = new Grading[totalPerformance.size()];
        for (int i = 0; i < totalPerformance.size(); i++) {
            Map workRating = totalPerformance.get(i).getWorkRating();
            Iterator it = workRating.entrySet().iterator();
            int size = 0;
            while (it.hasNext()) {
                HashMap.Entry pair = (HashMap.Entry) it.next();
                Integer rating = (Integer) pair.getValue();
                allGrades[i] += (double) rating;
                it.remove(); // avoids a ConcurrentModificationException
                size++;
            }
            allGrades[i] = 6 - allGrades[i] / size;
        }
        for (int i = 0; i < totalPerformance.size(); i++) {
            Grading shuttle = new Grading(totalPerformance.get(i).getStudentIdentifier(), allGrades[i]);
            grading[i] = shuttle;
        }
        return Arrays.asList(grading);
    }

    private Map<String, Double> meanOfWorkRatings(ArrayList<Map<String, Integer>> workRatings) {
        HashMap<String, Double> mean = new HashMap();
        double size = (double) workRatings.size();
        Iterator it = workRatings.get(0).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            mean.put((String) pair.getKey(), 0.0);
            it.remove(); // avoids a ConcurrentModificationException
        }
        for (int i = 0; i < workRatings.size(); i++) {
            it = workRatings.get(i).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                mean.put((String) pair.getKey(), (Double) pair.getValue() / size + mean.get(pair.getKey()));
            }
        }
        return mean;
    }
//todo: funktioniert noch nicht. Fliegt aus der foreach schleife, kA warum.
    public ArrayList<Map> cheatChecker(ArrayList<Map<String, Integer>> workRatings) {
        ArrayList<Map<String, Integer>> possiblyCheating;
        ArrayList<Map<String, Double>> means = new ArrayList<>();
        Double threshold = 0.4;
        boolean cheat;
        for (Map rating : workRatings) {
            possiblyCheating = workRatings;
            possiblyCheating.remove(rating);
            means.add(meanOfWorkRatings(possiblyCheating));
        }
        means.sort(byResponsibility);
        String resp = "responosibility";
        if (means.get(0).get(resp) - threshold > means.get(1).get(resp))
            cheat = true;
        return null;
    }

    @Override
    public ArrayList<Performance> getTotalAssessment(StudentIdentifier studentIdentifier) {
        return null;
    }

    @Override
    public int meanOfAssessment(String ProjectId) {
        return 0;
    }


    @Override
    public void postPeerRating(ArrayList<PeerRating> peerRatings, String projectId, String groupId) {

    }

    @Override
    public void answerQuiz(StudentAndQuiz studentAndQuiz, QuizAnswer quizAnswer) {

    }
///todo: das ist nicht die feine englische. Kann ich das hübschivieren?
    final String sortCase1 = "responsibility";
    Comparator<Map<String, Double>> byResponsibility = (o1, o2) -> {
        Double first = o1.get(sortCase1);
        Double second = o2.get(sortCase1);
        if (first.equals(second)) {
            return 0;
        } else {
            return first < second ? -1 : 1;
        }
    };
    final String sortCase2 = "partOfWork";
    Comparator<Map<String, Double>> byPartOfWork = (o1, o2) -> {
        Double first = o1.get(sortCase2);
        Double second = o2.get(sortCase2);
        if (first.equals(second)) {
            return 0;
        } else {
            return first < second ? -1 : 1;
        }
    };
    final String sortCase3 = "cooperation";
    Comparator<Map<String, Double>> byCooperation = (o1, o2) -> {
        Double first = o1.get(sortCase3);
        Double second = o2.get(sortCase3);
        if (first.equals(second)) {
            return 0;
        } else {
            return first < second ? -1 : 1;
        }
    };
    final String sortCase4 = "communication";
    Comparator<Map<String, Double>> byCommunication = (o1, o2) -> {
        Double first = o1.get(sortCase4);
        Double second = o2.get(sortCase4);
        if (first.equals(second)) {
            return 0;
        } else {
            return first < second ? -1 : 1;
        }
    };
    final String sortCase5 = "autonomous";
    Comparator<Map<String, Double>> byAutonomous = (o1, o2) -> {
        Double first = o1.get(sortCase5);
        Double second = o2.get(sortCase5);
        if (first.equals(second)) {
            return 0;
        } else {
            return first < second ? -1 : 1;
        }
    };


}
