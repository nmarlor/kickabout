package nmarlor.kickabout.pitch;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import nmarlor.kickabout.booking.BookVenueForm;
import nmarlor.kickabout.booking.Booking;
import nmarlor.kickabout.booking.BookingService;
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
	
	@Autowired
	private FacilitiesService facilitiesService;
	
	@Autowired
	private SportsService sportsService;
	
	@Autowired
	private CheckBookingValidator checkBookingValidator;
	
	@Autowired
	private BookingService bookingService;

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
	
	@RequestMapping(value = "location", method=RequestMethod.GET)
	public ModelAndView pitches(String name, String sport) {
		ModelAndView mv = new ModelAndView("/locations/location");
		
		BookVenueForm venueForm = new BookVenueForm();
		
		PitchLocation location = pitchLocationService.findByName(name);
		Long locationId = location.getId();
		
		ArrayList<String> facilities = new ArrayList<>();
		List<Facilities> retrievedFacilities = facilitiesService.findFacilitiesForLocation(location);

		for (Facilities retrievedFacility : retrievedFacilities) 
		{
			String facility = retrievedFacility.getFacility();
			facilities.add(facility);
		}
		
		List<Pitch> pitches = new ArrayList<>();
		pitches = pitchesService.findPitchesByLocation(location);
		
		ArrayList<String> surfaces = new ArrayList<>();
		ArrayList<String> sports = new ArrayList<>();
		ArrayList<String> environments = new ArrayList<>();
		
		List<Sports> sportsAvailable = new ArrayList<>();
		
		for (Pitch pitch : pitches) 
		{
			List<Sports> sportsForPitch = sportsService.findAvailableSportsByPitch(pitch);
			sportsAvailable.addAll(sportsForPitch);
		}
		
		Set<Sports> sortSports = new TreeSet<Sports>(new SortBySportName());
		sortSports.addAll(sportsAvailable);
		sportsAvailable.clear();
		sportsAvailable.addAll(sortSports);
		
		for (Sports sportAvailable : sportsAvailable) 
		{
			String sportToAdd = sportAvailable.getSport();
			sports.add(sportToAdd);
		}
		
		List<Pitch> surfacesForPitches = pitchesService.findPitchesByLocation(location);
			
		Set<Pitch> sortSurfaces = new TreeSet<Pitch>(new SortBySurface());
		sortSurfaces.addAll(surfacesForPitches);
		surfacesForPitches.clear();
		surfacesForPitches.addAll(sortSurfaces);
		
		for (Pitch pitch : surfacesForPitches) 
		{
			String surface = pitch.getSurface();
			surfaces.add(surface);
		}
		
		List<Pitch> environmentsForPitches = pitchesService.findPitchesByLocation(location);
		
		Set<Pitch> sortEnvironemnts = new TreeSet<Pitch>(new SortByEnvironment());
		sortEnvironemnts.addAll(environmentsForPitches);
		environmentsForPitches.clear();
		environmentsForPitches.addAll(sortEnvironemnts);
		
		for (Pitch pitch : environmentsForPitches) 
		{
			String environment = pitch.getEnvironment();
			environments.add(environment);
		}
		
		Calendar calendar = Calendar.getInstance();
		String dayOfWeek = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
		
		Availability availability = availabilityService.findByLocationAndDay(location, dayOfWeek);
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date avaialableFrom = availability.getAvailableFrom();
		String from = sdf.format(avaialableFrom);
		Date avaialableTo = availability.getAvailableTo();
		String to = sdf.format(avaialableTo);
		
		venueForm.setLocationId(locationId);
		venueForm.setSearchedName(name);
		venueForm.setSearchedSport(sport);
		
		mv.addObject("location", location);
		mv.addObject("locationId", locationId);
		mv.addObject("sport", sport);
		mv.addObject("facilities", facilities);
		mv.addObject("surfaces", surfaces);
		mv.addObject("sports", sports);
		mv.addObject("from", from);
		mv.addObject("to", to);
		mv.addObject("environments", environments);
		mv.addObject("venueForm", venueForm);
		return mv;
	}
	
	@RequestMapping(value = "location", method = RequestMethod.POST)
	public ModelAndView venueCheck(@ModelAttribute("venueForm") BookVenueForm venueForm, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("locations/checkBooking");
		
		String name = venueForm.getSearchedName();
		String sport = venueForm.getSport();
		String duration = venueForm.getDuration();
		
		PitchLocation location = pitchLocationService.findByName(name);
		Long locationId = location.getId();
		
		Calendar calendar = Calendar.getInstance();
		String dayOfWeek = getDayOfWeek(calendar.get(Calendar.DAY_OF_WEEK));
		
		Availability availability = availabilityService.findByLocationAndDay(location, dayOfWeek);
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date avaialableFrom = availability.getAvailableFrom();
		String from = sdf.format(avaialableFrom);
		Date avaialableTo = availability.getAvailableTo();
		String to = sdf.format(avaialableTo);
		
		List<Pitch> pitches = new ArrayList<>();
		pitches = pitchesService.findPitchesByLocation(location);
		
		ArrayList<String> sports = new ArrayList<>();
		List<Sports> sportsAvailable = new ArrayList<>();
		
		for (Pitch pitch : pitches) 
		{
			List<Sports> sportsForPitch = sportsService.findAvailableSportsByPitch(pitch);
			sportsAvailable.addAll(sportsForPitch);
		}
		
		Set<Sports> sortSports = new TreeSet<Sports>(new SortBySportName());
		sortSports.addAll(sportsAvailable);
		sportsAvailable.clear();
		sportsAvailable.addAll(sortSports);
		
		for (Sports sportAvailable : sportsAvailable) 
		{
			String sportToAdd = sportAvailable.getSport();
			sports.add(sportToAdd);
		}
		
		checkBookingValidator.validate(venueForm, result);
		if (result.hasErrors()) 
		{
			ModelAndView thisMv = new ModelAndView("/locations/location");
			
			ArrayList<String> facilities = new ArrayList<>();
			List<Facilities> retrievedFacilities = facilitiesService.findFacilitiesForLocation(location);

			for (Facilities retrievedFacility : retrievedFacilities) 
			{
				String facility = retrievedFacility.getFacility();
				facilities.add(facility);
			}
			
			ArrayList<String> surfaces = new ArrayList<>();
			
			ArrayList<String> environments = new ArrayList<>();
			
			List<Pitch> surfacesForPitches = pitchesService.findPitchesByLocation(location);
				
			Set<Pitch> sortSurfaces = new TreeSet<Pitch>(new SortBySurface());
			sortSurfaces.addAll(surfacesForPitches);
			surfacesForPitches.clear();
			surfacesForPitches.addAll(sortSurfaces);
			
			for (Pitch pitch : surfacesForPitches) 
			{
				String surface = pitch.getSurface();
				surfaces.add(surface);
			}
			
			List<Pitch> environmentsForPitches = pitchesService.findPitchesByLocation(location);
			
			Set<Pitch> sortEnvironemnts = new TreeSet<Pitch>(new SortByEnvironment());
			sortEnvironemnts.addAll(environmentsForPitches);
			environmentsForPitches.clear();
			environmentsForPitches.addAll(sortEnvironemnts);
			
			for (Pitch pitch : environmentsForPitches) 
			{
				String environment = pitch.getEnvironment();
				environments.add(environment);
			}
			
			thisMv.addObject("name", name);
			thisMv.addObject("sport", sport);
			thisMv.addObject("location", location);
			thisMv.addObject("locationId", locationId);
			thisMv.addObject("facilities", facilities);
			thisMv.addObject("surfaces", surfaces);
			thisMv.addObject("sports", sports);
			thisMv.addObject("from", from);
			thisMv.addObject("to", to);
			thisMv.addObject("environments", environments);
			thisMv.addObject("venueForm", venueForm);
			return thisMv;
		}
		
		List<Pitch> pitchesForLocation = new ArrayList<>();
		pitchesForLocation = pitchesService.findPitchesByLocation(location);
					
		List <Sports> sportsForNameAndPitch = new ArrayList<>();

		for (Pitch pitch : pitchesForLocation)
		{
			List <Sports> findSportsForNameAndPitch = sportsService.findSportsByNameAndPitch(sport, pitch);
			sportsForNameAndPitch.addAll(findSportsForNameAndPitch);
		}
					
		List<Pitch> pitchesForLocationAndSport = new ArrayList<>();
		
		for (Sports sportForNameAndPitch : sportsForNameAndPitch) 
		{
			Pitch pitchForLocationAndSport = sportForNameAndPitch.getPitch();
			pitchesForLocationAndSport.add(pitchForLocationAndSport);
		}
					
		String date = venueForm.getDate();
		String time = venueForm.getTime();
		
		time = time.substring(0, 5);
		String ending = ":00";
		time = time.concat(ending);
		
		Integer durationInt = Integer.valueOf(duration);
		
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date d = null;
		try {
			d = df.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, durationInt);
		String endTime = df.format(cal.getTime());
		
		endTime = endTime.concat(ending);
		
		java.sql.Date formattedDate = dateService.stringToDate(date);
		
		Time formattedTime = dateService.stringToTime(time);
		Time formattedEndTime = dateService.stringToTime(endTime);
		
		List<Pitch> pitchesToRemove = new ArrayList<>();
		
		for (Pitch pitch : pitchesForLocationAndSport) 
		{
			List<Booking> bookedDates = bookingService.findBookingsByPitchAndDate(pitch, formattedDate);
			for (Booking booking : bookedDates) 
			{
				//TODO - delete once certain it isn't required
/*				if ( formattedTime.after(booking.getBookedFrom()) && formattedTime.before(booking.getBookedTo()) 
						|| formattedEndTime.after(booking.getBookedFrom()) && formattedEndTime.before(booking.getBookedTo())) 
				{
					pitchesToRemove.add(pitch);
				}*/
				
				if (formattedTime.after(booking.getBookedFrom()) && formattedTime.before(booking.getBookedTo()) 
						|| formattedTime.equals(booking.getBookedTo()) || formattedTime.equals(booking.getBookedFrom())
						|| formattedEndTime.after(booking.getBookedFrom()) && formattedEndTime.before(booking.getBookedTo())
						|| formattedEndTime.equals(booking.getBookedTo()) || formattedEndTime.equals(booking.getBookedFrom()))
				{
					pitchesToRemove.add(pitch);
				}
				
			}
		}
		
		if (!pitchesToRemove.isEmpty()) 
		{
			pitchesForLocationAndSport.removeAll(pitchesToRemove);
		}
		
		List<Pitch> availablePitches = pitchesForLocationAndSport;
		
		venueForm.setEndTime(endTime);
		venueForm.setTime(time);
		
		mv.addObject("venueForm", venueForm);
		mv.addObject("locationId", locationId);
		mv.addObject("name", name);
		mv.addObject("sport", sport);
		mv.addObject("location", location);
		mv.addObject("from", from);
		mv.addObject("to", to);
		mv.addObject("sports", sports);
		mv.addObject("time", time);
		mv.addObject("endTime", endTime);
		mv.addObject("date", date);
		mv.addObject("duration", duration);
		mv.addObject("availablePitches", availablePitches);
		mv.addObject("formattedTime", formattedTime);
		mv.addObject("formattedEndTime", formattedEndTime);
		
		return mv;
	}
	
	@RequestMapping(value="/checkBooking", method=RequestMethod.GET)
	public ModelAndView checkBooking(BookVenueForm venueForm, Long id)
	{
		ModelAndView mv = new ModelAndView("locations/checkBooking");
		mv.addObject("venueForm", venueForm);
		return mv;
	}
	
	@RequestMapping(value = "/checkBooking", method = RequestMethod.POST)
	public ModelAndView checkBooking(@ModelAttribute("venueForm") BookVenueForm venueForm, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView("booking/makeBooking");
		
		String date = venueForm.getDate();
		Pitch pitch = pitchesService.retrievePitch(venueForm.getPitchId());
		String sport = venueForm.getSport();
		String time = venueForm.getTime();
		String endTime = venueForm.getEndTime();
		
		mv.addObject("venueForm", venueForm);
		mv.addObject("date", date);
		mv.addObject("pitch", pitch);
		mv.addObject("sport", sport);
		mv.addObject("time", time);
		mv.addObject("endTime", endTime);
		return mv;
	}
	
	private String getDayOfWeek(int value){
	    String day = "";
	    switch(value){
	    case 1:
	        day="Sunday";
	        break;
	    case 2:
	        day="Monday";
	        break;
	    case 3:
	        day="Tuesday";
	        break;
	    case 4:
	        day="Wednesday";
	        break;
	    case 5:
	        day="Thursday";
	        break;
	    case 6:
	        day="Friday";
	        break;
	    case 7:
	        day="Saturday";
	        break;
	    }
	    return day;
	}
	
	@RequestMapping(value = "/pitchImage", produces = MediaType.IMAGE_JPEG_VALUE) 
	public ResponseEntity<byte[]> getPitchImage(Long pitchId) throws IOException 
	{ 
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		byte[] imageContent =  pitch.getImage();
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.IMAGE_JPEG); 
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/locationImage", produces = MediaType.IMAGE_JPEG_VALUE) 
	public ResponseEntity<byte[]> getLocationImage(Long locationId) throws IOException 
	{ 
		PitchLocation pitchLocation = pitchLocationService.retrieve(locationId);
		byte[] imageContent =  pitchLocation.getImage();
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.IMAGE_JPEG); 
		return new ResponseEntity<byte[]>(imageContent, headers, HttpStatus.OK);
	}
	
	// TODO - May need to use this logic for finding the cost of a sport for a duration of time, for pitch
	@RequestMapping(value = "/pitchCost") 
	public BigDecimal getPitchCost(Long pitchId, String sport, String duration)
	{ 
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		
		//get sport by sport and pitch
		Sports retrievedSport = sportsService.findSportByNameAndPitch(sport, pitch);
		
		// get price for sport by duration
		if (duration.equals("45")) 
		{
			BigDecimal cost = retrievedSport.getCostPerFourtyFive();
			return cost;
		}
		// get price for sport by duration
		if (duration.equals("60")) 
		{
			BigDecimal cost = retrievedSport.getCostPerSixty();
			return cost;
		}
		// get price for sport by duration
		if (duration.equals("90")) 
		{
			BigDecimal cost = retrievedSport.getCostPerNinety();
			return cost;
		}
		
		return null;
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
