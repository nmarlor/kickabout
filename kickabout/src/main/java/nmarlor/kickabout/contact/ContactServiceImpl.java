package nmarlor.kickabout.contact;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContactServiceImpl implements ContactService{
	
	@Override
	public void sendEmail(ContactForm contactForm) 
	{
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		
		String name = contactForm.getName();
		String email = contactForm.getEmail();
		String subject = contactForm.getSubject();
		String emaiMessage = contactForm.getMessage();
		
		try {
	        Message msg = new MimeMessage(session);
	        msg.setFrom(new InternetAddress(email, name));
	        msg.addRecipient(Message.RecipientType.TO,
	                         new InternetAddress("nickmarlor@hotmail.com", "nickmarlor@hotmail.com"));
	        msg.setSubject(subject);
	        msg.setText(emaiMessage);
	        Transport.send(msg);

	    } catch (AddressException e) {
	        // ...
	    } catch (MessagingException e) {
	        // ...
	    } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
