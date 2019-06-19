package unipotsdam.gf.modules.communication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.modules.quiz.StudentIdentifier;
import unipotsdam.gf.modules.communication.model.EMailMessage;
import unipotsdam.gf.modules.project.Project;
import unipotsdam.gf.modules.user.User;
import unipotsdam.gf.modules.user.UserDAO;
import unipotsdam.gf.process.constraints.ConstraintsMessages;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmailService {

    private final static Logger log = LoggerFactory.getLogger(EmailService.class);


    @Inject
    private UserDAO userDAO;


    public boolean sendSingleMessage(EMailMessage eMailMessage, User user) {
/*
        try {

            final String fromEmail = GFMailConfig.EMAIL_ADRESS; //requires valid gmail id
            final String password = GFMailConfig.SMTP_PASSWORD; // correct password for gmail id
            final String toEmail = user.getEmail(); // can be any email id

            //System.out.println("TLSEmail Start");
            Properties props = new Properties();
            props.put("mail.smtp.host", GFMailConfig.SMTP_HOST); //SMTP Host
            props.put("mail.smtp.port", GFMailConfig.SMTP_PORT); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            Session session = Session.getInstance(props, auth);

            EmailUtil.sendEmail(session, toEmail, eMailMessage.getSubject(), eMailMessage.getBody());

        } catch (Exception e) {
            log.warn("could not send email to: " + user.getEmail());
        }
*/
        return true;
    }


    public boolean informAboutMissingTasks(Map<StudentIdentifier, ConstraintsMessages> tasks, Project project) {
        HashMap<StudentIdentifier, ConstraintsMessages> notSentEMailMap = new HashMap<>();
        tasks.entrySet().stream().filter(entry -> {
            User user = new User();
            user.setEmail(entry.getKey().getUserEmail());
            EMailMessage eMailMessage = new EMailMessage();
            eMailMessage.setSubject("Benachrichtigung über nicht erledigte Aufgaben im Projekt " + project.getName());
            eMailMessage.setBody(entry.getValue().toString());
            return !sendSingleMessage(eMailMessage, user);
        }).forEach(entry -> notSentEMailMap.put(entry.getKey(), entry.getValue()));
        return notSentEMailMap.isEmpty();
    }


    public boolean sendMessageToUsers(Project project, EMailMessage eMailMessage) {
        try {
            List<User> users = userDAO.getUsersByProjectName(project.getName());
            List<User> userEmailProblemList = users.stream().filter(user -> !sendSingleMessage(eMailMessage, user)).collect(Collectors.toList());
            return userEmailProblemList.isEmpty();
        } catch (Exception e) {
            log.warn("could not send email to users of project: " + project.getName());
        }
        return false;
    }
}
