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
		Long locationId = pitchForm.getLocationId();
		PitchLocation pitchLocation = pitchLocationService.retrieve(locationId);
		
		Pitch pitch = new Pitch();
		pitch.setPitchLocation(pitchLocation);
		pitch.setAvailableFrom(pitchForm.getAvailableFrom());
		pitch.setAvailableTo(pitchForm.getAvailableFrom());
		pitch.setCost(pitchForm.getCost());
		pitch.setPitchNumber(pitchForm.getPitchNumber());
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
}
