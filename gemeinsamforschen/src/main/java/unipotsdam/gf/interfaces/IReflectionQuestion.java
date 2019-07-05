package unipotsdam.gf.interfaces;

import unipotsdam.gf.modules.reflection.model.ReflectionQuestion;
import unipotsdam.gf.modules.submission.model.FullSubmission;

public interface IReflectionQuestion {

    void saveAnswerReference(FullSubmission fullSubmission, ReflectionQuestion reflectionQuestion);
}
