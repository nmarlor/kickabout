package nmarlor.kickabout.booking;

import java.security.Principal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.account.AccountRepository;
import nmarlor.kickabout.account.AccountService;
import nmarlor.kickabout.date.DateService;
import nmarlor.kickabout.pitch.Pitch;
import nmarlor.kickabout.pitch.PitchAvailability;
import nmarlor.kickabout.pitch.PitchAvailabilityService;
import nmarlor.kickabout.pitch.PitchesService;

@Controller
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PitchesService pitchesService;
	
	@Autowired
	private PitchAvailabilityService pitchAvailabilityService;
	
	@Autowired
	private DateService dateService;
	
	@Autowired
	private BookingValidator bookingValidator;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@RequestMapping(value = "booking/newBooking", method = RequestMethod.GET)
	public ModelAndView viewNewBooking(Long pitchId, String date){
		ModelAndView result = new ModelAndView("booking/newBooking");
		
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		PitchAvailability pitchAvailability = new PitchAvailability();
		pitchAvailability.setPitch(pitch);
		
		BookingForm bookingForm = new BookingForm();
		bookingForm.setCost(pitch.getCost());
		bookingForm.setPitchId(pitchId);
		bookingForm.setDate(date);
		
		result.addObject("bookingForm", bookingForm);
		result.addObject("pitch", pitch);
		result.addObject("date", date);
		result.addObject("pitchAvailability", pitchAvailability);
		
		return result;
	}
	
	@RequestMapping(value = "booking/newBooking", method = RequestMethod.POST)
	public ModelAndView makeBooking(@ModelAttribute("bookingForm") BookingForm bookingForm, Principal principal, BindingResult bindingResult){
		Long pitchId = bookingForm.getPitchId();
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		
		ModelAndView thisMv = new ModelAndView("booking/newBooking");
		thisMv.addObject("pitch", pitch);
		
		bookingValidator.validate(bookingForm, bindingResult);
		if (bindingResult.hasErrors()) 
		{
			thisMv.addObject("errors", bindingResult);
			return thisMv;
		}
		
		String date = bookingForm.getDate();
		Date formattedDate = dateService.stringToDate(date);
		
		Time bookedFrom = bookingForm.getBookedFrom();
		Time bookedTo = bookingForm.getBookedTo();
		
		PitchAvailability pitchAvailability = new PitchAvailability();
		pitchAvailability.setPitch(pitch);
		pitchAvailability.setDate(formattedDate);
		pitchAvailability.setBookedFrom(bookedFrom);
		pitchAvailability.setBookedTo(bookedTo);
		
		List<PitchAvailability> bookedDates = pitchAvailabilityService.findPitchAvailabilityByPitchAndDate(pitch, formattedDate);
		for (PitchAvailability bookedDate : bookedDates) 
		{
			if (bookedFrom.before(bookedDate.getPitch().getAvailableFrom()) ) {
				bindingResult.rejectValue("bookedFrom", "bookedBeforeAvailableFrom.message");
				thisMv.addObject("errors", bindingResult);
				return thisMv;
			}
			if (bookedFrom.after(bookedDate.getPitch().getAvailableTo())) {
				bindingResult.rejectValue("bookedFrom", "bookedAfterAvailableTo.message");
				thisMv.addObject("errors", bindingResult);
				return thisMv;
			}
			if (bookedTo.before(bookedDate.getPitch().getAvailableFrom())) {
				bindingResult.rejectValue("bookedTo", "bookedBeforeAvailableFrom.message");
				thisMv.addObject("errors", bindingResult);
				return thisMv;
			}
			if (bookedTo.after(bookedDate.getPitch().getAvailableTo())) {
				bindingResult.rejectValue("bookedTo", "bookedAfterAvailableTo.message");
				thisMv.addObject("errors", bindingResult);
				return thisMv;
			}
		}
		
		try {
			pitchAvailabilityService.createPitchAvailability(pitchAvailability);
		} catch (Exception e) {
			ModelAndView duplicateMv = new ModelAndView("booking/duplicateBooking");
			duplicateMv.addObject("pitch", pitch);
			return duplicateMv;
		}
		
		String name = principal.getName();
		Account account = accountRepository.findByEmail(name);
		
		Booking booking = new Booking();
		booking.setAccount(account);
		booking.setPitchAvailability(pitchAvailability);
		booking.setBookedFrom(bookedFrom);
		booking.setBookedTo(bookedTo);
		booking.setCost(bookingForm.getCost());
		booking.setDate(formattedDate);
		booking.setEmail(bookingForm.getEmail());
		booking.setName(bookingForm.getName());
		
		//TODO When payment is introduced, if user can't pay then catch exception and delete previously create pitchAvailability
		bookingService.createBooking(booking);
		
		ModelAndView successMv = new ModelAndView("booking/bookingSuccessful");
		successMv.addObject("booking", booking);
		successMv.addObject("pitchAvailability", pitchAvailability);
		
		return successMv;
	}
	
	@RequestMapping(value = "myBookings", method = RequestMethod.GET)
	public ModelAndView viewMyBookings(Long accountId){
		ModelAndView mv = new ModelAndView("booking/myBookings");
		
		Account account = accountService.retrieveAccount(accountId);
		List<Booking> bookings = bookingService.findBookingsForAccount(account);
		
		if (bookings.isEmpty()) {
			ModelAndView emptyBookingMv = new ModelAndView("booking/noBookings");
			emptyBookingMv.addObject("bookings", bookings);
			return emptyBookingMv;
		}
		
		mv.addObject("bookings", bookings);
		return mv;
	}
	
	@RequestMapping(value = "manageBookings", method = RequestMethod.GET)
	public ModelAndView viewPitchBookings(Long accountId){
		ModelAndView mv = new ModelAndView("booking/manageBookings");
		
		Account account = accountService.retrieveAccount(accountId);
		
		mv.addObject("account", account);
		return mv;
	}
}
