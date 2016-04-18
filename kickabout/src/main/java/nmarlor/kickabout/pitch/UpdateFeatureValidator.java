package nmarlor.kickabout.pitch;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("updateFeatureValidator")
public class UpdateFeatureValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		UpdatePitchFeatureForm updateFeatureForm = (UpdatePitchFeatureForm) target;
		
		String feature = updateFeatureForm.getFeature();
		
		if (StringUtil.isEmpty(feature)) 
		errors.rejectValue("feature", "featureEmpty.message");
	}

}
