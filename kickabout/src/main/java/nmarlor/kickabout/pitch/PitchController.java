package nmarlor.kickabout.pitch;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PitchController {

	@RequestMapping(value = "addPitch", method = RequestMethod.GET)
	public ModelAndView addPitch(Long locationId){
		ModelAndView mv = new ModelAndView("pitches/newPitch");
		
		NewPitchForm pitchForm = new NewPitchForm();
		pitchForm.setLocationId(locationId);
		
		mv.addObject("pitchForm", pitchForm);
		return mv;
	}
	
	@RequestMapping(value = "addPitch", method = RequestMethod.POST)
	public ModelAndView submitPitch(@ModelAttribute("pitchForm") NewPitchForm pitchForm, BindingResult bindingResult){
		ModelAndView mv = new ModelAndView("pitches/newPitch");
		
		Long locationId = pitchForm.getLocationId();
		
		mv.addObject("locationId", locationId);
		mv.addObject("pitchForm", pitchForm);
		return mv;
	}
}
