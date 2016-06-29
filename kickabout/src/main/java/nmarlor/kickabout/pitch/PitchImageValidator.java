package nmarlor.kickabout.pitch;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

@Component("pitchImageValidator")
public class PitchImageValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) 
	{
		MultipartFile uploadedFile = (MultipartFile) target;
		
		if(uploadedFile.isEmpty() || uploadedFile.getSize()==0)
	           errors.reject("file.empty.message", "X");
	       if(!(uploadedFile.getContentType().toLowerCase().equals("image/jpg") 
	            || uploadedFile.getContentType().toLowerCase().equals("image/jpeg") 
	            || uploadedFile.getContentType().toLowerCase().equals("image/png"))){
	           errors.reject("file.supportedTypes.message", "X");
	       }
	}

}
