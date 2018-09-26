package unipotsdam.gf.modules.assessment.controller.model;

public enum cheatCheckerMethods {
    variance("variance"),
    median("median"),
    none("");

    private final String text;

    cheatCheckerMethods(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
