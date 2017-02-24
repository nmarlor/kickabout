package nmarlor.kickabout.home;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import nmarlor.kickabout.pitch.LocationForm;

@Component("locationSearchValidator")
public class LocationSearchValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		LocationForm locationForm = (LocationForm) target;
		
		if (StringUtil.isEmpty(locationForm.getName()))
			errors.rejectValue("name", "locationSearchName.message");
		
		if (StringUtil.isEmpty(locationForm.getSport()))
			errors.rejectValue("sport", "locationSport.message");
	}
}