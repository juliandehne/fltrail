package unipotsdam.gf.healthchecks;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HealthData {
    private Boolean CompBaseOnline;
    private Boolean RocketChatOnline;
    private Boolean MysqlOnline;

    private Boolean GroupAlOnline;

    public String getMysqlConnectionStatus() {
        return mysqlConnectionStatus;
    }

    public void setMysqlConnectionStatus(String mysqlConnectionStatus) {
        this.mysqlConnectionStatus = mysqlConnectionStatus;
    }

    private String mysqlConnectionStatus;

    public HealthData() {
    }

    public HealthData(Boolean compBaseOnline, Boolean rocketChatOnline, Boolean mysqlOnline, Boolean groupAlOnline,
                      String mysqlConnectionStatus) {
        CompBaseOnline = compBaseOnline;
        RocketChatOnline = rocketChatOnline;
        MysqlOnline = mysqlOnline;
        GroupAlOnline = groupAlOnline;
        this.mysqlConnectionStatus = mysqlConnectionStatus;
    }

    public Boolean getGroupAlOnline() {
        return GroupAlOnline;
    }

    public void setGroupAlOnline(Boolean groupAlOnline) {
        GroupAlOnline = groupAlOnline;
    }


    public Boolean getCompBaseOnline() {
        return CompBaseOnline;
    }

    public void setCompBaseOnline(Boolean compBaseOnline) {
        CompBaseOnline = compBaseOnline;
    }

    public Boolean getRocketChatOnline() {
        return RocketChatOnline;
    }

    public void setRocketChatOnline(Boolean rocketChatOnline) {
        RocketChatOnline = rocketChatOnline;
    }

    public Boolean getMysqlOnline() {
        return MysqlOnline;
    }

    public void setMysqlOnline(Boolean mysqlOnline) {
        MysqlOnline = mysqlOnline;
    }
}
