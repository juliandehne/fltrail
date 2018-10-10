package unipotsdam.gf.modules.assessment.controller.model;

import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.VereinfachtesResultSet;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class PACalculate {

    @Inject
    MysqlConnect connect;

    public List<Double> meanOfAssessments(String name, String projekt) {

    /*
    Berechnet aus Namen und ProjektId das arithmetische Mittel der Bewertungen und gibt
    eine Liste mit allen EInzelnoten und dem Mittel als letztem Eintrag zurück
     */

        double zwischenErgebnis = 0.0;
        double counter = 0.0;
        List<Double> results = new ArrayList<>();

        connect.connect();
        String mysqlRequest = "SELECT * FROM `assessments` WHERE `empfaengerId`=? AND `projektId`=?";

        String nameDesKandidaten = name;
        String projektDesKandidaten = projekt;
        VereinfachtesResultSet ausgabe = connect.issueSelectStatement(mysqlRequest, nameDesKandidaten, projektDesKandidaten);

        while (ausgabe.next()) {
            counter++;
            zwischenErgebnis = zwischenErgebnis + ausgabe.getInt("bewertung");
            results.add((double) ausgabe.getInt("bewertung"));
        }
        results.add(zwischenErgebnis / counter);
        connect.close();
        return results;

    }
}
