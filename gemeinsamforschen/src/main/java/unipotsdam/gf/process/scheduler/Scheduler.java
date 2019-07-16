package unipotsdam.gf.process.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import unipotsdam.gf.modules.communication.service.EmailService;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.session.LockCronJob;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@WebListener
public class Scheduler implements ServletContextListener {

    private final Project project;
    private final EmailService emailService;
    private org.quartz.Scheduler sched;
    //private boolean shutDownLocker=false;

    private ScheduledExecutorService scheduler;

    public Scheduler() {
        try {
            this.sched = new StdSchedulerFactory().getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        emailService = null;
        project = null;
    }

    public Scheduler(Project project, EmailService emailService) {
        this.project = project;
        this.emailService = emailService;
    }


    public void start(ServletContextEvent servletContextEvent) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        //scheduler.schedule(new SendMails(project, emailService), 21 , TimeUnit.DAYS);
        //scheduler.schedule(new SendMails(project, emailService), 2 , TimeUnit.MINUTES);
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
        try {
            JobDetail job = JobBuilder.newJob(LockCronJob.class).withIdentity("TaskLocker", "group1").build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(5).repeatForever())
                    .build();
            sched.start();
            sched.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            sched.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        //scheduler.shutdownNow();
    }
}
