package unipotsdam.gf.process.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendMails implements Runnable {

    private final static Logger log = LoggerFactory.getLogger(SendMails.class);


    @Override
    public void run() {

        log.debug("Nach 3 Wochen Email an Gruppenmitglieder.");
    }
}
