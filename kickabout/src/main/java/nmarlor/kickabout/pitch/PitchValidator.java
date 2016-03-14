package nmarlor.kickabout.pitch;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("pitchValidator")
public class PitchValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		PitchForm pitchForm = (PitchForm) target;
		
		if (StringUtil.isEmpty(pitchForm.getDate())) 
			errors.rejectValue("date", "dateSearch.message");
	}

}
