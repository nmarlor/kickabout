package nmarlor.kickabout.booking;

import java.sql.Time;

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
		
		Time bookedFrom = booking.getBookedFrom();
		Time bookedTo = booking.getBookedTo();
		
		if (StringUtil.isEmpty(booking.getEmail())) 
			errors.rejectValue("email", "email.message");
		
		if (StringUtil.isEmpty(booking.getName())) 
			errors.rejectValue("name", "name.message");
		
		if (bookedFrom == null)
			errors.rejectValue("bookedFrom", "bookedFrom.empty");
		
		if (bookedTo == null)
			errors.rejectValue("bookedTo", "bookedTo.empty");
		
		if (bookedTo != null && bookedFrom != null) {
			if (bookedTo.before(bookedFrom))
				errors.rejectValue("bookedTo", "bookedTo.before");
		}
		
		if (bookedFrom != null && bookedTo != null) {
			if (bookedFrom.after(bookedTo))
				errors.rejectValue("bookedFrom", "bookedFrom.error");
		}
	}
}
