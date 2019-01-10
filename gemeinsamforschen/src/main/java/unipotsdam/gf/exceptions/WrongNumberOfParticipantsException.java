package unipotsdam.gf.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.communication.service.CommunicationService;

public class WrongNumberOfParticipantsException extends Exception {

    private final static Logger log = LoggerFactory.getLogger(WrongNumberOfParticipantsException.class);

    public WrongNumberOfParticipantsException() {
        log.warn("the number of participants must be dividable for groupal and result in at least 2 groups");
    }
}
