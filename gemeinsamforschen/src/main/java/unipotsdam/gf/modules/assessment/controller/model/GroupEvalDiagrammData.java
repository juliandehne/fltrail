package unipotsdam.gf.modules.assessment.controller.model;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.core.database.mysql.VereinfachtesResultSet;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/assessments4")

public class GroupEvalDiagrammData {
    private String type;
    private GroupEvalDataList data;
    private GroupEvalOption option;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GroupEvalDataList getData() {
        return data;
    }

    public void setData(GroupEvalDataList data) {
        this.data = data;
    }
    public GroupEvalDiagrammData(){}

    public GroupEvalDiagrammData(String type, GroupEvalDataList data){
        this.type=type;
        this.data=data;
    }

    public GroupEvalOption getOption() {
        return option;
    }

    public void setOption(GroupEvalOption option) {
        this.option = option;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/diagramm1/{projectId}")
    public GroupEvalDiagrammData getValuesFromDBByProjectID(@PathParam("projectId") String projectId)
    {

        //GruppenID muss noch irgendwie aus der Seite ausgelesen werden, wenn die dann mal dynamisch gefüllt wurde
        GroupEvalDiagrammData diagramm= new GroupEvalDiagrammData();
        diagramm.setType("line");
        GroupEvalOption option=new GroupEvalOption();
        GroupEvalDiagrammLegende legende =new GroupEvalDiagrammLegende();
        legende.setDisplay(false);
        option.setLegende(legende);
        diagramm.setOption(option);
        MysqlConnect connect = new MysqlConnect();
        List<String> userNamen=new ArrayList<>();
        GroupEvalDataDatasets datenSaetze = new GroupEvalDataDatasets();
        GroupEvalDataList datenDia = new GroupEvalDataList();
        connect.connect();

        String mysqlRequestGroupuser = "SELECT * FROM `groupuser` WHERE `groupId`=? ";

        VereinfachtesResultSet namenDerUser = connect.issueSelectStatement(mysqlRequestGroupuser,3);
        List<Integer> bewertungenZwischen = new ArrayList<Integer>();
        List<String> labelZwischen = new ArrayList<String>();


        while (namenDerUser.next()){
            userNamen.add(namenDerUser.getString("userEmail"));

        }
        for (String anUserNamen : userNamen) {
            String mysqlRequestAssessment = "SELECT * FROM `assessments` WHERE `empfaengerId`=?";
            VereinfachtesResultSet bewertungDerUser = connect.issueSelectStatement(mysqlRequestAssessment, anUserNamen);

            while (bewertungDerUser.next()) {
                bewertungenZwischen.add(bewertungDerUser.getInt("bewertung"));
                labelZwischen.add(String.valueOf(bewertungDerUser.getTimestamp("deadline")).substring(0,10));
            }
            int[] hilfeDaten = new int[bewertungenZwischen.size()];
            for (int z = 0; z < labelZwischen.size(); z++) {
                hilfeDaten[z] = bewertungenZwischen.get(z);
            }
            datenSaetze.setData(hilfeDaten);
            datenSaetze.setLabel(anUserNamen);
            datenDia.appendDataSet(datenSaetze);

            String[] hilfeLabel = new String[labelZwischen.size()];
            for (int z = 0; z < labelZwischen.size(); z++) {
                hilfeLabel[z] = labelZwischen.get(z);
            }
            datenDia.setLabels(hilfeLabel);
            System.out.println(Arrays.toString(datenSaetze.getData()));
            System.out.println(labelZwischen);
            System.out.println(anUserNamen);
            bewertungenZwischen.clear();
            labelZwischen.clear();
        }
        connect.close();
        diagramm.setData(datenDia);


        return diagramm;
    }
}
