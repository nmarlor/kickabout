package nmarlor.kickabout.account;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("newAccountValidator")
public class NewAccountValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		NewAccountForm newAccount = (NewAccountForm) target;
		
		if (StringUtil.isEmpty(newAccount.getEmail())) 
			errors.rejectValue("email", "email.message");
		
		if (StringUtil.isEmpty(newAccount.getName())) 
			errors.rejectValue("name", "name.message");
		
		if (StringUtil.isEmpty(newAccount.getPassword())) 
			errors.rejectValue("password", "password.message");
		
		if (StringUtil.isEmpty(newAccount.getRole())) 
			errors.rejectValue("role", "role.message");
	}

}
