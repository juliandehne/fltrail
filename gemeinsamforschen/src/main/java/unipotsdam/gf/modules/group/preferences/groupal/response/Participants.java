package unipotsdam.gf.modules.group.preferences.groupal.response;

import org.codehaus.jackson.annotate.JsonProperty;

public class Participants
{
    @JsonProperty("criteria")
    private Criteria[] criteria;

    @JsonProperty("iD")
    private String iD;


    public Criteria[] getCriteria ()
    {
        return criteria;
    }

    public void setCriteria (Criteria[] criteria)
    {
        this.criteria = criteria;
    }

    public String getiD()
    {
        return iD;
    }

    public void setiD(String iD)
    {
        this.iD = iD;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [criteria = "+criteria+", iD = "+ iD +"]";
    }
}