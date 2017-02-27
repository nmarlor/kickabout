package nmarlor.kickabout.home;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
import nmarlor.kickabout.pitch.SortByLocationName;
import nmarlor.kickabout.pitch.Sports;
import nmarlor.kickabout.pitch.SportsService;

@Controller
public class HomeController {
	
	@Autowired
	private PitchLocationService pitchLocationService;
	
	@Autowired
	private SportsService sportsService;
	
	@Autowired
	private LocationSearchValidator locationSearchValidator;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(Principal principal){
		ModelAndView mv = new ModelAndView("home/homepage");
		
		LocationForm locationForm = new LocationForm();
		mv.addObject("locationForm", locationForm);
		
		return mv;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView index(@Valid @ModelAttribute("locationForm") LocationForm locationForm, BindingResult bindingResult){
		ModelAndView mv = new ModelAndView("locations/locationsSearch");
		
		locationSearchValidator.validate(locationForm, bindingResult);
		if (bindingResult.hasErrors()) 
		{
			ModelAndView thisMv = new ModelAndView("home/homepage");
			thisMv.addObject("errors", bindingResult);
			return thisMv;
		}
		
		String searchName = locationForm.getName();
		String sport = locationForm.getSport();
		
		List<PitchLocation> retrievedLocations = pitchLocationService.findAllLocationsByTownOrCity(searchName);
		
		if (retrievedLocations.isEmpty()) {
			retrievedLocations = pitchLocationService.findAllLocationsByCounty(searchName);
		}
		
		if (retrievedLocations.isEmpty()) {
			ModelAndView failedSearch = new ModelAndView("home/failedLocationSearch");
			failedSearch.addObject("name", searchName);
			return failedSearch;
		}
		
		List<Sports> sportsForSearch = sportsService.findSportsByName(sport);
		List<PitchLocation> locationsForSports = new ArrayList<PitchLocation>();
		
		for (Sports sports : sportsForSearch) 
		{
			PitchLocation location = sports.getPitch().getPitchLocation();
			locationsForSports.add(location);
			
		}
		
		List<PitchLocation> locations = new ArrayList<PitchLocation>();
		
		for (PitchLocation pitchLocation : locationsForSports) 
		{
			if (pitchLocation.getCity().equalsIgnoreCase(searchName) || pitchLocation.getCounty().equalsIgnoreCase(searchName)) 
			{
				locations.add(pitchLocation);
			}
		}
		
		if (locations.isEmpty()) 
		{
			ModelAndView failedSearch = new ModelAndView("home/failedSportSearch");
			failedSearch.addObject("name", searchName);
			failedSearch.addObject("sport", sport);
			return failedSearch;
		}
		
		Set<PitchLocation> deleteDuplicates = new TreeSet<PitchLocation>(new SortByLocationName());
		deleteDuplicates.addAll(locations);
		locations.clear();
		locations.addAll(deleteDuplicates);
		
		mv.addObject("locations", locations);
		mv.addObject("locationForm", locationForm);
		mv.addObject("sport", sport);
		mv.addObject("searchName", searchName);
		
		return mv;
	}
}
