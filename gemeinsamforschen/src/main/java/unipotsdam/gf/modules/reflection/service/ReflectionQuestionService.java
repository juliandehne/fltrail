package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.interfaces.IReflectionQuestion;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.submission.model.FullSubmission;

import javax.inject.Inject;

public class ReflectionQuestionService implements IReflectionQuestion {

    @Inject
    private ReflectionQuestionDAO reflectionQuestionDAO;

    @Override
    public void saveAnswerReference(FullSubmission fullSubmission, ReflectionQuestion reflectionQuestion) {
        reflectionQuestionDAO.saveAnswerReference(fullSubmission, reflectionQuestion);
    }
}
