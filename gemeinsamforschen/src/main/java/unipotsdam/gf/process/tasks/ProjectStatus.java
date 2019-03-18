package unipotsdam.gf.process.tasks;

public class ProjectStatus {
    private int participants;
    private int participantsNeeded;
    private String dataName;
    private Boolean groupsFormed;
    private Boolean isAutomated;


    private static String PARTICIPANT_COUNT_NAME="PARTICIPANT_COUNT_NAME";

    public ProjectStatus() {
    }

    public ProjectStatus(int count, int participantsNeeded) {
        this.dataName = PARTICIPANT_COUNT_NAME;
        this.participants = count;
        this.participantsNeeded =  participantsNeeded;
    }

    public ProjectStatus(int participants) {
        this.participants = participants;
        this.participantsNeeded = 1;
        this.dataName = PARTICIPANT_COUNT_NAME;
    }

    public int getParticipants() {
        return participants;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public int getParticipantsNeeded() {
        return participantsNeeded;
    }

    public void setParticipantsNeeded(int participantsNeeded) {
        this.participantsNeeded = participantsNeeded;
    }

    public Boolean getGroupsFormed() {
        return groupsFormed;
    }

    public void setGroupsFormed(Boolean groupsFormed) {
        this.groupsFormed = groupsFormed;
    }

    public Boolean getAutomated() {
        return isAutomated;
    }

    public void setAutomated(Boolean automated) {
        isAutomated = automated;
    }
}
