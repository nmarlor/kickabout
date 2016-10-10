package nmarlor.kickabout.pitch;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import nmarlor.kickabout.booking.ReferenceOrNameAdminForm;

@Component("referenceOrNameSearchAdminValidator")
public class ReferenceOrNameSearchAdminValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		ReferenceOrNameAdminForm referenceOrNameAdminForm = (ReferenceOrNameAdminForm) target;
		
		if (StringUtil.isEmpty(referenceOrNameAdminForm.getSearch()))
			errors.rejectValue("search", "referenceOrName.message");
	}

}
