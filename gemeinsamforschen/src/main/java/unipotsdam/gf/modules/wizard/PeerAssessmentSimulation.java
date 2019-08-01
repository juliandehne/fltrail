package unipotsdam.gf.modules.wizard;

import com.itextpdf.text.DocumentException;
import de.svenjacobs.loremipsum.LoremIpsum;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.modules.assessment.InternalAssessmentQuestions;
import unipotsdam.gf.modules.assessment.QuestionData;
import unipotsdam.gf.modules.fileManagement.FileManagementDAO;
import unipotsdam.gf.modules.fileManagement.FileManagementService;
import unipotsdam.gf.modules.fileManagement.FileRole;
import unipotsdam.gf.modules.fileManagement.FileType;
import unipotsdam.gf.modules.group.Group;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.wizard.compbase.ConceptImporter;
import unipotsdam.gf.process.PeerAssessmentProcess;
import unipotsdam.gf.process.tasks.Progress;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class PeerAssessmentSimulation {

    private final ConceptImporter conceptImporter;
    private final LoremIpsum loremIpsum;

    @Inject
    private WizardDao wizardDao;


    @Inject
    private TaskDAO taskDAO;

    @Inject
    UserDAO userDAO;

    @Inject
    private GroupDAO groupDAO;

    @Inject
    private PeerAssessmentProcess peerAssessmentProcess;

    @Inject
    private IPeerAssessment peer;

    @Inject
    private FileManagementDAO fileManagementDAO;

    @Inject
    private FileManagementService fileManagementService;


    public PeerAssessmentSimulation() throws UnsupportedEncodingException {
        this.conceptImporter = new ConceptImporter();
        this.loremIpsum = new LoremIpsum();
    }

    void generatePresentationsForAllGroupsAndUploadThem(Project project)
            throws DocumentException, IOException {
        peerAssessmentProcess.startPeerAssessmentPhase(project);

        FileRole fileRole = FileRole.PRESENTATION;
        createMissingFiles(project, fileRole);
        taskDAO.updateForAll(new Task(TaskName.UPLOAD_PRESENTATION, null, project, Progress.FINISHED));
    }

    void generateFinalReportsForAllGroupsAndUploadThem(Project project) throws Exception {
        FileRole fileRole = FileRole.FINAL_REPORT;
        createMissingFiles(project, fileRole);
        taskDAO.updateForAll(new Task(TaskName.UPLOAD_FINAL_REPORT, null, project, Progress.FINISHED));
        // start the student assessment process
        if (!wizardDao.getWizardrelevantTaskStatus(project).contains(TaskName.GIVE_EXTERNAL_ASSESSMENT)) {
            peerAssessmentProcess.startStudentAssessments(project);
        }
    }

    private void createMissingFiles(Project project, FileRole fileRole) throws IOException, DocumentException {
        List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());
        for (Group group : groupsByProjectName) {
            User representativUser = groupDAO.getRepresentativUser(group, project);
            if (!fileManagementDAO.fileExists(project, fileRole, group)) {
                generateFile(project, representativUser, fileRole);
            }
        }
    }

    void externalPeerAssessments(Project project) throws Exception {
        List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());
        for (Group group : groupsByProjectName) {
            Integer groupToRate = peer.whichGroupToRate(project, group.getId());
            List<User> members = group.getMembers();
            for (User member : members) {
                Map<FileRole, Integer> contributionRating = generateContributionRatings();
                peerAssessmentProcess
                        .postContributionRating(contributionRating, groupToRate + "", project, member.getEmail(), true);
            }
        }
    }

    private Map<FileRole, Integer> generateContributionRatings() {
        Map<FileRole, Integer> contributionRating = new HashMap<>();
        FileRole[] values = FileRole.values();
        for (FileRole value : values) {
            Random random = new Random();
            contributionRating.put(value, random.nextInt(4) + 1);
        }
        return contributionRating;
    }

    void internalPeerAssessments(Project project) throws Exception {
        List<User> usersByProjectName = userDAO.getUsersByProjectName(project.getName());
        for (User user : usersByProjectName) {
            doInternalAssessment(user, project);
        }

    }

    private void doInternalAssessment(User user, Project project) throws Exception {
        User nextUserToRateInternally = peerAssessmentProcess.getNextUserToRateInternally(project, user);
        // need different break condition then tasks
        if (nextUserToRateInternally != null) {
            HashMap<String, Integer> data = new HashMap<>();
            generateInternalFakeData(data);
            peerAssessmentProcess.persistInternalAssessment(project, user, nextUserToRateInternally, data);
            doInternalAssessment(user, project);
        }

    }

    private void generateInternalFakeData(HashMap<String, Integer> data) {
        InternalAssessmentQuestions internalAssessmentQuestions = new InternalAssessmentQuestions();
        ArrayList<QuestionData> theQuestions = internalAssessmentQuestions.getTheQuestions();
        Random random = new Random();
        for (QuestionData theQuestion : theQuestions) {
            int rating = random.nextInt(4) + 1;
            data.put(theQuestion.getKey(), rating);
        }
    }

    void docentAssessments(Project project) throws Exception {
        List<Group> groupsByProjectName = groupDAO.getGroupsByProjectName(project.getName());

        for (Group group : groupsByProjectName) {
            Map<FileRole, Integer> contributionRating = generateContributionRatings();
            peerAssessmentProcess
                    .postContributionRating(contributionRating, group.getId() + "", project, project.getAuthorEmail(),
                            false);
        }
    }

    private void generateFile(Project project, User representativUser, FileRole fileRole)
            throws IOException, DocumentException {
        // file content
        String fileName = conceptImporter.getNumberedConcepts(3).stream().reduce((x, y) -> x + y).get();
        // html content as string
        String content = loremIpsum.getWords(300);
        String wrapper = "<!DOCTYPE html><html lang=\"en\"><body><span>" + content + "</span></body></html>";
        fileManagementService.deleteFiles(project, representativUser, fileRole);
        fileManagementService.uploadFile(representativUser, project, fileName, fileRole, wrapper, FileType.HTML);
        peerAssessmentProcess.fileHasBeenUploaded(fileRole, representativUser, project);

    }
}
