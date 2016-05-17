package nmarlor.kickabout.booking;

import java.security.Principal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import nmarlor.kickabout.account.AccountService;
import nmarlor.kickabout.date.DateService;
import nmarlor.kickabout.pitch.Pitch;
import nmarlor.kickabout.pitch.PitchesService;

@Controller
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PitchesService pitchesService;
	
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
		
		BookingForm bookingForm = new BookingForm();
		bookingForm.setCost(pitch.getCost());
		bookingForm.setPitchId(pitchId);
		bookingForm.setDate(date);
		
		result.addObject("bookingForm", bookingForm);
		result.addObject("pitch", pitch);
		result.addObject("date", date);
		
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
		
		String bookedFrom = bookingForm.getBookedFrom();
		String bookedTo = bookingForm.getBookedTo();
		
		Time formattedBookedFrom = dateService.stringToTime(bookedFrom);
		Time formattedBookedTo = dateService.stringToTime(bookedTo);
		
		if (formattedBookedTo != null && formattedBookedFrom != null) {
			if (formattedBookedTo.before(formattedBookedFrom)) {
				bindingResult.rejectValue("bookedTo", "bookedTo.error");
				thisMv.addObject("errors", bindingResult);
				return thisMv;
			}
		}
		
		if (formattedBookedFrom != null && formattedBookedTo != null) {
			if (formattedBookedFrom.after(formattedBookedTo)) {
				bindingResult.rejectValue("bookedFrom", "bookedFrom.error");
				thisMv.addObject("errors", bindingResult);
				return thisMv;
			}
		}
		
		String bookingName = bookingForm.getName();
		
		String name = principal.getName();
		Account account = accountRepository.findByEmail(name);
		
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
				thisMv.addObject("errors", bindingResult);
				return thisMv;
			}
			if (formattedBookedFrom.after(bookedDate.getPitch().getAvailableTo())) {
				bindingResult.rejectValue("bookedFrom", "bookedAfterAvailableTo.message");
				thisMv.addObject("errors", bindingResult);
				return thisMv;
			}
			if (formattedBookedTo.before(bookedDate.getPitch().getAvailableFrom())) {
				bindingResult.rejectValue("bookedTo", "bookedBeforeAvailableFrom.message");
				thisMv.addObject("errors", bindingResult);
				return thisMv;
			}
			if (formattedBookedTo.after(bookedDate.getPitch().getAvailableTo())) {
				bindingResult.rejectValue("bookedTo", "bookedAfterAvailableTo.message");
				thisMv.addObject("errors", bindingResult);
				return thisMv;
			}
		}
		
		try {
			//TODO When payment is introduced, if user can't pay then catch exception and delete previously create pitchAvailability
			bookingService.createBooking(booking);
		} catch (Exception e) {
			ModelAndView duplicateMv = new ModelAndView("booking/duplicateBooking");
			duplicateMv.addObject("pitch", pitch);
			return duplicateMv;
		}
				
		ModelAndView successMv = new ModelAndView("booking/bookingSuccessful");
		successMv.addObject("booking", booking);
		
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
	
	@RequestMapping(value = "cancelMyBooking", method = RequestMethod.GET)
	public ModelAndView cancelMyBookingRequest(Long id){
		ModelAndView mv = new ModelAndView("booking/cancelMyBooking");
		
		Booking booking = bookingService.retrieve(id);
		
		DeleteBookingForm bookingForm = new DeleteBookingForm();
		bookingForm.setBookingId(booking.getId());
		
		mv.addObject("bookingForm", bookingForm);
		
		return mv;
	}
	
	@RequestMapping(value = "cancelMyBooking", method = RequestMethod.POST)
	public ModelAndView cancelMyBooking(@ModelAttribute("bookingForm") DeleteBookingForm bookingForm, BindingResult result, HttpServletRequest request){
		Booking booking = bookingService.retrieve(bookingForm.getBookingId());
		
		bookingService.delete(booking);
		MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
		jsonView.setModelKey("redirect");
		return new ModelAndView (jsonView, "redirect", request.getContextPath() + "booking/myBookings");
	}
	
	@RequestMapping(value = "manageBookings", method = RequestMethod.GET)
	public ModelAndView viewPitchBookings(Long accountId){
		ModelAndView mv = new ModelAndView("booking/manageBookings");
		
		Account account = accountService.retrieveAccount(accountId);
		
		mv.addObject("account", account);
		return mv;
	}
	
	@RequestMapping(value = "myBookingPitchInfo", method = RequestMethod.GET)
	public ModelAndView myBookingPitchInfo(Long id){
		ModelAndView mv = new ModelAndView("booking/myBookingPitchInfo");
		
		Booking booking = bookingService.retrieve(id);
		
		mv.addObject("booking", booking);
		return mv;
	}
}
