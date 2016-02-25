package nmarlor.kickabout.pitch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	@RequestMapping(value = "/availability", method = RequestMethod.GET)
	public ModelAndView pitchAvailability(Long pitchId, String date){
		ModelAndView mv = new ModelAndView("pitchAvailability/availability");
		
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date availabilityDate = null;
		
		try {

			availabilityDate = formatter.parse(date);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<PitchAvailability> pitchAvailabilities = pitchAvailabilityService.findPitchAvailabilityByPitchAndDate(pitch, availabilityDate);
		
		mv.addObject("pitchAvailabilities", pitchAvailabilities);
		return mv;
	}
}
