package unipotsdam.gf.modules.wizard;

import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.process.phases.Phase;
import unipotsdam.gf.process.tasks.TaskName;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class Wizard {

    @Inject
    WizardDao wizardDao;


    public void doSpells(TaskName taskName, Project project) {
        switch (TaskName.WAIT_FOR_PARTICPANTS) {
            // ...
        }
    }

    public void doSpells(Phase phase, Project project) {
        switch (phase){

        }
    }

    public void createStudents(Project project) {

    }

    public void skipGroupfinding(Project project) {

    }

    public void createDossiers(Project project) {

    }

    public void annotateDossiers(Project project) {

    }

    public void generateFeedbacks(Project project) {

    }

    public void finalizeDossiers(Project project) {

    }

    public void skipConceptPhase(Project project) {

    }

    public void skipReflexionPhase(Project project) {

    }

    public void generatePresentationsForAllGroupsAndUploadThem(Project project) {

    }

    public void generateFinalReportsForAllGroupsAndUploadThem(Project project) {

    }

    public void externalPeerAssessments(Project project) {

    }

    public void internalPeerAssessments(Project project) {

    }

    public void docentAssessments(Project project) {

    }

    public void skipPeerAssessment() {

    }


    public List<WizardProject> getProjects() {
        List<WizardProject> projects =  wizardDao.getProjects();
        List<WizardProject> result =  new ArrayList<>();
        // TODO
        return result;
    }
}


