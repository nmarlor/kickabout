package nmarlor.kickabout.home;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import nmarlor.kickabout.pitch.LocationForm;
import nmarlor.kickabout.pitch.PitchLocation;
import nmarlor.kickabout.pitch.PitchLocationService;

@Controller
public class HomeController {
	
	@Autowired
	private PitchLocationService pitchLocationService;
	
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String index(Principal principal) {
//		return principal != null ? "home/homeSignedIn" : "home/homeNotSignedIn";
//	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(Principal principal){
		ModelAndView mv = new ModelAndView("home/homepage");
		
		LocationForm locationForm = new LocationForm();
		mv.addObject("locationForm", locationForm);
		
		return mv;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView index(@Valid @ModelAttribute("locationForm") LocationForm locationForm, BindingResult bindingResult){
		ModelAndView mv = new ModelAndView("locations/locations");
		String searchName = locationForm.getName();
		
		List<PitchLocation> locations = pitchLocationService.findAllLocationsByTownCityOrPostcode(searchName);
		
		if (locations.isEmpty()) {
			//TODO Need to create a new view for a failed search
//			ModelAndView failedSearch = new ModelAndView("home/failedLocationSearch");
//			failedSearch.addObject("name", searchName);
			ModelAndView failedSearch = new ModelAndView("home/homepage");
			return failedSearch;
		}
		
		mv.addObject("locations", locations);
		mv.addObject("locationForm", locationForm);
		mv.addObject("searchName", searchName);
		
		return mv;
	}
}
