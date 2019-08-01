package unipotsdam.gf.config;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.healthchecks.HealthChecks;
import unipotsdam.gf.interfaces.*;
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
import unipotsdam.gf.modules.group.*;
import unipotsdam.gf.modules.group.learninggoals.CompBaseMatcher;
import unipotsdam.gf.modules.group.preferences.database.ProfileDAO;
import unipotsdam.gf.modules.group.preferences.groupal.PGroupAlMatcher;
import unipotsdam.gf.modules.group.preferences.survey.SurveyMapper;
import unipotsdam.gf.modules.group.random.RandomGroupAlgorithm;
import unipotsdam.gf.modules.portfolio.service.PortfolioService;
import unipotsdam.gf.modules.project.Management;
import unipotsdam.gf.modules.project.ManagementImpl;
import unipotsdam.gf.modules.project.ProjectConfigurationDAO;
import unipotsdam.gf.modules.project.ProjectDAO;
import unipotsdam.gf.modules.reflection.service.*;
import unipotsdam.gf.modules.submission.controller.SubmissionController;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.modules.wizard.PeerAssessmentSimulation;
import unipotsdam.gf.modules.wizard.Wizard;
import unipotsdam.gf.modules.wizard.WizardDao;
import unipotsdam.gf.mysql.MysqlConnect;
import unipotsdam.gf.mysql.MysqlConnectImpl;
import unipotsdam.gf.process.*;
import unipotsdam.gf.process.constraints.ConstraintsImpl;
import unipotsdam.gf.process.phases.PhasesImpl;
import unipotsdam.gf.process.tasks.Task;
import unipotsdam.gf.process.tasks.TaskDAO;
import unipotsdam.gf.process.tasks.TaskMapper;
import unipotsdam.gf.session.GFContext;
import unipotsdam.gf.session.GFContexts;
import unipotsdam.gf.session.Lock;

public class GFApplicationBinder extends AbstractBinder {

    private final static Logger log = LoggerFactory.getLogger(GFApplicationBinder.class);

    /**
     * TODO replace DummyImplementation
     */
    @Override
    protected void configure() {

        // check if rocket chat is online
        Boolean rocketOnline = HealthChecks.isRocketOnline();
        if (rocketOnline) {
            bind(CommunicationService.class).to(ICommunication.class);
        } else {
            bind(DummyCommunicationService.class).to(ICommunication.class);
            log.trace("Rocket Chat is not online. Removing chat capabilities");
        }
        // TODO: sort by phases for a better overview
        bind(EmailService.class).to(EmailService.class);
        bind(ManagementImpl.class).to(Management.class);
        bind(PeerAssessmentProcess.class).to(PeerAssessmentProcess.class);
        bind(PhasesImpl.class).to(IPhases.class);
        bind(GFContext.class).to(GFContext.class);
        bind(ManagementImpl.class).to(Management.class);
        bind(GroupfindingImpl.class).to(IGroupFinding.class);
        bind(AssessmentDAO.class).to(AssessmentDAO.class);
        bind(GFContexts.class).to(GFContexts.class);
        bind(ProjectCreationProcess.class).to(ProjectCreationProcess.class);
        bind(GroupFormationProcess.class).to(GroupFormationProcess.class);
        bind(ConstraintsImpl.class).to(ConstraintsImpl.class);
        bind(DossierCreationProcess.class).to(DossierCreationProcess.class);
        bind(SubmissionController.class).to(SubmissionController.class);
        bind(AnnotationController.class).to(AnnotationController.class);
        bind(ProjectConfigurationDAO.class).to(ProjectConfigurationDAO.class);
        bind(UserDAO.class).to(UserDAO.class);
        bind(ProjectDAO.class).to(ProjectDAO.class);
        bind(GroupDAO.class).to(GroupDAO.class);
        bind(TaskDAO.class).to(TaskDAO.class);
        bind(FeedbackImpl.class).to(Feedback.class);
        bind(UnirestService.class).to(UnirestService.class);
        bind(ProfileDAO.class).to(ProfileDAO.class);
        bind(SurveyMapper.class).to(SurveyMapper.class);
        //bind(GroupAlMatcher.class).to(GroupAlMatcher.class);
        bind(PGroupAlMatcher.class).to(PGroupAlMatcher.class);
        bind(CompBaseMatcher.class).to(CompBaseMatcher.class);
        bind(BigGroupMatcher.class).to(BigGroupMatcher.class);
        bind(GroupFormationFactory.class).to(GroupFormationFactory.class);
        bind(RandomGroupAlgorithm.class).to(RandomGroupAlgorithm.class);
        bind(SingleGroupMatcher.class).to(SingleGroupMatcher.class);
        bind(SurveyProcess.class).to(SurveyProcess.class);
        bind(FileManagementService.class).to(FileManagementService.class);
        bind(FileManagementDAO.class).to(FileManagementDAO.class);
        bind(AssessmentDAO.class).to(AssessmentDAO.class);
        bind(PortfolioProcess.class).to(PortfolioProcess.class);
        bind(TaskMapper.class).to(TaskMapper.class);
        bind(PeerAssessmentImpl.class).to(IPeerAssessment.class);
        bind(Task.class).to(Task.class);
        bind(WizardDao.class).to(WizardDao.class);
        bind(Wizard.class).to(Wizard.class);
        bind(Lock.class).to(Lock.class);
        bind(PeerAssessmentSimulation.class).to(PeerAssessmentSimulation.class);
        bind(EvaluationDAO.class).to(EvaluationDAO.class);
        bind(ExecutionProcess.class).to(ExecutionProcess.class);

        /*
         * TODO: @Martin comment in for your development
         */
        bind(DummyExecutionProcess.class).to(IExecutionProcess.class);
        //bind(ExecutionProcess.class).to(IExecutionProcess.class);
        bind(PortfolioService.class).to(IPortfolioService.class);
        bind(ReflectionQuestionDAO.class).to(ReflectionQuestionDAO.class);
        bind(ReflectionQuestionsStoreDAO.class).to(ReflectionQuestionsStoreDAO.class);
        bind(LearningGoalStoreDAO.class).to(LearningGoalStoreDAO.class);
        bind(LearningGoalsDAO.class).to(LearningGoalsDAO.class);
        bind(ReflectionService.class).to(IReflection.class);
        bind(LearningGoalStudentResultsDAO.class).to(LearningGoalStudentResultsDAO.class);
        bindMore();
    }

    protected void bindMore() {
        bind(MysqlConnectImpl.class).to(MysqlConnect.class);
        bind(MysqlConnect.class).to(MysqlConnect.class);
        bind(GroupfindingImpl.class).to(IGroupFinding.class);
        bind(UnirestService.class).to(UnirestService.class);
        bind(ContributionFeedbackService.class).to(IContributionFeedback.class);
        bind(ContributionFeedbackDAO.class).to(ContributionFeedbackDAO.class);
    }
}
