package nmarlor.kickabout.booking;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("searchByReferenceOrNameValidator")
public class SearchByReferenceOrNameValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		ReferenceOrNameForm referenceOrNameForm = (ReferenceOrNameForm) target;
		
		if (StringUtil.isEmpty(referenceOrNameForm.getSearch()))
			errors.rejectValue("search", "referenceOrName.message");
	}

}
