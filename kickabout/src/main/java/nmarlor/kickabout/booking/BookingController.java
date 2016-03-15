package nmarlor.kickabout.booking;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView makeBooking(@ModelAttribute("bookingForm") BookingForm bookingForm, BindingResult bindingResult){
		ModelAndView mv = new ModelAndView("booking/bookingSuccessful");
		
		Long pitchId = bookingForm.getPitchId();
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		
		bookingValidator.validate(bookingForm, bindingResult);
		if (bindingResult.hasErrors()) 
		{
			ModelAndView thisMv = new ModelAndView("booking/newBooking");
			thisMv.addObject("pitch", pitch);
			thisMv.addObject("errors", bindingResult);
			return thisMv;
		}
		
		String date = bookingForm.getDate();
		Date formattedDate = dateService.stringToDate(date);
		
		PitchAvailability pitchAvailability = new PitchAvailability();
		pitchAvailability.setPitch(pitch);
		pitchAvailability.setDate(formattedDate);
		pitchAvailability.setBookedFrom(bookingForm.getBookedFrom());
		pitchAvailability.setBookedTo(bookingForm.getBookedTo());
		
		try {
			pitchAvailabilityService.createPitchAvailability(pitchAvailability);
		} catch (Exception e) {
			ModelAndView thisMv = new ModelAndView("booking/duplicateBooking");
			thisMv.addObject("pitch", pitch);
			return thisMv;
		}
		
		Booking booking = new Booking();
		booking.setPitchAvailability(pitchAvailability);
		booking.setBookedFrom(bookingForm.getBookedFrom());
		booking.setBookedTo(bookingForm.getBookedTo());
		booking.setDate(formattedDate);
		booking.setEmail(bookingForm.getEmail());
		booking.setName(bookingForm.getName());
		bookingService.createBooking(booking);
		
		mv.addObject("booking", booking);
		mv.addObject("pitchAvailability", pitchAvailability);
		
		return mv;
	}
}
