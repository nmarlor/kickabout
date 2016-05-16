package nmarlor.kickabout.pitch;

import java.security.Principal;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.account.AccountRepository;
import nmarlor.kickabout.booking.AdminBookingValidator;
import nmarlor.kickabout.booking.Booking;
import nmarlor.kickabout.booking.BookingForm;
import nmarlor.kickabout.booking.BookingService;
import nmarlor.kickabout.booking.DeleteBookingForm;
import nmarlor.kickabout.date.DateService;

@Controller
public class PitchAvailabilityController {
	
	@Autowired
	private PitchesService pitchesService;
	
	@Autowired
	private PitchFeatureService PitchFeatureService;
	
	@Autowired
	private DateService dateService;
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PitchLocationService locationService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private AdminBookingValidator bookingValidator;
	
	@RequestMapping(value = "/availability", method = RequestMethod.GET)
	public ModelAndView pitchAvailability(Long pitchId){
		ModelAndView mv = new ModelAndView("pitchAvailability/availabilityAndFeatures");
		
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		Date formattedDate = dateService.getTodaysDate();
		
		List<PitchFeature> pitchFeatures = PitchFeatureService.findPitchFeaturesByPitch(pitch);
		List<Booking> pitchAvailabilities = bookingService.findBookingsByPitchAndDate(pitch, formattedDate);
		
		PitchForm pitchForm = new PitchForm();
		pitchForm.setPitchId(pitchId);
		
		// Date to be displayed on the front end
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String date = df.format(formattedDate);
		
		mv.addObject("pitchId", pitchId);
		mv.addObject("pitch", pitch);
		mv.addObject("pitchFeatures", pitchFeatures);
		mv.addObject("pitchForm", pitchForm);
		mv.addObject("date", date);
		mv.addObject("pitchAvailabilities", pitchAvailabilities);
		
		return mv;
	}
	
	@RequestMapping(value = "/availability", method = RequestMethod.POST)
	public ModelAndView pitchAvailability(@Valid @ModelAttribute("pitchForm") PitchForm pitchForm, BindingResult bindingResult, String date) {
		ModelAndView mv = new ModelAndView("pitchAvailability/availabilityAndFeatures");
		mv.addObject("pitchForm", pitchForm);
		
		Long pitchId = pitchForm.getPitchId();
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		List<PitchFeature> pitchFeatures = PitchFeatureService.findPitchFeaturesByPitch(pitch);
		
		if (!date.isEmpty()) {
			Date availabilityDate = dateService.stringToDate(date);
			List<Booking> pitchAvailabilities = bookingService.findBookingsByPitchAndDate(pitch, availabilityDate);
			mv.addObject("pitchAvailabilities", pitchAvailabilities);
		}

		mv.addObject("pitchForm", pitchForm);
		mv.addObject("pitch", pitch);
		mv.addObject("date", date);
		mv.addObject("pitchFeatures", pitchFeatures);
		
		return mv;
	}
	
	@RequestMapping(value = "/adminPitchAvailability", method = RequestMethod.GET)
	public ModelAndView adminPitchAvailability(Long pitchId){
		ModelAndView mv = new ModelAndView("pitchAvailability/adminPitchAvailability");
		
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		Date formattedDate = dateService.getTodaysDate();
		
		List<Booking> pitchAvailabilities = bookingService.findBookingsByPitchAndDate(pitch, formattedDate);
		
		PitchForm pitchForm = new PitchForm();
		pitchForm.setPitchId(pitchId);
		
		// Date to be displayed on the front end
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String date = df.format(formattedDate);
		
		mv.addObject("pitchId", pitchId);
		mv.addObject("pitch", pitch);
		mv.addObject("pitchForm", pitchForm);
		mv.addObject("date", date);
		mv.addObject("pitchAvailabilities", pitchAvailabilities);
		
		return mv;
	}
	
	@RequestMapping(value = "/adminPitchAvailability", method = RequestMethod.POST)
	public ModelAndView adminPitchAvailability(@Valid @ModelAttribute("pitchForm") PitchForm pitchForm, BindingResult bindingResult, String date) {
		ModelAndView mv = new ModelAndView("pitchAvailability/adminPitchAvailability");
		mv.addObject("pitchForm", pitchForm);
		
		Long pitchId = pitchForm.getPitchId();
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		
		if (!date.isEmpty()) {
			Date availabilityDate = dateService.stringToDate(date);
			List<Booking> pitchAvailabilities = bookingService.findBookingsByPitchAndDate(pitch, availabilityDate);
			mv.addObject("pitchAvailabilities", pitchAvailabilities);
		}

		mv.addObject("pitchForm", pitchForm);
		mv.addObject("pitch", pitch);
		mv.addObject("date", date);
		
		return mv;
	}
	
	@RequestMapping(value = "adminCancelBooking", method = RequestMethod.GET)
	public ModelAndView adminCancelBookingRequest(Long id){
		ModelAndView mv = new ModelAndView("pitchAvailability/adminCancelBooking");
		
		Booking booking = bookingService.retrieve(id);
		
		DeleteBookingForm bookingForm = new DeleteBookingForm();
		bookingForm.setBookingId(booking.getId());
		
		mv.addObject("bookingForm", bookingForm);
		
		return mv;
	}
	
	@RequestMapping(value = "adminCancelBooking", method = RequestMethod.POST)
	public ModelAndView adminCancelBooking(@ModelAttribute("bookingForm") DeleteBookingForm bookingForm, BindingResult result, HttpServletRequest request){
		Booking booking = bookingService.retrieve(bookingForm.getBookingId());
		
		bookingService.delete(booking);
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("redirect");
		return new ModelAndView (jsonView, "redirect", request.getContextPath() + "pitchAvailability/adminPitchAvailability");
	}
	
	@RequestMapping(value = "/viewBookingsForAllPitches", method = RequestMethod.GET)
	public ModelAndView viewBookingsForAllPitches(Long locationId){
		ModelAndView mv = new ModelAndView("booking/viewBookingsForAllPitches");
		
		LocationForm locationForm = new LocationForm();
		locationForm.setLocationId(locationId);
		
		PitchLocation location = locationService.retrieve(locationId);
		List<Pitch> pitches = pitchesService.findPitchesByLocation(location);
		
		Date formattedDate = dateService.getTodaysDate();
		
		List<Booking> bookings = new ArrayList<>();
		for (Pitch pitch : pitches) 
		{
			List<Booking> pitchAvailabilities = bookingService.findBookingsByPitchAndDate(pitch, formattedDate);
			bookings.addAll(pitchAvailabilities);
		}
		
		// Date to be displayed on the front end
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		String date = df.format(formattedDate);
		
		mv.addObject("location", location);
		mv.addObject("locationId", locationId);
		mv.addObject("date", date);
		mv.addObject("bookings", bookings);
		mv.addObject("locationForm", locationForm);
		
		return mv;
	}
	
	@RequestMapping(value = "/viewBookingsForAllPitches", method = RequestMethod.POST)
	public ModelAndView viewBookingsForAllPitches(@Valid @ModelAttribute("locationForm") LocationForm locationForm, BindingResult bindingResult, String date) {
		ModelAndView mv = new ModelAndView("booking/viewBookingsForAllPitches");
		mv.addObject("locationForm", locationForm);
		
		Long locationId = locationForm.getLocationId();
		
		PitchLocation location = locationService.retrieve(locationId);
		List<Pitch> pitches = pitchesService.findPitchesByLocation(location);
		
		Date availabilityDate = dateService.stringToDate(date);
		
		List<Booking> bookings = new ArrayList<>();
		if (availabilityDate != null) {
			
			for (Pitch pitch : pitches) 
			{
				List<Booking> pitchAvailabilities = bookingService.findBookingsByPitchAndDate(pitch, availabilityDate);
				bookings.addAll(pitchAvailabilities);
			}
		}

		// Date to be displayed on the front end
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		date = df.format(availabilityDate);
		
		mv.addObject("locationForm", locationForm);
		mv.addObject("location", location);
		mv.addObject("locationId", locationId);
		mv.addObject("date", date);
		mv.addObject("bookings", bookings);
				
		return mv;
	}
	
	@RequestMapping(value = "adminAddBooking", method = RequestMethod.GET)
	public ModelAndView adminAddBookingRequest(Long id, String selectedDate){
		ModelAndView mv = new ModelAndView("booking/adminAddBooking");
		
		BookingForm bookingForm = new BookingForm();
		bookingForm.setPitchId(id);
		bookingForm.setDate(selectedDate);
		
		mv.addObject("bookingForm", bookingForm);
		return mv;
	}
	
	@RequestMapping(value = "adminAddBooking", method = RequestMethod.POST)
	public ModelAndView adminAddBooking(@ModelAttribute("bookingForm") BookingForm bookingForm, Principal principal, BindingResult bindingResult, HttpServletRequest request){
		Long pitchId = bookingForm.getPitchId();
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		
		ModelAndView mv = new ModelAndView("booking/adminAddBooking");
		mv.addObject("pitch", pitch);
		
		bookingValidator.validate(bookingForm, bindingResult);
		if (bindingResult.hasErrors()) 
		{
			mv.addObject("errors", bindingResult);
			return mv;
		}
		
		String date = bookingForm.getDate();
		Date formattedDate = dateService.stringToDate(date);
		String bookedFrom = bookingForm.getBookedFrom();
		String bookedTo = bookingForm.getBookedTo();
		String bookingName = bookingForm.getName();
		
		String name = principal.getName();
		Account account = accountRepository.findByEmail(name);
		
		Time formattedBookedFrom = dateService.stringToTime(bookedFrom);
		Time formattedBookedTo = dateService.stringToTime(bookedTo);
		
		if (formattedBookedTo != null && formattedBookedFrom != null) {
			if (formattedBookedTo.before(formattedBookedFrom)) {
				bindingResult.rejectValue("bookedTo", "bookedTo.error");
				mv.addObject("errors", bindingResult);
				return mv;
			}
		}
		
		if (formattedBookedFrom != null && formattedBookedTo != null) {
			if (formattedBookedFrom.after(formattedBookedTo)) {
				bindingResult.rejectValue("bookedFrom", "bookedFrom.error");
				mv.addObject("errors", bindingResult);
				return mv;
			}
		}
		
		Booking booking = new Booking();
		booking.setAccount(account);
		booking.setPitch(pitch);
		booking.setBookedFrom(formattedBookedFrom);
		booking.setBookedTo(formattedBookedTo);
		booking.setCost(bookingForm.getCost());
		booking.setDate(formattedDate);
		booking.setEmail(bookingForm.getEmail());
		booking.setName(bookingName);
		
		List<Booking> bookedDates = bookingService.findBookingsByPitchAndDate(pitch, formattedDate);
		for (Booking bookedDate : bookedDates) 
		{
			if (formattedBookedFrom.before(bookedDate.getPitch().getAvailableFrom()) ) {
				bindingResult.rejectValue("bookedFrom", "bookedBeforeAvailableFrom.message");
				mv.addObject("errors", bindingResult);
				return mv;
			}
			if (formattedBookedFrom.after(bookedDate.getPitch().getAvailableTo())) {
				bindingResult.rejectValue("bookedFrom", "bookedAfterAvailableTo.message");
				mv.addObject("errors", bindingResult);
				return mv;
			}
			if (formattedBookedTo.before(bookedDate.getPitch().getAvailableFrom())) {
				bindingResult.rejectValue("bookedTo", "bookedBeforeAvailableFrom.message");
				mv.addObject("errors", bindingResult);
				return mv;
			}
			if (formattedBookedTo.after(bookedDate.getPitch().getAvailableTo())) {
				bindingResult.rejectValue("bookedTo", "bookedAfterAvailableTo.message");
				mv.addObject("errors", bindingResult);
				return mv;
			}
		}
		
		try {
			//TODO When payment is introduced, if user can't pay then catch exception and delete previously create pitchAvailability
			bookingService.createBooking(booking);
		} catch (Exception e) {
			mv.addObject("pitch", pitch);
			return mv;
		}
		
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("redirect");
		return new ModelAndView (jsonView, "redirect", request.getContextPath() + "pitchAvailability/adminPitchAvailability");
	}
}
