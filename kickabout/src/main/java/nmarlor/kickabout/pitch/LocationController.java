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
	private PitchesService pitchesService;
	
	@Autowired
	private PitchLocationService pitchLocationService;

	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public ModelAndView Locations() {
		ModelAndView mv = new ModelAndView("locations/locations");
		
		List<PitchLocation> locations = pitchLocationService.findAll();
		mv.addObject("locations", locations);
		
		return mv;
	}
	
	@RequestMapping(value = "/locationsSearch", method = RequestMethod.GET)
	public ModelAndView LocationsSearch() {
		ModelAndView mv = new ModelAndView("locations/locationsSearch");
		
		List<PitchLocation> locations = pitchLocationService.findAll();
		mv.addObject("locations", locations);
		
		return mv;
	}
	
	@RequestMapping(value = "pitches", method=RequestMethod.GET)
	public ModelAndView pitches(Long locationId) {
		ModelAndView mv = new ModelAndView("/locations/pitches");
		
		PitchLocation location = pitchLocationService.retrieve(locationId);
		
		List<Pitch> pitches = new ArrayList<>();
		pitches = pitchesService.findPitchesByLocation(location);
		
		mv.addObject("location", location);
		mv.addObject("pitches", pitches);
		return mv;
	}
	
	@RequestMapping(value = "pitchesForAdminLocation", method=RequestMethod.GET)
	public ModelAndView adminPitches(Long locationId) {
		ModelAndView mv = new ModelAndView("/locations/pitchesForAdminLocation");
		
		PitchLocation location = pitchLocationService.retrieve(locationId);
		
		List<Pitch> pitches = new ArrayList<>();
		pitches = pitchesService.findPitchesByLocation(location);
		
		mv.addObject("location", location);
		mv.addObject("locationId", locationId);
		mv.addObject("pitches", pitches);
		return mv;
	}
}
