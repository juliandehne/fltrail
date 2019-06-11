package unipotsdam.gf.modules.assessment;

import org.junit.Test;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HashMapTest {

    private Map<String, Double> meanOfWorkRatings(ArrayList<Map<String, Double>> workRatings) {
        HashMap<String, Double> mean = new HashMap();
        double size = (double) workRatings.size();
        Iterator it = workRatings.get(0).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            mean.put((String) pair.getKey(), 0.0);
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
            return sumOfO1 < sumOfO2 ? -1 : 1;
        }
    };

    //fixme: use the function from the class .... obviously
    public ArrayList<Map<String, Double>> cheatChecker(ArrayList<Map<String, Double>> workRatings, String method) {
        ArrayList<Map<String, Double>> result = new ArrayList<>();
        //todo: magicString sollte Enum sein um nutzbarer zu sein.
        if (method.equals("median")) {
            workRatings.sort(byMean);
            result.add(workRatings.get(workRatings.size() / 2)); //in favor of student
        }
        if (method.equals("variance")) {
            ArrayList<Map<String, Double>> oneExcludedMeans = new ArrayList<>();
            if (workRatings.size() > 1) {
                for (Map rating : workRatings) {
                    ArrayList<Map<String, Double>> possiblyCheating = new ArrayList<>(workRatings);
                    possiblyCheating.remove(rating);
                    oneExcludedMeans.add(meanOfWorkRatings(possiblyCheating));
                }
            } else {
                oneExcludedMeans.add(meanOfWorkRatings(workRatings));
            }
            Map<String, Double> meanWorkRating = new HashMap<>(meanOfWorkRatings(oneExcludedMeans));
            ArrayList<Map<String, Double>> elementwiseDeviation = new ArrayList<>();
            for (Map<String, Double> rating : oneExcludedMeans) {
                HashMap<String, Double> shuttle = new HashMap<>();
                for (String key : rating.keySet()) {
                    Double value = (rating.get(key) - meanWorkRating.get(key)) * (rating.get(key) - meanWorkRating.get(key));
                    shuttle.put(key, value);
                }
                elementwiseDeviation.add(shuttle);
            }
            Double deviationOld = 0.;
            Integer key = 0;
            for (Integer i = 0; i < elementwiseDeviation.size(); i++) {
                Double deviationNew = 0.;
                for (Double devi : elementwiseDeviation.get(i).values()) {
                    deviationNew += devi;
                }
                if (deviationNew > deviationOld) {
                    deviationOld = deviationNew;
                    key = i;
                }
            }
            result.add(oneExcludedMeans.get(key)); //gets set of rates with smallest deviation in data
        }
        return result;
    }

    @Test
    public void sortTest() {
        Map work = new HashMap<String, Double>();
        work.put("responsibility", 1.);
        work.put("partOfWork", 1.);
        work.put("cooperation", 1.);
        work.put("communication", 1.);
        work.put("autonomous", 1.);
        Map work2 = new HashMap<String, Double>();
        work2.put("responsibility", 3.);
        work2.put("partOfWork", 4.);
        work2.put("cooperation", 5.);
        work2.put("communication", 3.);
        work2.put("autonomous", 4.);
        Map work3 = new HashMap<String, Double>();
        work3.put("responsibility", 2.);
        work3.put("partOfWork", 3.);
        work3.put("cooperation", 5.);
        work3.put("communication", 2.);
        work3.put("autonomous", 1.);
        Map work4 = new HashMap<String, Double>();
        work4.put("responsibility", 5.);
        work4.put("partOfWork", 5.);
        work4.put("cooperation", 4.);
        work4.put("communication", 4.);
        work4.put("autonomous", 5.);
        ArrayList<Map<String, Double>> workRatings = new ArrayList<>();
        workRatings.add(work);
        workRatings.add(work2);
        workRatings.add(work3);
        workRatings.add(work4);

        //fixme: workRating in class. cheatchecker extends Hashmap<String, Double>!?
        System.out.println(cheatChecker(workRatings, "median").toString());
        System.out.println(cheatChecker(workRatings, "variance").toString());
    }

    @Test
    public void meanMap() {
        Map work = new HashMap<String, Double>();
        work.put("responsibility", 1.);
        work.put("partOfWork", 1.);
        work.put("cooperation", 1.);
        work.put("communication", 1.);
        work.put("autonomous", 1.);
        Map work2 = new HashMap<String, Double>();
        work2.put("responsibility", 3.);
        work2.put("partOfWork", 4.);
        work2.put("cooperation", 5.);
        work2.put("communication", 3.);
        work2.put("autonomous", 4.);
        ArrayList<Map<String, Integer>> workRatings = new ArrayList<>();
        workRatings.add(work);
        workRatings.add(work2);
        workRatings.add(work2);

        Map mean = new HashMap();
        double size = (double) workRatings.size();
        Iterator it = workRatings.get(0).entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            mean.put(pair.getKey(), 0.0);
        }
        for (int i = 0; i < workRatings.size(); i++) {
            it = workRatings.get(i).entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                mean.put(pair.getKey(), (Double) mean.get(pair.getKey()) + (Double) pair.getValue() / size);
            }
        }
        System.out.println(mean.toString());
    }

    @Test
    public void printMap() {
        Map workWork = new HashMap<String, Integer>();
        workWork.put("horst", 2);
        workWork.put("Stefan", 5);
        Performance performance = new Performance();
        performance.setWorkRating(workWork);
        Iterator it = workWork.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(workWork.get(pair.getKey()));
            System.out.println((double) 2 * (Integer) pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    @Test
    public void test1() {
        ArrayList<Performance> result = new ArrayList<>();
        List<Integer> quiz = new ArrayList<>();
        quiz.add(1);
        quiz.add(0);
        quiz.add(1);
        quiz.add(0);
        quiz.add(1);
        quiz.add(0);
        quiz.add(1);
        Map work = new HashMap<String, Double>();
        work.put("responsibility", 1.);
        work.put("partOfWork", 1.);
        work.put("cooperation", 1.);
        work.put("communication", 1.);
        work.put("autonomous", 1.);
        Map work2 = new HashMap<String, Double>();
        work2.put("responsibility", 3.);
        work2.put("partOfWork", 4.);
        work2.put("cooperation", 5.);
        work2.put("communication", 3.);
        work2.put("autonomous", 4.);
        Map contribution1 = new HashMap<String, Double>();
        contribution1.put("Dossier", 4.);
        contribution1.put("eJournal", 2.);
        contribution1.put("research", 4.);
        Map contribution2 = new HashMap<String, Double>();
        contribution2.put("Dossier", 2.);
        contribution2.put("eJournal", 3.);
        contribution2.put("research", 4.);
        Performance pf = new Performance();
        pf.setContributionRating(contribution1);
        pf.setQuizAnswer(quiz);
        pf.setProject(new Project("test1"));
        pf.setUser(new User("test@uni.de"));
        pf.setWorkRating(work);
        Performance pf2 = new Performance();
        pf2.setContributionRating(contribution2);
        pf2.setQuizAnswer(quiz);
        pf2.setProject(new Project("test1"));
        pf2.setUser(new User("test@uni.de"));
        pf2.setWorkRating(work2);
        result.add(pf);
        result.add(pf2);
    }

}
