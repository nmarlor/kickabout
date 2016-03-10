package nmarlor.kickabout.pitch;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import nmarlor.kickabout.date.DateService;

@Controller
public class PitchAvailabilityController {
	
	@Autowired
	private PitchAvailabilityService pitchAvailabilityService;
	
	@Autowired
	private PitchesService pitchesService;
	
	@Autowired
	private PitchFeatureService PitchFeatureService;
	
	@Autowired
	private DateService dateService;
	
	@RequestMapping(value = "/availability", method = RequestMethod.GET)
	public ModelAndView pitchAvailability(Long pitchId){
		ModelAndView mv = new ModelAndView("pitchAvailability/availabilityAndFeatures");
		
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		Date formattedDate = dateService.getTodaysDate();
		
		List<PitchFeature> pitchFeatures = PitchFeatureService.findPitchFeaturesByPitch(pitch);
		List<PitchAvailability> pitchAvailabilities = pitchAvailabilityService.findPitchAvailabilityByPitchAndDate(pitch, formattedDate);
		
		PitchForm pitchForm = new PitchForm();
		pitchForm.setPitchId(pitchId);
		
		// Date to be displayed on the front end
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String date = df.format(formattedDate);
		
		mv.addObject("pitchId", pitchId);
		mv.addObject("pitch", pitch);
		mv.addObject("pitchFeatures", pitchFeatures);
		mv.addObject("pitchForm", pitchForm);
		mv.addObject("date", date);
		mv.addObject("pitchAvailabilities", pitchAvailabilities);
		
		return mv;
	}
	
	@RequestMapping(value = "/availability", method = RequestMethod.POST)
	public ModelAndView pitchAvailability(@Valid @ModelAttribute("pitchForm") PitchForm pitchForm, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("pitchAvailability/availabilityAndFeatures");
		mv.addObject("pitchForm", pitchForm);
		
		String date = pitchForm.getDate();
		Date availabilityDate = dateService.stringToDate(date);
		
		Long pitchId = pitchForm.getPitchId();
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		
		List<PitchFeature> pitchFeatures = PitchFeatureService.findPitchFeaturesByPitch(pitch);
		List<PitchAvailability> pitchAvailabilities = pitchAvailabilityService.findPitchAvailabilityByPitchAndDate(pitch, availabilityDate);

		mv.addObject("pitchForm", pitchForm);
		mv.addObject("pitch", pitch);
		mv.addObject("date", date);
		mv.addObject("pitchFeatures", pitchFeatures);
		mv.addObject("pitchAvailabilities", pitchAvailabilities);
		
		return mv;
	}
}
