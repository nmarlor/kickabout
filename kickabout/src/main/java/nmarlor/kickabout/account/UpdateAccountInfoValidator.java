package nmarlor.kickabout.account;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("updateAccountInfoValidator")
public class UpdateAccountInfoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		UpdateAccountInfoForm accountForm = (UpdateAccountInfoForm) target;
	
		if (StringUtil.isEmpty(accountForm.getEmail())) 
			errors.rejectValue("email", "email.message");
		
		if (StringUtil.isEmpty(accountForm.getName())) 
			errors.rejectValue("name", "name.message");
	}

}
