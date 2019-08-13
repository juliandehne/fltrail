package unipotsdam.gf.modules.communication.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import unipotsdam.gf.config.GeneralConfig;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;

public class EmailUtil {

	private final static Logger log = LoggerFactory.getLogger(EmailUtil.class);

	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(Session session, String toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");

	      msg.setFrom(new InternetAddress(GeneralConfig.EMAIL_ADRESS, "Julian Dehne"));

	      msg.setReplyTo(InternetAddress.parse(GeneralConfig.EMAIL_ADRESS, false));

	      msg.setSubject(subject, "UTF-8");

	      msg.setText(body, "UTF-8");

	      msg.setSentDate(new Date());

	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      System.out.println("Message is ready");
    	  Transport.send(msg);

	      log.info("EMail Sent Successfully to: " +toEmail);
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
}