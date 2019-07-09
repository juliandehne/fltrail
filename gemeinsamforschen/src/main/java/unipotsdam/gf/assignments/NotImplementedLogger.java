package unipotsdam.gf.assignments;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class NotImplementedLogger {

    private static final Logger log = LoggerFactory.getLogger(NotImplementedLogger.class);
    private static final ConcurrentHashMap<String, Boolean> messagesSend = new ConcurrentHashMap<String, Boolean>();

    public static synchronized void logAssignment(Assignee assignee, Class element) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(assignee.name());
        stringBuilder.append(": Please implement ");
        stringBuilder.append(element);
        String result = stringBuilder.toString();

        if (!getMessageMap().keySet().contains(result)) {
            log.info(result);
        }
        messagesSend.put(result, true);
    }

    public static synchronized void logAssignment(Assignee assignee, Class element, String fakeMessage) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(assignee.name());
        stringBuilder.append(": Please implement ");
        stringBuilder.append(element + "\n");
        stringBuilder.append("process: " + fakeMessage + "\n");
        String result = stringBuilder.toString();

        if (!getMessageMap().keySet().contains(result)) {
            log.info(result);
        }
        messagesSend.put(result, true);
    }



    public static void logAssignment(Assignee assignee, Class className, String method, String fakeMessage) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(assignee.name());
        stringBuilder.append(": Please implement ");
        stringBuilder.append(className.getCanonicalName());
        stringBuilder.append(":");
        stringBuilder.append(method);
        stringBuilder.append("\n");
        stringBuilder.append(fakeMessage);
        String result = stringBuilder.toString();

        if (!getMessageMap().keySet().contains(result)) {
            log.info(result);
        }
        messagesSend.put(result, true);
    }

    public static synchronized ConcurrentHashMap<String, Boolean> getMessageMap() {
        if (messagesSend == null) {
            return new ConcurrentHashMap<String, Boolean>();
        }
        return messagesSend;
    }

}
