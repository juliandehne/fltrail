package unipotsdam.gf.modules.assessment;

import org.junit.Test;
import unipotsdam.gf.core.management.user.User;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.Assessment;
import unipotsdam.gf.modules.assessment.controller.service.FBAssessement;
import unipotsdam.gf.modules.assessment.controller.model.Performance;
import unipotsdam.gf.modules.assessment.controller.model.StudentIdentifier;

public class TestAddAssessment {

    @Test
    public void addTestAssessment() {
        IPeerAssessment iPeerAssessment = new FBAssessement();
        int [] quizAnswers = new int[5];
        quizAnswers[0] = 0;
        quizAnswers[1] = 1;
        quizAnswers[2] = 0;
        quizAnswers[3] = 1;
        quizAnswers[4] = 1;
        int [] workRating = new int[3];
        workRating[0] = 5;      //Führungsqualität
        workRating[1] = 1;      //Pünktlichkeit
        workRating[2] = 4;      //Hilfsbereitschaft oder so

        StudentIdentifier student = new StudentIdentifier("Spaß", "Haralf");
        Performance performance = new Performance(student, quizAnswers,"so ein toller Typ", workRating);
        Assessment assessment = new Assessment(student, performance);
        iPeerAssessment.addAssessmentDataToDB(assessment);
    }
    @Test
    public void addTestAss(){
        User tim;//entschuldige das freche klauen
        tim = new User("Max Troll","123456","d4rk@gbz.de",true);
        int [] quizAnswers = new int[5];
        quizAnswers[0] = 4;
        quizAnswers[1] = 5;
        quizAnswers[2] = 6;
        quizAnswers[3] = 7;
        quizAnswers[4] = 8;
        int [] workRating = new int[3];
        workRating[0] = 5;
        workRating[1] = 1;
        workRating[2] = 4;
        StudentIdentifier student = new StudentIdentifier("Test", "Case");
        Performance performance = new Performance(student, quizAnswers,"so ein toller Typ", workRating);
        Assessment assessment = new Assessment(student, performance);
        assessment.setAssessment(tim,assessment);

    }
}
