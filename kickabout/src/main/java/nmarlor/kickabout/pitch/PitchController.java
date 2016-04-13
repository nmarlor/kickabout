package nmarlor.kickabout.pitch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PitchController {
	
	@Autowired
	private PitchLocationService pitchLocationService;
	
	@Autowired
	private PitchesService pitchService;
	
	@Autowired
	private NewPitchValidator pitchValidator;

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
		ModelAndView thisMv = new ModelAndView("pitches/newPitch");
		Long locationId = pitchForm.getLocationId();
		PitchLocation pitchLocation = pitchLocationService.retrieve(locationId);
		Integer pitchNumber = pitchForm.getPitchNumber();
		
		pitchValidator.validate(pitchForm, bindingResult);
		if (bindingResult.hasErrors()) 
		{
			thisMv.addObject("errors", bindingResult);
			return thisMv;
		}
		
		Pitch retrievedPitch = pitchService.findPitchByLocationAndNumber(pitchLocation, pitchNumber);
		if (retrievedPitch != null) {
			bindingResult.rejectValue("pitchNumber", "pitchNumberDuplicate.message");
			thisMv.addObject("errors", bindingResult);
			return thisMv;
		}
		
		Pitch pitch = new Pitch();
		pitch.setPitchLocation(pitchLocation);
		pitch.setAvailableFrom(pitchForm.getAvailableFrom());
		pitch.setAvailableTo(pitchForm.getAvailableFrom());
		pitch.setCost(pitchForm.getCost());
		pitch.setPitchNumber(pitchNumber);
		pitch.setPitchSize(pitchForm.getPitchSize());
		
		pitchService.createPitch(pitch);
		
		List<Pitch> pitches = new ArrayList<>();
		pitches = pitchService.findPitchesByLocation(pitchLocation);
		
		ModelAndView successMv = new ModelAndView("pitches/successfulPitchCreation");
		successMv.addObject("locationId", locationId);
		successMv.addObject("pitches", pitches);
		successMv.addObject("pitchForm", pitchForm);
		
		return successMv;
	}
	
	@RequestMapping(value = "managePitch", method = RequestMethod.GET)
	public ModelAndView managePitch(Long pitchId){
		ModelAndView mv = new ModelAndView("pitches/managePitch");
		
		Pitch pitch = pitchService.retrievePitch(pitchId);
		
		mv.addObject("pitch", pitch);
		return mv;
	}
}
