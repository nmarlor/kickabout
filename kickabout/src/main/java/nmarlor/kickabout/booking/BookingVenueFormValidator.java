package nmarlor.kickabout.booking;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("bookingVenueFormValidator")
public class BookingVenueFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		BookVenueForm venueForm = (BookVenueForm) target;
		
		if (StringUtil.isEmpty(venueForm.getEmail())) 
			errors.rejectValue("email", "email.message");
		
		if (StringUtil.isEmpty(venueForm.getName())) 
			errors.rejectValue("name", "name.message");
	}

}
