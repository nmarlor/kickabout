package nmarlor.kickabout.pitch;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.account.AccountService;
import nmarlor.kickabout.date.DateService;

@Controller
public class LocationController {
	
	@Autowired
	private PitchesService pitchesService;
	
	@Autowired
	private AvailabilityService availabilityService;
	
	@Autowired
	private PitchLocationService pitchLocationService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private NewLocationValidator locationValidator;
	
	@Autowired
	private PitchImageValidator imageValidator;
	
	@Autowired
	private DateService dateService;

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
		
		ArrayList<String> times = new ArrayList<>();
		
		String eight = "08:00";
		String nine = "09:00";
		String ten = "10:00";
		String eleven = "11:00";
		String twelve = "12:00";
		String thirteen = "13:00";
		String fourteen = "14:00";
		String fifteen = "15:00";
		String sixteen = "16:00";
		String seventeen = "17:00";
		String eighteen = "18:00";
		String nineteen = "19:00";
		String twenty = "20:00";
		String twentyOne = "21:00";
		String twentyTwo = "22:00";
		String twentyThree = "23:00";
		String tewntyFour = "24:00";
		
		times.add(eight);
		times.add(nine);
		times.add(ten);
		times.add(eleven);
		times.add(twelve);
		times.add(thirteen);
		times.add(fourteen);
		times.add(fifteen);
		times.add(sixteen);
		times.add(seventeen);
		times.add(eighteen);
		times.add(nineteen);
		times.add(twenty);
		times.add(twentyOne);
		times.add(twentyTwo);
		times.add(twentyThree);
		times.add(tewntyFour);
		
		pitchLocationForm.setTimes(times);
		
		mv.addObject("pitchLocationForm", pitchLocationForm);
		mv.addObject("times", times);
		return mv;
	}
	
	@RequestMapping(value = "addPitchLocation", method = RequestMethod.POST)
	public ModelAndView submitPitchLocation(@ModelAttribute("pitchLocationForm") NewPitchLocationForm pitchLocationForm, BindingResult bindingResult, @RequestParam("file") MultipartFile uploadedFile){
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
		pitchLocation.setName(pitchLocationForm.getName());
		pitchLocation.setCounty(pitchLocationForm.getCounty());
		pitchLocation.setEmail(pitchLocationForm.getEmail());
		pitchLocation.setPostCode(pitchLocationForm.getPostcode());
		pitchLocation.setTelephone(pitchLocationForm.getTelephone());
		
		Errors errors = bindingResult;
		
		imageValidator.validate(uploadedFile, bindingResult);
		if (bindingResult.hasErrors()) 
		{
			errors.reject("image.errors.message", "X");
			
			thisMv.addObject("errors", bindingResult);
			return thisMv;
		}
		
		if (!uploadedFile.isEmpty()) 
		{
			try 
			{
				byte[] image = uploadedFile.getBytes();
				pitchLocation.setImage(image);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		pitchLocationService.createPitchLocation(pitchLocation);
		
		// TODO Break the below code out into a service
		List<Availability> availabilities = new ArrayList<>();
		
		String format = ":00";
		
		String mondayFrom = pitchLocationForm.getMonday_from();
		mondayFrom = mondayFrom + format;
		String mondayTo = pitchLocationForm.getMonday_to();
		mondayTo = mondayTo + format;
		Time convertedMondayFrom = dateService.stringToTime(mondayFrom);
		Time convertedMondayTo = dateService.stringToTime(mondayTo);
		
		Availability monday = new Availability();
		monday.setDay("Monday");
		monday.setAvailableFrom(convertedMondayFrom);
		monday.setAvailableTo(convertedMondayTo);
		monday.setPitchLocation(pitchLocation);
		availabilities.add(monday);
		
		String tuesdayFrom = pitchLocationForm.getTuesday_from();
		tuesdayFrom = tuesdayFrom + format;
		String tuesdayTo = pitchLocationForm.getTuesday_to();
		tuesdayTo = tuesdayTo + format;
		Time convertedTuesdayFrom = dateService.stringToTime(tuesdayFrom);
		Time convertedTuesdayTo = dateService.stringToTime(tuesdayTo);
		
		Availability tuesday = new Availability();
		tuesday.setDay("Tuesday");
		tuesday.setAvailableFrom(convertedTuesdayFrom);
		tuesday.setAvailableTo(convertedTuesdayTo);
		tuesday.setPitchLocation(pitchLocation);
		availabilities.add(tuesday);
		
		String wednesdayFrom = pitchLocationForm.getWednesday_from();
		wednesdayFrom = wednesdayFrom + format;
		String wednesdayTo = pitchLocationForm.getWednesday_to();
		wednesdayTo = wednesdayTo + format;
		Time convertedWednesdayFrom = dateService.stringToTime(wednesdayFrom);
		Time convertedWednesdayTo = dateService.stringToTime(wednesdayTo);
		
		Availability wednesday = new Availability();
		wednesday.setDay("Wednesday");
		wednesday.setAvailableFrom(convertedWednesdayFrom);
		wednesday.setAvailableTo(convertedWednesdayTo);
		wednesday.setPitchLocation(pitchLocation);
		availabilities.add(wednesday);
		
		String thursdayFrom = pitchLocationForm.getThursday_from();
		thursdayFrom = thursdayFrom + format;
		String thursdayTo = pitchLocationForm.getThursday_to();
		thursdayTo = thursdayTo + format;
		Time convertedThursdayFrom = dateService.stringToTime(thursdayFrom);
		Time convertedThursdayTo = dateService.stringToTime(thursdayTo);
		
		Availability thursday = new Availability();
		thursday.setDay("Thursday");
		thursday.setAvailableFrom(convertedThursdayFrom);
		thursday.setAvailableTo(convertedThursdayTo);
		thursday.setPitchLocation(pitchLocation);
		availabilities.add(thursday);
		
		String fridayFrom = pitchLocationForm.getFriday_from();
		fridayFrom = fridayFrom + format;
		String fridayTo = pitchLocationForm.getFriday_to();
		fridayTo = fridayTo + format;
		Time convertedFridayFrom = dateService.stringToTime(fridayFrom);
		Time convertedFridayTo = dateService.stringToTime(fridayTo);
		
		Availability friday = new Availability();
		friday.setDay("Friday");
		friday.setAvailableFrom(convertedFridayFrom);
		friday.setAvailableTo(convertedFridayTo);
		friday.setPitchLocation(pitchLocation);
		availabilities.add(friday);
		
		String saturdayFrom = pitchLocationForm.getSaturday_from();
		saturdayFrom = saturdayFrom + format;
		String saturdayTo = pitchLocationForm.getSaturday_to();
		saturdayTo = saturdayTo + format;
		Time convertedSaturdayFrom = dateService.stringToTime(saturdayFrom);
		Time convertedSaturdayTo = dateService.stringToTime(saturdayTo);
		
		Availability saturday = new Availability();
		saturday.setDay("Saturday");
		saturday.setAvailableFrom(convertedSaturdayFrom);
		saturday.setAvailableTo(convertedSaturdayTo);
		saturday.setPitchLocation(pitchLocation);
		availabilities.add(saturday);
		
		String sundayFrom = pitchLocationForm.getSunday_from();
		sundayFrom = sundayFrom + format;
		String sundayTo = pitchLocationForm.getSunday_to();
		sundayTo = sundayTo + format;
		Time convertedSundayFrom = dateService.stringToTime(sundayFrom);
		Time convertedSundayTo = dateService.stringToTime(sundayTo);
		
		Availability sunday = new Availability();
		sunday.setDay("Sunday");
		sunday.setAvailableFrom(convertedSundayFrom);
		sunday.setAvailableTo(convertedSundayTo);
		sunday.setPitchLocation(pitchLocation);
		availabilities.add(sunday);
		
		availabilityService.createAll(availabilities);
		
		ModelAndView successMv = new ModelAndView("locations/successfulLocationCreation");
		List<PitchLocation> locationsForAccount = pitchLocationService.findPitchLocationsForAccount(account);
		successMv.addObject("locationsForAccount", locationsForAccount);
		successMv.addObject("accountId", accountId);
		
		return successMv;
	}
}
