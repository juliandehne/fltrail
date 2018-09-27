package unipotsdam.gf.modules.coAssessment;
import com.mysql.jdbc.Statement;
import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PACalculate {

public List<Double> meanOfAssessments(String name, String projekt){
    /*
    Berechnet aus Namen und ProjektId das arithmetische Mittel der Bewertungen und gibt
    eine Liste mit allen EInzelnoten und dem Mittel als letztem Eintrag zurück
     */

    double zwischenErgebnis=0.0;
    double counter=0.0;
    List<Double> results = new ArrayList<>();
    MysqlConnect connect = new MysqlConnect();

    connect.connect();
    String mysqlRequest = "SELECT * FROM `assessments` WHERE `empfaengerId`=? AND `projektId`=?";

    String nameDesKandidaten = name;
    String projektDesKandidaten = projekt;
    VereinfachtesResultSet ausgabe = connect.issueSelectStatement(mysqlRequest,nameDesKandidaten, projektDesKandidaten);

    while (ausgabe.next()){
        counter++;
        zwischenErgebnis=zwischenErgebnis+ausgabe.getInt("bewertung");
        results.add((double) ausgabe.getInt("bewertung"));
    }
    results.add(zwischenErgebnis/counter);
    connect.close();
    return results;

}
}
