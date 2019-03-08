package unipotsdam.gf.process.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Scheduler implements ServletContextListener {

    private ScheduledExecutorService scheduler;


    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new SendMails(), 21 , 21, TimeUnit.DAYS);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        scheduler.shutdownNow();
    }
}
