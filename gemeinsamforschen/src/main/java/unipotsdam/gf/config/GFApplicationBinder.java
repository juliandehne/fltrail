package unipotsdam.gf.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.healthchecks.HealthChecks;
import unipotsdam.gf.interfaces.Feedback;
import unipotsdam.gf.interfaces.ICommunication;
import unipotsdam.gf.interfaces.IContributionFeedback;
import unipotsdam.gf.interfaces.IGroupFinding;
import unipotsdam.gf.interfaces.IPeerAssessment;
import unipotsdam.gf.interfaces.IPhases;
import unipotsdam.gf.interfaces.IReflection;
import unipotsdam.gf.modules.annotation.controller.AnnotationController;
import unipotsdam.gf.modules.annotation.controller.FeedbackImpl;
import unipotsdam.gf.modules.assessment.AssessmentDAO;
import unipotsdam.gf.modules.assessment.PeerAssessmentImpl;
import unipotsdam.gf.modules.communication.DummyCommunicationService;
import unipotsdam.gf.modules.communication.service.CommunicationService;
import unipotsdam.gf.modules.communication.service.EmailService;
import unipotsdam.gf.modules.communication.service.UnirestService;
import unipotsdam.gf.modules.contributionFeedback.service.ContributionFeedbackDAO;
import unipotsdam.gf.modules.contributionFeedback.service.ContributionFeedbackService;
import unipotsdam.gf.modules.evaluation.EvaluationDAO;
import unipotsdam.gf.modules.fileManagement.FileManagementDAO;
import unipotsdam.gf.modules.fileManagement.FileManagementService;
import unipotsdam.gf.modules.group.BigGroupMatcher;
import unipotsdam.gf.modules.group.GroupDAO;
import unipotsdam.gf.modules.group.GroupFormationFactory;
import unipotsdam.gf.modules.group.GroupfindingImpl;
import unipotsdam.gf.modules.group.SingleGroupMatcher;
import unipotsdam.gf.modules.group.learninggoals.CompBaseMatcher;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.excel.ItemWriter;
import unipotsdam.gf.modules.group.preferences.groupal.PGroupAlMatcher;
import unipotsdam.gf.modules.group.preferences.survey.SurveyMapper;
import unipotsdam.gf.modules.group.random.RandomGroupAlgorithm;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.ManagementImpl;
import unipotsdam.gf.modules.project.ProjectConfigurationDAO;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.reflection.service.LearningGoalStoreDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionAnswersDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionQuestionsStoreDAO;
import unipotsdam.gf.modules.reflection.service.ReflectionService;
import unipotsdam.gf.modules.reflection.service.SelectedLearningGoalsDAO;
import unipotsdam.gf.modules.reflection.service.SelectedReflectionQuestionsDAO;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.wizard.DummyReflectionPhasesimulation;
import unipotsdam.gf.modules.wizard.IReflectionPhaseSimulation;
import unipotsdam.gf.modules.wizard.PeerAssessmentSimulation;
import unipotsdam.gf.modules.wizard.ReflectionPhaseSimulation;
import unipotsdam.gf.modules.wizard.Wizard;
import unipotsdam.gf.modules.wizard.WizardDao;
import unipotsdam.gf.mysql.ConnectionPoolUtility;
import unipotsdam.gf.mysql.IConnectionPoolUtility;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.PoolingMysqlConnectImpl;
import unipotsdam.gf.process.DossierCreationProcess;
import unipotsdam.gf.process.DummyExecutionProcess;
import unipotsdam.gf.process.ExecutionProcess;
import unipotsdam.gf.process.GroupFormationProcess;
import unipotsdam.gf.process.IExecutionProcess;
import unipotsdam.gf.process.PeerAssessmentProcess;
import unipotsdam.gf.process.ProjectCreationProcess;
import unipotsdam.gf.process.SurveyProcess;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.PhasesImpl;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskMapper;
import unipotsdam.gf.session.GFContext;
import unipotsdam.gf.session.GFContexts;
import unipotsdam.gf.session.Lock;

import javax.inject.Singleton;

import static unipotsdam.gf.config.FLTrailConfig.REFLECTION_MODULE_ENABLED;

public class GFApplicationBinder extends AbstractBinder {

    private final static Logger log = LoggerFactory.getLogger(GFApplicationBinder.class);

    /**
     * @see unipotsdam.gf.config.GFApplicationBinderFactory
     */
    @Deprecated
    GFApplicationBinder() {
    }

    @Override
    protected void configure() {

        if (FLTrailConfig.productionContext) {
            if (FLTrailConfig.staging) {
                bind(StagingConfig.class).to(IConfig.class);
            } else {
                bind(ProductionConfig.class).to(IConfig.class);
            }
        } else {
            bind(TestConfig.class).to(IConfig.class);
        }



        // check if rocket chat is online
        Boolean rocketOnline = HealthChecks.isRocketOnline();
        if (rocketOnline) {
            bind(CommunicationService.class).to(ICommunication.class);
        } else {
            bind(DummyCommunicationService.class).to(ICommunication.class);
            log.trace("Rocket Chat is not online. Removing chat capabilities");
        }

        bindUtility();

        bindSurvey();

        bindPhases();

        bindWizard();

        bind(ItemWriter.class).to(ItemWriter.class);

    }

    private void bindWizard() {
        bind(WizardDao.class).to(WizardDao.class);
        bind(Wizard.class).to(Wizard.class);
        bind(PeerAssessmentSimulation.class).to(PeerAssessmentSimulation.class);

    }

    private void bindProjectFinished() {
        bind(EvaluationDAO.class).to(EvaluationDAO.class);
    }

    private void bindAssessment() {
        bind(PeerAssessmentProcess.class).to(PeerAssessmentProcess.class);
        bind(AssessmentDAO.class).to(AssessmentDAO.class);
        bind(PeerAssessmentImpl.class).to(IPeerAssessment.class);
    }

    private void bindExecution() {
        if (REFLECTION_MODULE_ENABLED) {
            bind(ExecutionProcess.class).to(IExecutionProcess.class);
            bind(ReflectionPhaseSimulation.class).to(IReflectionPhaseSimulation.class);
        } else {
            bind(DummyExecutionProcess.class).to(IExecutionProcess.class);
            bind(DummyReflectionPhasesimulation.class).to(IReflectionPhaseSimulation.class);
        }
        bind(ReflectionQuestionAnswersDAO.class).to(ReflectionQuestionAnswersDAO.class);
        bind(ReflectionQuestionsStoreDAO.class).to(ReflectionQuestionsStoreDAO.class);
        bind(LearningGoalStoreDAO.class).to(LearningGoalStoreDAO.class);
        bind(SelectedLearningGoalsDAO.class).to(SelectedLearningGoalsDAO.class);
        bind(SelectedReflectionQuestionsDAO.class).to(SelectedReflectionQuestionsDAO.class);
        bind(ReflectionService.class).to(IReflection.class);
    }

    private void bindDossierFeedback() {
        bind(DossierCreationProcess.class).to(DossierCreationProcess.class);
        bind(ConstraintsImpl.class).to(ConstraintsImpl.class);
        bind(SubmissionController.class).to(SubmissionController.class);    //holds portfolio and dossier stuff
        bind(AnnotationController.class).to(AnnotationController.class);
        bind(FeedbackImpl.class).to(Feedback.class);
    }

    private void bindGroupFinding() {
        bind(ProjectCreationProcess.class).to(ProjectCreationProcess.class);
        bind(GroupfindingImpl.class).to(IGroupFinding.class);
        bind(GroupFormationProcess.class).to(GroupFormationProcess.class);
        bind(PGroupAlMatcher.class).to(PGroupAlMatcher.class);
        bind(CompBaseMatcher.class).to(CompBaseMatcher.class);
        bind(BigGroupMatcher.class).to(BigGroupMatcher.class);
        bind(GroupFormationFactory.class).to(GroupFormationFactory.class);
        bind(RandomGroupAlgorithm.class).to(RandomGroupAlgorithm.class);
        bind(SingleGroupMatcher.class).to(SingleGroupMatcher.class);
    }

    private void bindPhases() {
        bind(PhasesImpl.class).to(IPhases.class);
        bind(TaskMapper.class).to(TaskMapper.class);
        bind(Task.class).to(Task.class);
        bindGroupFinding();
        bindDossierFeedback();
        bindExecution();
        bindAssessment();
        bindProjectFinished();
    }

    private void bindSurvey() {
        bind(SurveyMapper.class).to(SurveyMapper.class);
        bind(SurveyProcess.class).to(SurveyProcess.class);
    }

    private void bindCommunication() {
        bind(EmailService.class).to(EmailService.class);
    }

    private void bindUtility() {
        bind(ManagementImpl.class).to(Management.class);
        bind(GFContext.class).to(GFContext.class);
        bind(GFContexts.class).to(GFContexts.class);
        bind(UnirestService.class).to(UnirestService.class);
        bind(FileManagementService.class).to(FileManagementService.class);
        bind(Lock.class).to(Lock.class);
        bindDBConnections();
        bindCommunication();
    }

    protected void bindDBConnections() {
        bind(ConnectionPoolUtility.class).to(IConnectionPoolUtility.class).in(Singleton.class);
        bind(PoolingMysqlConnectImpl.class).to(MysqlConnect.class);
        //bind(MysqlConnect.class).to(MysqlConnect.class);
        bind(GroupfindingImpl.class).to(IGroupFinding.class);
        bind(UnirestService.class).to(UnirestService.class);
        bind(ContributionFeedbackService.class).to(IContributionFeedback.class);
        bind(ContributionFeedbackDAO.class).to(ContributionFeedbackDAO.class);
        bind(UserDAO.class).to(UserDAO.class);
        bind(ProjectConfigurationDAO.class).to(ProjectConfigurationDAO.class);
        bind(ProjectDAO.class).to(ProjectDAO.class);
        bind(GroupDAO.class).to(GroupDAO.class);
        bind(TaskDAO.class).to(TaskDAO.class);
        bind(ProfileDAO.class).to(ProfileDAO.class);
        bind(FileManagementDAO.class).to(FileManagementDAO.class);
    }
}
