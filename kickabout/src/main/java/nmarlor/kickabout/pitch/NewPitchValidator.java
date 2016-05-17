package nmarlor.kickabout.pitch;

import java.math.BigDecimal;
import java.sql.Time;

import org.hsqldb.lib.StringUtil;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("newPitchValidator")
public class NewPitchValidator implements Validator{
	
	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		NewPitchForm pitchForm = (NewPitchForm) target;
		
		Time availableFrom = pitchForm.getAvailableFrom();
		Time availableTo = pitchForm.getAvailableTo();
		BigDecimal cost = pitchForm.getCost();
		Integer pitchNumber = pitchForm.getPitchNumber();
		String pitchSize = pitchForm.getPitchSize();
		
		if (availableFrom == null)
			errors.rejectValue("availableFrom", "pitchAvailableFrom.message");
		
		if (availableTo == null)
			errors.rejectValue("availableTo", "pitchAvailableTo.message");
		
		if (cost == null)
			errors.rejectValue("cost", "pitchCost.message");
		
		if (pitchNumber == null)
			errors.rejectValue("pitchNumber", "pitchNumberEmpty.message");
		
		if (StringUtil.isEmpty(pitchSize)) 
			errors.rejectValue("pitchSize", "pitchSize.message");
	}

}
