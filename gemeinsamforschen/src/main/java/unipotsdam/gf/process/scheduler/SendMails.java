package unipotsdam.gf.process.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.communication.Messages;
import unipotsdam.gf.modules.communication.service.EmailService;
import unipotsdam.gf.modules.project.Project;

public class SendMails implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(SendMails.class);
    private final Project project;
    private final EmailService emailService;

    public SendMails(Project project, EmailService emailService) {
        this.project = project;
        this.emailService = emailService;
    }


    @Override
    public void run() {
        log.debug("Nach 3 Wochen Email an Gruppenmitglieder.");
        emailService.sendMessageToUsers(project, Messages.SurveyEvaluation(project));
    }
}
