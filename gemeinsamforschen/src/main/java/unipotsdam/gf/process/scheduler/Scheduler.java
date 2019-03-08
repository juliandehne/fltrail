package unipotsdam.gf.process.scheduler;

import unipotsdam.gf.modules.communication.service.EmailService;
import unipotsdam.gf.modules.project.Project;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Scheduler implements ServletContextListener {

    private final Project project;
    private final EmailService emailService;

    private ScheduledExecutorService scheduler;

    public Scheduler() {
        emailService = null;
        project = null;
    }

    public Scheduler(Project project, EmailService emailService) {
        this.project = project;
        this.emailService = emailService;
    }


    public void start(ServletContextEvent servletContextEvent) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new SendMails(project, emailService), 0 , 21, TimeUnit.DAYS);
    }


    /**
     * this is called on tomcat startup, not precisely want we wanted
     * @param sce
     */
 /*   @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new SendMails(), 21 , 21, TimeUnit.DAYS);
        scheduler.scheduleAtFixedRate(new SendMails(project, emailService), 0 , 21, TimeUnit.DAYS);
    }*/

    @Override
    public void contextInitialized(ServletContextEvent sce) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //scheduler.shutdownNow();
    }
}
