package nmarlor.kickabout.booking;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("adminBookingValidator")
public class AdminBookingValidator implements Validator {

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
		
		if (StringUtil.isEmpty(booking.getName())) 
			errors.rejectValue("name", "name.message");
		
		if (booking.getCost() == null)
			errors.rejectValue("cost", "bookingCost.message");
		
		if (StringUtil.isEmpty(bookedFrom)) 
			errors.rejectValue("bookedFrom", "bookedFrom.empty");
		
		if (StringUtil.isEmpty(bookedTo)) 
			errors.rejectValue("bookedTo", "bookedTo.empty");
	}
}
