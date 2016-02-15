package nmarlor.kickabout.pitch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import nmarlor.kickabout.lib.CRUDDAO;

@Controller
class LocationController {
	
	@Autowired
	private CRUDDAO dao;

	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	public ModelAndView Locations() {
		ModelAndView mv = new ModelAndView("locations/pitches");
		
		List<PitchLocation> allPitches = dao.retrieveAll(PitchLocation.class);
		mv.addObject("pitches", allPitches);
		
		return mv;
	}
}
