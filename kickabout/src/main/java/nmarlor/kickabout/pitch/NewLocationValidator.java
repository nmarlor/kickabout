package nmarlor.kickabout.pitch;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("newLocationValidator")
public class NewLocationValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		NewPitchLocationForm pitchLocationForm = (NewPitchLocationForm) target;
		
		String addressLine1 = pitchLocationForm.getAddressLine1();
		String addressLine2 = pitchLocationForm.getAddressLine2();
		String city = pitchLocationForm.getCity();
		String name = pitchLocationForm.getName();
		String county = pitchLocationForm.getCounty();
		String email = pitchLocationForm.getEmail();
		String postcode = pitchLocationForm.getPostcode();
		String telephone = pitchLocationForm.getTelephone();
		
		String mondayFrom = pitchLocationForm.getMonday_from();
		String mondayTo = pitchLocationForm.getMonday_to();
		String tuesdayFrom = pitchLocationForm.getTuesday_from();
		String tuesdayTo = pitchLocationForm.getTuesday_to();
		String wednesdayFrom = pitchLocationForm.getWednesday_from();
		String wednesdayTo = pitchLocationForm.getWednesday_to();
		String thursdayFrom = pitchLocationForm.getThursday_from();
		String thursdayTo = pitchLocationForm.getThursday_to();
		String fridayFrom = pitchLocationForm.getFriday_from();
		String fridayTo = pitchLocationForm.getFriday_to();
		String saturdayFrom = pitchLocationForm.getSaturday_from();
		String saturdayTo = pitchLocationForm.getSaturday_to();
		String sundayFrom = pitchLocationForm.getSaturday_from();
		String sundayTo = pitchLocationForm.getSunday_to();
		
		if (StringUtil.isEmpty(addressLine1)) 
			errors.rejectValue("addressLine1", "address1Empty.message");
		
		if (StringUtil.isEmpty(addressLine2)) 
			errors.rejectValue("addressLine2", "address2Empty.message");
		
		if (StringUtil.isEmpty(city)) 
			errors.rejectValue("city", "cityEmpty.message");
		
		if (StringUtil.isEmpty(name)) 
			errors.rejectValue("name", "name.message");
		
		if (StringUtil.isEmpty(county)) 
			errors.rejectValue("county", "countyEmpty.message");
		
		if (StringUtil.isEmpty(email)) 
			errors.rejectValue("email", "emailEmpty.message");
		
		if (StringUtil.isEmpty(postcode)) 
			errors.rejectValue("postcode", "postcodeEmpty.message");
		
		if (StringUtil.isEmpty(telephone)) 
			errors.rejectValue("telephone", "telephoneEmpty.message");
		
		if (StringUtil.isEmpty(mondayFrom)) 
			errors.rejectValue("monday_from", "availability.message");
		
		if (StringUtil.isEmpty(mondayTo)) 
			errors.rejectValue("monday_to", "availability.message");
		
		if (StringUtil.isEmpty(tuesdayFrom)) 
			errors.rejectValue("tuesday_from", "availability.message");
		
		if (StringUtil.isEmpty(tuesdayTo)) 
			errors.rejectValue("tuesday_to", "availability.message");
		
		if (StringUtil.isEmpty(wednesdayFrom)) 
			errors.rejectValue("wednesday_from", "availability.message");
		
		if (StringUtil.isEmpty(wednesdayTo)) 
			errors.rejectValue("wednesday_to", "availability.message");
		
		if (StringUtil.isEmpty(thursdayFrom)) 
			errors.rejectValue("thursday_from", "availability.message");
		
		if (StringUtil.isEmpty(thursdayTo)) 
			errors.rejectValue("thursday_to", "availability.message");
		
		if (StringUtil.isEmpty(fridayFrom)) 
			errors.rejectValue("friday_from", "availability.message");
		
		if (StringUtil.isEmpty(fridayTo)) 
			errors.rejectValue("friday_to", "availability.message");
		
		if (StringUtil.isEmpty(saturdayFrom)) 
			errors.rejectValue("saturday_from", "availability.message");
		
		if (StringUtil.isEmpty(saturdayTo)) 
			errors.rejectValue("saturday_to", "availability.message");
		
		if (StringUtil.isEmpty(sundayFrom)) 
			errors.rejectValue("sunday_from", "availability.message");
		
		if (StringUtil.isEmpty(sundayTo)) 
			errors.rejectValue("sunday_to", "availability.message");
	}

}
