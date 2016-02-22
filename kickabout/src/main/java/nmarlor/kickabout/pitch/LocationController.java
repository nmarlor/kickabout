package nmarlor.kickabout.pitch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LocationController {
	
	@Autowired
	private PitchLocationDAO pitchLocationDAO;
	
	@Autowired
	private PitchesService pitchesService;

	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public ModelAndView Locations() {
		ModelAndView mv = new ModelAndView("locations/pitches");
		
		List<PitchLocation> allPitches = pitchLocationDAO.findAll();
		mv.addObject("pitches", allPitches);
		
		return mv;
	}
	
	@RequestMapping(value = "pitch", method=RequestMethod.GET)
	public ModelAndView editProductRequest(Long id) {
		ModelAndView mv = new ModelAndView("/locations/pitch");
		
		List<Pitch> pitches = new ArrayList<>();
		pitches = pitchesService.findPitchesByLocationId(id);
		
		mv.addObject("pitches", pitches);
		return mv;
	}
}
