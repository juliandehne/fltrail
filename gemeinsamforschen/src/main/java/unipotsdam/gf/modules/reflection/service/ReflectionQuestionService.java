package unipotsdam.gf.modules.reflection.service;

import unipotsdam.gf.interfaces.IReflectionQuestion;
import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.submission.model.FullSubmission;

import javax.annotation.ManagedBean;
import javax.annotation.Resource;
import javax.inject.Inject;

@ManagedBean
@Resource
public class ReflectionQuestionService implements IReflectionQuestion {

    @Inject
    private ReflectionQuestionDAO reflectionQuestionDAO;

    @Override
    public void saveAnswerReference(FullSubmission fullSubmission, ReflectionQuestion reflectionQuestion) {
        reflectionQuestionDAO.saveAnswerReference(fullSubmission, reflectionQuestion);
    }
}
