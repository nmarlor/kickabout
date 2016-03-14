package nmarlor.kickabout.booking;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("bookingValidator")
public class BookingValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		BookingForm booking = (BookingForm) target;
		
		if (StringUtil.isEmpty(booking.getEmail())) 
			errors.rejectValue("email", "email.message");
		
		if (StringUtil.isEmpty(booking.getName())) 
			errors.rejectValue("name", "name.message");
		
		if (booking.getBookedFrom() == null)
			errors.rejectValue("bookedFrom", "bookedFrom.message");
		
		if (booking.getBookedTo() == null)
			errors.rejectValue("bookedTo", "bookedTo.message");
	}

}
