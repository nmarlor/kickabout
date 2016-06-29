package nmarlor.kickabout.contact;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("contactUsValidator")
public class ContactUsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		ContactForm contactForm = (ContactForm) target;
		
		if (StringUtil.isEmpty(contactForm.getEmail())) 
			errors.rejectValue("email", "email.message");
		
		if (StringUtil.isEmpty(contactForm.getName())) 
			errors.rejectValue("name", "name.message");
		
		if (StringUtil.isEmpty(contactForm.getSubject())) 
			errors.rejectValue("subject", "subject.message");
		
		if (StringUtil.isEmpty(contactForm.getMessage())) 
			errors.rejectValue("message", "emptyMessage.message");
	}

}
