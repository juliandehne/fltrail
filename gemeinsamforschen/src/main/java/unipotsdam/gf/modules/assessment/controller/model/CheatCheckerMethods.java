package unipotsdam.gf.modules.assessment.controller.model;

public enum CheatCheckerMethods {
    variance("variance"),
    median("median"),
    none("");

    private final String text;

    CheatCheckerMethods(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
