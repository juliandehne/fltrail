package unipotsdam.gf.healthchecks;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HealthData {
    private Boolean CompBaseOnline;
    private Boolean RocketChatOnline;
    private Boolean MysqlOnline;

    public HealthData() {
    }

    public HealthData(Boolean compBaseOnline, Boolean rocketChatOnline, Boolean mysqlOnline) {
        CompBaseOnline = compBaseOnline;
        RocketChatOnline = rocketChatOnline;
        MysqlOnline = mysqlOnline;
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
