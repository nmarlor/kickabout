package nmarlor.kickabout.pitch;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
public class PitchController {
	
	@Autowired
	private PitchLocationService pitchLocationService;
	
	@Autowired
	private PitchesService pitchService;
	
	@Autowired
	private NewPitchValidator pitchValidator;
	
	@Autowired
	private UpdateFeatureValidator updateFeatureValidator;
	
	@Autowired
	private PitchFeatureService pitchFeatureService;

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
		List<PitchFeature> pitchFeatures = pitchFeatureService.findPitchFeaturesByPitch(pitch);
		
		mv.addObject("pitch", pitch);
		mv.addObject("pitchFeatures", pitchFeatures);
		return mv;
	}
	
	@RequestMapping(value = "editFeature", method = RequestMethod.GET)
	public ModelAndView editFeatureRequest(Long id){
		ModelAndView mv = new ModelAndView("pitches/editFeature");
		
		PitchFeature pitchFeature = pitchFeatureService.retrieve(id);
		Long pitchId = pitchFeature.getPitch().getId();
		String feature = pitchFeature.getFeature();
		
		UpdatePitchFeatureForm featureForm = new UpdatePitchFeatureForm();
		featureForm.setFeature(feature);
		featureForm.setFeatureId(id);
		featureForm.setPitchId(pitchId);
		
		mv.addObject("featureForm", featureForm);
		return mv;
	}
	
	@RequestMapping(value = "editFeature", method = RequestMethod.POST)
	public ModelAndView editFeature(@ModelAttribute("featureForm") UpdatePitchFeatureForm featureForm, BindingResult result, HttpServletRequest request){
		ModelAndView thisMv = new ModelAndView("pitches/editFeature");
		PitchFeature pitchFeature = pitchFeatureService.retrieve(featureForm.getFeatureId());
		
		updateFeatureValidator.validate(featureForm, result);
		if (result.hasErrors()) 
		{
			thisMv.addObject("errors", result);
			return thisMv;
		}
		
		pitchFeature.setFeature(featureForm.getFeature());
		
		pitchFeatureService.update(pitchFeature);
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("redirect");
		return new ModelAndView (jsonView, "redirect", request.getContextPath() + "pitches/managePitch");
	}
}
