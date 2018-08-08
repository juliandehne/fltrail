package unipotsdam.gf.modules.peerassessment;

import org.junit.Test;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

import java.util.*;

public class HashMapTest {

    private Map<String, Double> meanOfWorkRatings(ArrayList<Map<String, Integer>> workRatings) {
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

    final String sortCase1 = "responsibility";
    Comparator<Map<String, Double>> byResponsibility = (o1, o2) -> {
        Double sumOfO1 = 0.;
        Double sumOfO2 = 0.;
        for (String key : o1.keySet()) {
            sumOfO1 += o1.get(key);
            sumOfO2 += o2.get(key);
        }
        //Double first = o1.get(sortCase1);
        //Double second = o2.get(sortCase1);
        if (sumOfO1.equals(sumOfO2)) {
            return 0;
        } else {
            return sumOfO1 < sumOfO2 ? -1 : 1;
        }
    };

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
        ArrayList<Map<String, Integer>> workRatings = new ArrayList<>();
        workRatings.add(work);
        workRatings.add(work2);
        workRatings.add(work3);
        workRatings.add(work4);
        ArrayList<Map<String, Double>> means = new ArrayList<>();
        Double threshold = 0.4;
        if (workRatings.size() > 1) {
            for (Map rating : workRatings) {
                ArrayList<Map<String, Integer>> possiblyCheating = new ArrayList<>(workRatings);
                possiblyCheating.remove(rating);
                means.add(meanOfWorkRatings(possiblyCheating));
            }
        } else {
            for (Map rating : workRatings) {
                means.add(meanOfWorkRatings(workRatings));
            }
        }
        means.sort(byResponsibility);
        System.out.println(means.get(means.size() / 2).toString());
        System.out.println(means.toString());

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
        StudentIdentifier student = new StudentIdentifier("projekt", "student");
        int[] quiz = {1, 0, 1, 1, 1, 0};
        Map work = new HashMap<String, Integer>();
        work.put("responsibility", 1);
        work.put("partOfWork", 1);
        work.put("cooperation", 1);
        work.put("communication", 1);
        work.put("autonomous", 1);
        Map work2 = new HashMap<String, Integer>();
        work2.put("responsibility", 3);
        work2.put("partOfWork", 4);
        work2.put("cooperation", 5);
        work2.put("communication", 3);
        work2.put("autonomous", 4);
        Performance pf = new Performance();
        pf.setFeedback("ein toller typ");
        pf.setQuizAnswer(quiz);
        pf.setStudentIdentifier(student);
        pf.setWorkRating(work);
        Performance pf2 = new Performance();
        pf2.setFeedback("feini feini");
        pf2.setQuizAnswer(quiz);
        pf2.setStudentIdentifier(student);
        pf2.setWorkRating(work2);
        result.add(pf);
        result.add(pf2);
    }

}
