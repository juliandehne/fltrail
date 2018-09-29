package unipotsdam.gf.modules.researchreport.service;

import unipotsdam.gf.core.database.mysql.MysqlConnect;
import unipotsdam.gf.modules.researchreport.model.ResearchReportSection;

public class ResearchReportSectionDAO {

    private MysqlConnect mysqlConnect;

    public ResearchReportSectionDAO(MysqlConnect mysqlConnect) {
        this.mysqlConnect = mysqlConnect;
    }

    public void persist(ResearchReportSection researchReportSection) {
        String mysql = "INSERT INTO researchreport (content, category, groupId, projectId) values(?,?,?,?)";
        mysqlConnect.connect();
        mysqlConnect.issueInsertOrDeleteStatement(mysql, researchReportSection.getContent(),
                researchReportSection.getCategory().name(), researchReportSection.getGroupId(),
                researchReportSection.getProjectName());
        mysqlConnect.close();
    }
}

