package unipotsdam.gf.process.tasks;

public class ParticipantsCount {
    private int participants;
    private String dataName;

    private static String PARTICIPANT_COUNT_NAME="PARTICIPANT_COUNT_NAME";

    public ParticipantsCount() {
        this.dataName = PARTICIPANT_COUNT_NAME;
    }

    public ParticipantsCount(int participants) {
        this.participants = participants;
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
}
