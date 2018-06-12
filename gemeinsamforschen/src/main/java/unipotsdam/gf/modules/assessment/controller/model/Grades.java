package unipotsdam.gf.modules.assessment.controller.model;

public class Grades {
    private Grading[] grading;

    public Grades(){}

    public Grades(Grading[] grading) {
        this.grading = grading;
    }

    public Grading[] getGrading() {
        return grading;
    }

    public void setGrading(Grading[] grading) {
        this.grading = grading;
    }

    @Override
    public String toString() {
        return "Grades{" +
                "grading=" + grading +
                '}';
    }

}
