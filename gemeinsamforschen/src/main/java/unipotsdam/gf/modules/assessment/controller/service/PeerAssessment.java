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
                Double rating = (Double) pair.getValue();
                allGrades[i] += rating;
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

    private Map<String, Double> meanOfWorkRatings(ArrayList<Map<String, Double>> workRatings) {
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

    public ArrayList<Map<String, Double>> cheatChecker(ArrayList<Map<String, Double>> workRatings, String method) {
        ArrayList<Map<String, Double>> oneExcludedMeans = new ArrayList<>();
        ArrayList<Map<String, Double>> result = new ArrayList<>();
        Double threshold = 0.4;
        if (workRatings.size() > 1) {
            for (Map rating : workRatings) {
                ArrayList<Map<String, Double>> possiblyCheating = new ArrayList<>(workRatings);
                possiblyCheating.remove(rating);
                oneExcludedMeans.add(meanOfWorkRatings(possiblyCheating));
            }
        } else {
            oneExcludedMeans.add(meanOfWorkRatings(workRatings));
        }
        if (method.equals("median")) {
            workRatings.sort(byMean);
            result.add(workRatings.get(workRatings.size() / 2)); //in favor of student
        }
        if (method.equals("variance")) {
            Map<String, Double> meanWorkRating = new HashMap<>(meanOfWorkRatings(oneExcludedMeans));
            ArrayList<Map<String, Double>> elementwiseDeviation = new ArrayList<>();
            for (Map<String, Double> rating: oneExcludedMeans){
                HashMap<String, Double> shuttle = new HashMap<>();
                for (String key: rating.keySet()){
                    Double value = (rating.get(key)-meanWorkRating.get(key))*(rating.get(key)-meanWorkRating.get(key));
                    shuttle.put(key, value);
                }
                elementwiseDeviation.add(shuttle);
            }
            Double deviationOld=0.;
            Integer key=0;
            for (Integer i=0; i<elementwiseDeviation.size(); i++){
                Double deviationNew=0.;
                for (Double devi: elementwiseDeviation.get(i).values()){
                    deviationNew += devi;
                }
                if (deviationNew>deviationOld){
                    deviationOld=deviationNew;
                    key = i;
                }
            }
            result.add(oneExcludedMeans.get(key)); //gets set of rates with smallest deviation in data
        }
        return result;
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

    Comparator<Map<String, Double>> byMean = (o1, o2) -> {
        Double sumOfO1 = 0.;
        Double sumOfO2 = 0.;
        for (String key : o1.keySet()) {
            sumOfO1 += o1.get(key);
            sumOfO2 += o2.get(key);
        }
        if (sumOfO1.equals(sumOfO2)) {
            return 0;
        } else {
            return sumOfO1 > sumOfO2 ? -1 : 1;
        }
    };
}
