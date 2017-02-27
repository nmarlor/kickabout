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
	}

}
