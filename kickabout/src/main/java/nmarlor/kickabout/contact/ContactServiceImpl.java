package nmarlor.kickabout.contact;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContactServiceImpl implements ContactService{
	
	private static String USER_NAME = "testkickabout@gmail.com";
    private static String PASSWORD = "testkickabout1";

	@Override
	public void sendEmail(ContactForm contactForm) 
	{
		String password = PASSWORD;
		String username = USER_NAME;
		
		Properties props = System.getProperties();
	    String host = "smtp.gmail.com";
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.user", username);
	    props.put("mail.smtp.password", password);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", "true");
		
	    Session session = Session.getDefaultInstance(props);
	    MimeMessage message = new MimeMessage(session);
	    
	    String kickaboutEmail = username;
	    
	    InternetAddress toAddress = new InternetAddress();
	    toAddress.setAddress(kickaboutEmail);
	    
	    String[] from = { contactForm.getEmail() }; 
	    InternetAddress[] fromAddress = new InternetAddress[from.length];
	    
	    try 
	    {
	    	message.setSubject(contactForm.getSubject());
	    	message.setText("Name: " + contactForm.getName()
	    					+ "\n" + "\n" + "Email Address: " + contactForm.getEmail()
	    					+ "\n" + "\n" + "Message: " + contactForm.getMessage());
	        message.addRecipient(Message.RecipientType.TO, toAddress);
	        
            for( int i = 0; i < from.length; i++ ) {
            	fromAddress[i] = new InternetAddress(from[i]);
            }
	        message.setReplyTo(fromAddress);
	        
	        Transport transport = session.getTransport("smtp");
	        transport.connect(host, kickaboutEmail, password);
	        transport.sendMessage(message, message.getAllRecipients());
	        transport.close();
		} 
	    catch (Exception e) 
	    {
			e.printStackTrace();
		}
	}
}
