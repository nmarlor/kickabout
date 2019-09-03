package nmarlor.kickabout.pitch;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;


@Controller
public class PitchController {
	
	@Autowired
	private PitchLocationService pitchLocationService;
	
	@Autowired
	private PitchesService pitchService;
	
	@Autowired
	private SportsService sportsService;
	
	@Autowired
	private NewPitchValidator pitchValidator;
	
	@Autowired
	private NewPitchFeatureValidator newPitchFeatureValidator;
	
	@Autowired
	private UpdateFeatureValidator updateFeatureValidator;
	
	@Autowired
	private PitchFeatureService pitchFeatureService;
	
	@Autowired
	private PitchImageValidator imageValidator;
	
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
		pitch.setAvailableTo(pitchForm.getAvailableTo());
		pitch.setCost(pitchForm.getCost());
		pitch.setPitchNumber(pitchNumber);
		pitch.setPitchSize(pitchForm.getPitchSize());
		pitch.setEnvironment(pitchForm.getEnvironment());
		pitch.setSurface(pitchForm.getSurface());
		
		pitchService.createPitch(pitch);
		
		Sports sport1 = new Sports();
		sport1.setSport("5 a side football");
		sport1.setAvailable(false);
		sport1.setPitch(pitch);
		
		sportsService.createSport(sport1);
		
		Sports sport2 = new Sports();
		sport2.setSport("7 a side football");
		sport2.setAvailable(false);
		sport2.setPitch(pitch);
		
		sportsService.createSport(sport2);
		
		Sports sport3 = new Sports();
		sport3.setSport("11 a side football");
		sport3.setAvailable(false);
		sport3.setPitch(pitch);
		
		sportsService.createSport(sport3);
		
		Sports sport4 = new Sports();
		sport4.setSport("Badminton");
		sport4.setAvailable(false);
		sport4.setPitch(pitch);
		
		sportsService.createSport(sport4);
		
		Sports sport5 = new Sports();
		sport5.setSport("Basketball");
		sport5.setAvailable(false);
		sport5.setPitch(pitch);
		
		sportsService.createSport(sport5);
		
		Sports sport6 = new Sports();
		sport6.setSport("Hockey");
		sport6.setAvailable(false);
		sport6.setPitch(pitch);
		
		sportsService.createSport(sport6);
		
		Sports sport7 = new Sports();
		sport7.setSport("Netball");
		sport7.setAvailable(false);
		sport7.setPitch(pitch);
		
		sportsService.createSport(sport7);
		
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
		List<Sports> retrievedSports = sportsService.findSportsByPitch(pitch);
		
		PitchForm pitchForm = new PitchForm();
		pitchForm.setPitchId(pitchId);
		
		SportsForm sportsForm = new SportsForm();
		sportsForm.setPitchId(pitchId);
		
		ArrayList<Sport> pitchSports = new ArrayList<Sport>();
		
		for (Sports sport : retrievedSports) 
		{
			Sport newSport = new Sport();
			newSport.setId(sport.getId());
			newSport.setSport(sport.getSport());
			
			if (sport.getAvailable().equals(true)) 
			{
				newSport.setAvailable(true);
			}
			if (sport.getAvailable().equals(false)) 
			{
				newSport.setAvailable(false);
			}
			pitchSports.add(newSport);
		}
		
		sportsForm.setPitchSports(pitchSports);
		
		mv.addObject("pitch", pitch);
		mv.addObject("pitchId", pitchId);
		mv.addObject("pitchFeatures", pitchFeatures);
		mv.addObject("pitchSports", pitchSports);
		mv.addObject("pitchForm", pitchForm);
		mv.addObject("sportsForm", sportsForm);
		return mv;
	}
	
	@RequestMapping(value = "pitchSports", method = RequestMethod.POST)
	public ModelAndView updateSports(@ModelAttribute("sportsForm") SportsForm sportsForm, BindingResult bindingResult, HttpServletRequest request){
		
		Long pitchId = sportsForm.getPitchId();
		Pitch pitch = pitchService.retrievePitch(pitchId);
		
		List<Sport> pitchSports = sportsForm.getPitchSports();
		
		for (Sport sport : pitchSports)
		{
			Long id = sport.getId();
			Sports dbSport = sportsService.retrieveSport(id);
			
			Boolean availability = sport.getAvailable();
			
			if (availability.equals(true)) 
			{
				dbSport.setAvailable(true);
			}
			
			if (availability.equals(false)) 
			{
				dbSport.setAvailable(false);
			}
			
			sportsService.update(dbSport);
		}
		
		PitchForm pitchForm = new PitchForm();
		pitchForm.setPitchId(pitchId);
		
		List<PitchFeature> pitchFeatures = pitchFeatureService.findPitchFeaturesByPitch(pitch);
		
		ModelAndView mv = new ModelAndView("pitches/managePitch");
		mv.addObject("pitchId", pitchId);
		mv.addObject("pitch", pitch);
		mv.addObject("pitchSports", pitchSports);
		mv.addObject("pitchFeatures", pitchFeatures);
		mv.addObject("pitchForm", pitchForm);
		
		return mv;
	}
	
	@RequestMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE) 
	public ResponseEntity<byte[]> getImage(Long pitchId) throws IOException 
	{ 
		Pitch pitch = pitchService.retrievePitch(pitchId);
		byte[] imageContent =  pitch.getImage();
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.IMAGE_JPEG); 
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
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
	
	@RequestMapping(value = "deleteFeature", method = RequestMethod.GET)
	public ModelAndView deleteFeatureRequest(Long id){
		ModelAndView mv = new ModelAndView("pitches/deleteFeature");
		
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
	
	@RequestMapping(value = "deleteFeature", method = RequestMethod.POST)
	public ModelAndView deleteFeature(@ModelAttribute("featureForm") UpdatePitchFeatureForm featureForm, BindingResult result, HttpServletRequest request){
		PitchFeature pitchFeature = pitchFeatureService.retrieve(featureForm.getFeatureId());
		
		pitchFeatureService.delete(pitchFeature);
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("redirect");
		return new ModelAndView (jsonView, "redirect", request.getContextPath() + "pitches/managePitch");
	}
	
	@RequestMapping(value = "addFeature", method = RequestMethod.GET)
	public ModelAndView addFeatureRequest(Long id){
		ModelAndView mv = new ModelAndView("pitches/addFeature");
		
		NewPitchFeatureForm featureForm = new NewPitchFeatureForm();
		featureForm.setPitchId(id);
		
		mv.addObject("featureForm", featureForm);
		return mv;
	}
	
	@RequestMapping(value = "addFeature", method = RequestMethod.POST)
	public ModelAndView addFeature(@ModelAttribute("featureForm") NewPitchFeatureForm featureForm, BindingResult result, HttpServletRequest request){
		ModelAndView thisMv = new ModelAndView("pitches/addFeature");

		PitchFeature pitchFeature = new PitchFeature();
		Pitch pitch = pitchService.retrievePitch(featureForm.getPitchId());
		String feature = featureForm.getFeature();
		
		newPitchFeatureValidator.validate(featureForm, result);
		if (result.hasErrors()) 
		{
			thisMv.addObject("errors", result);
			return thisMv;
		}
		
		PitchFeature retrievedPitchFeature = pitchFeatureService.findPitchFeatureByPitchAndFeature(pitch, feature);
		if (retrievedPitchFeature != null) {
			result.rejectValue("feature", "featureDuplicate.message");
			thisMv.addObject("errors", result);
			return thisMv;
		}
		
		pitchFeature.setFeature(feature);
		pitchFeature.setPitch(pitch);
		pitchFeatureService.create(pitchFeature);
		
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("redirect");
		return new ModelAndView (jsonView, "redirect", request.getContextPath() + "pitches/managePitch");
	}
	
	@RequestMapping(value = "uploadImage", method = RequestMethod.POST)
	public ModelAndView uploadImage(@ModelAttribute("pitchForm") PitchForm pitchForm, Principal principal, BindingResult result, @RequestParam("file") MultipartFile uploadedFile)
	{
		ModelAndView mv = new ModelAndView("pitches/managePitch");
		
		Errors errors = result;
		
		Long pitchId = pitchForm.getPitchId();
		Pitch pitch = pitchService.retrievePitch(pitchId);
		List<PitchFeature> pitchFeatures = pitchFeatureService.findPitchFeaturesByPitch(pitch);
		
		imageValidator.validate(uploadedFile, result);
		if (result.hasErrors()) 
		{
			errors.reject("image.errors.message", "X");
			
			mv.addObject("errors", result);
			mv.addObject("pitch", pitch);
			mv.addObject("pitchId", pitchId);
			mv.addObject("pitchFeatures", pitchFeatures);
			mv.addObject("pitchForm", pitchForm);
			
			return mv;
		}
		
		if (!uploadedFile.isEmpty()) 
		{
			try 
			{
				byte[] image = uploadedFile.getBytes();
				pitch.setImage(image);
				pitchService.updatePitch(pitch);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
               
		mv.addObject("pitch", pitch);
		mv.addObject("pitchId", pitchId);
		mv.addObject("pitchFeatures", pitchFeatures);
		mv.addObject("pitchForm", pitchForm);
		
		return mv;
	}
	
}
