package nmarlor.kickabout.pitch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.account.AccountService;

@Controller
public class LocationController {
	
	@Autowired
	private PitchesService pitchesService;
	
	@Autowired
	private PitchLocationService pitchLocationService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private NewLocationValidator locationValidator;

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
	
	@RequestMapping(value = "addPitchLocation", method = RequestMethod.GET)
	public ModelAndView addPitchLocation(Long accountId){
		ModelAndView mv = new ModelAndView("locations/newPitchLocation");
		
		NewPitchLocationForm pitchLocationForm = new NewPitchLocationForm();
		pitchLocationForm.setAccountId(accountId);
		
		mv.addObject("pitchLocationForm", pitchLocationForm);
		return mv;
	}
	
	@RequestMapping(value = "addPitchLocation", method = RequestMethod.POST)
	public ModelAndView submitPitchLocation(@ModelAttribute("pitchLocationForm") NewPitchLocationForm pitchLocationForm, BindingResult bindingResult){
		ModelAndView thisMv = new ModelAndView("locations/newPitchLocation");
		
		Long accountId = pitchLocationForm.getAccountId();
		Account account = accountService.retrieveAccount(accountId);
		
		locationValidator.validate(pitchLocationForm, bindingResult);
		if (bindingResult.hasErrors()) 
		{
			thisMv.addObject("errors", bindingResult);
			return thisMv;
		}
		
		PitchLocation pitchLocation = new PitchLocation();
		pitchLocation.setAccount(account);
		pitchLocation.setAddressLine1(pitchLocationForm.getAddressLine1());
		pitchLocation.setAddressLine2(pitchLocationForm.getAddressLine2());
		pitchLocation.setCity(pitchLocationForm.getCity());
		pitchLocation.setCompany(pitchLocationForm.getCompany());
		pitchLocation.setCounty(pitchLocationForm.getCounty());
		pitchLocation.setEmail(pitchLocationForm.getEmail());
		pitchLocation.setPostCode(pitchLocationForm.getPostcode());
		pitchLocation.setTelephone(pitchLocationForm.getTelephone());
		
		pitchLocationService.createPitchLocation(pitchLocation);
		
		ModelAndView successMv = new ModelAndView("locations/successfulLocationCreation");
		List<PitchLocation> locationsForAccount = pitchLocationService.findPitchLocationsForAccount(account);
		successMv.addObject("locationsForAccount", locationsForAccount);
		successMv.addObject("accountId", accountId);
		
		return successMv;
	}
}
