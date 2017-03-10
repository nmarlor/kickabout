package nmarlor.kickabout.pitch;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import nmarlor.kickabout.booking.BookVenueForm;

@Component("checkBookingValidator")
public class CheckBookingValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		BookVenueForm venueForm = (BookVenueForm) target;
		
		if (StringUtil.isEmpty(venueForm.getSport()))
			errors.rejectValue("sport", "sport.message");
		
		if (StringUtil.isEmpty(venueForm.getDate()))
			errors.rejectValue("date", "date.message");
		
		if (StringUtil.isEmpty(venueForm.getTime()))
			errors.rejectValue("time", "time.message");
		
		if (StringUtil.isEmpty(venueForm.getDuration()))
			errors.rejectValue("duration", "duration.message");
	}

	
}
