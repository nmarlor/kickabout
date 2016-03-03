package nmarlor.kickabout.pitch;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PitchAvailabilityController {
	
	@Autowired
	private PitchAvailabilityService pitchAvailabilityService;
	
	@Autowired
	private PitchesService pitchesService;
	
	@Autowired
	private PitchFeatureService PitchFeatureService;
	
	@RequestMapping(value = "/availability", method = RequestMethod.GET)
	public ModelAndView pitchAvailability(Long pitchId){
		ModelAndView mv = new ModelAndView("pitchAvailability/availabilityAndFeatures");
		
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		
		List<PitchFeature> pitchFeatures = PitchFeatureService.findPitchFeaturesByPitch(pitch);
		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
//		LocalDate availabilityDate = LocalDate.parse(date, formatter);
//		Date checkDate = Date.valueOf(availabilityDate);
//		
//		List<PitchAvailability> pitchAvailabilities = pitchAvailabilityService.findPitchAvailabilityByPitchAndDate(pitch, checkDate);
		
		mv.addObject("pitch", pitch);
		mv.addObject("pitchFeatures", pitchFeatures);
//		mv.addObject("date", date);
//		mv.addObject("pitchAvailabilities", pitchAvailabilities);
		
		return mv;
	}
}
