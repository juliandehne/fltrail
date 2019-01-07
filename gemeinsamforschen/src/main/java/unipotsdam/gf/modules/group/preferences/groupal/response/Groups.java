package unipotsdam.gf.modules.group.preferences.groupal.response;

public class Groups
{
    private String groupID;

    private Results results;

    private String groupPerformanceIndex;

    private unipotsdam.gf.modules.group.preferences.groupal.response.Participants[] participants;

    public String getGroupID ()
    {
        return groupID;
    }

    public void setGroupID (String groupID)
    {
        this.groupID = groupID;
    }

    public Results getResults ()
    {
        return results;
    }

    public void setResults (Results results)
    {
        this.results = results;
    }

    public String getGroupPerformanceIndex ()
    {
        return groupPerformanceIndex;
    }

    public void setGroupPerformanceIndex (String groupPerformanceIndex)
    {
        this.groupPerformanceIndex = groupPerformanceIndex;
    }

    public unipotsdam.gf.modules.group.preferences.groupal.response.Participants[] getParticipants ()
    {
        return participants;
    }

    public void setParticipants (unipotsdam.gf.modules.group.preferences.groupal.response.Participants[] participants)
    {
        this.participants = participants;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [groupID = "+groupID+", results = "+results+", groupPerformanceIndex = "+groupPerformanceIndex+", participants = "+participants+"]";
    }
}
