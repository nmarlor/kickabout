package nmarlor.kickabout.pitch;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("newFeatureValidator")
public class NewPitchFeatureValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		NewPitchFeatureForm newPitchFeatureForm = (NewPitchFeatureForm) target;
		
		String feature = newPitchFeatureForm.getFeature();
		
		if (StringUtil.isEmpty(feature)) 
			errors.rejectValue("feature", "featureEmpty.message");
	}

}
