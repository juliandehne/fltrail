package unipotsdam.gf.modules.assessment;

import org.junit.Test;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.controller.model.*;
import unipotsdam.gf.modules.assessment.controller.service.FBAssessement;

import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.getString;

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
        //Performance performance = new Performance(student, quizAnswers,"so ein toller Typ", workRating);
        //Assessment assessment = new Assessment(student, performance);
        //iPeerAssessment.addAssessmentDataToDB(assessment);
    }
    @Test
    public void meanOfAssessments(){
        double Ergebnis=0.0;
        double zwischenErgebnis=0.0;
        double counter=0.0;
        List<Double> results = new ArrayList<>();
        MysqlConnect connect = new MysqlConnect();

        connect.connect();
        String mysqlRequest = "SELECT * FROM `assessments` WHERE `empfaengerId`=? AND `projektId`=?";
        String test = "fgnxn";
        String test2 = "projekt";
        VereinfachtesResultSet ausgabe = connect.issueSelectStatement(mysqlRequest,test, test2);
        while (ausgabe.next()){
            counter++;
            zwischenErgebnis=zwischenErgebnis+ausgabe.getInt("bewertung");
            results.add((double) ausgabe.getInt("bewertung"));
        }
        results.add(zwischenErgebnis/counter);
        System.out.println(results);
        //Integer bewertung = ausgabe.getInt("bewertung");
        connect.close();
    }
    @Test
    public void groupDatafromDB(){
        MysqlConnect connect = new MysqlConnect();
        List<String> userNamen=new ArrayList<>();
        GroupEvalDataDatasets datenSaetze = new GroupEvalDataDatasets();
        GroupEvalDataList datenDia = new GroupEvalDataList();
        connect.connect();

        String mysqlRequestGroupuser = "SELECT * FROM `groupuser` WHERE `groupId`=? ";

        VereinfachtesResultSet namenDerUser = connect.issueSelectStatement(mysqlRequestGroupuser,3);
        int[] bewertungenZwischen=new int[10];
        while (namenDerUser.next()){
            userNamen.add(namenDerUser.getString("userEmail"));
        }
        for (int i=0;i<userNamen.size();i++){
            String mysqlRequestAssessment = "SELECT * FROM `assessments` WHERE `empfaengerId`=?";
            VereinfachtesResultSet bewertungDerUser = connect.issueSelectStatement(mysqlRequestAssessment,userNamen.get(i));

            while (bewertungDerUser.next()) {
                //bewertungenZwischen.add(bewertungDerUser.getInt("bewertung"));
                System.out.println("Hass");
                }
            datenSaetze.setData(bewertungenZwischen);
            //datenSaetze.setLabel(userNamen.get(i));
            datenDia.appendDataSet(datenSaetze);
            System.out.println(datenSaetze.getData());
            System.out.println(datenSaetze.getLabel());

            }
        connect.close();
    }


}
