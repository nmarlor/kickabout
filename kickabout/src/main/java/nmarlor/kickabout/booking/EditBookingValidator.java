package nmarlor.kickabout.booking;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("editBookingValidator")
public class EditBookingValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		EditBookingForm bookingForm = (EditBookingForm) target;
		
		if (StringUtil.isEmpty(bookingForm.getDate())) 
			errors.rejectValue("date", "date.message");
		
		if (StringUtil.isEmpty(bookingForm.getName())) 
			errors.rejectValue("name", "name.message");
		
		if (StringUtil.isEmpty(bookingForm.getBookedFrom())) 
			errors.rejectValue("bookedFrom", "bookedFrom.empty");
		
		if (StringUtil.isEmpty(bookingForm.getBookedTo())) 
			errors.rejectValue("bookedTo", "bookedTo.empty");
	}

}
