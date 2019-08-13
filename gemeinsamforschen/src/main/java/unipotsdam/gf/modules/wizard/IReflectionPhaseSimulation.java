package unipotsdam.gf.modules.wizard;

import unipotsdam.gf.modules.project.Project;

public interface IReflectionPhaseSimulation {
    void simulateQuestionSelection(Project project) throws Exception;

    void simulateCreatingPortfolioEntries(Project project) throws Exception;

    void simulateDocentFeedback(Project project);

    void simulateChoosingPortfolioEntries(Project project) throws Exception;

    void simulateAnsweringReflectiveQuestions(Project project) throws Exception;
}
