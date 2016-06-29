package nmarlor.kickabout.booking;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("importBookingValidator")
public class ImportBookingValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		Booking booking = (Booking) target;
		
		Time bookedFrom = booking.getBookedFrom();
		Time bookedTo = booking.getBookedTo();
		BigDecimal cost = booking.getCost();
		Date date = booking.getDate();
		String email = booking.getEmail();
		String name = booking.getName();
		
		if (bookedFrom == null) {
			errors.reject("file.emptyBookedFrom.message", "X");
		}
		
		if (bookedTo == null) {
			errors.reject("file.emptyBookedTo.message", "X");
		}
		
		if (StringUtil.isEmpty(email))
			errors.reject("file.emptyEmail.message", "X");
		
		if (StringUtil.isEmpty(name))
			errors.reject("file.emptyName.message", "X");
		
		if (date == null) {
			errors.reject("file.emptyDate.message", "X");
		}
		
		if (cost == null) {
			errors.reject("file.emptyCost.message", "X");
		}
	}

}
