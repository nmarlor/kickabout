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
		
		String bookedFrom = booking.getBookedFrom();
		String bookedTo = booking.getBookedTo();
		
		if (StringUtil.isEmpty(booking.getEmail())) 
			errors.rejectValue("email", "email.message");
		
		if (StringUtil.isEmpty(booking.getName())) 
			errors.rejectValue("name", "name.message");
		
		if (StringUtil.isEmpty(bookedFrom)) 
			errors.rejectValue("bookedFrom", "bookedFrom.empty");
		
		if (StringUtil.isEmpty(bookedTo)) 
			errors.rejectValue("bookedTo", "bookedTo.empty");
	}
}
