package nmarlor.kickabout.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import nmarlor.kickabout.pitch.Pitch;
import nmarlor.kickabout.pitch.PitchesService;

@Controller
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private PitchesService pitchesService;
	
	@RequestMapping(value = "booking/newBooking", method = RequestMethod.GET)
	public ModelAndView viewNewBooking(Long pitchId, String date){
		ModelAndView result = new ModelAndView("booking/newBooking");
		
		Booking booking = new Booking();
		Pitch pitch = pitchesService.retrievePitch(pitchId);
		
		result.addObject("booking", booking);
		result.addObject("pitch", pitch);
		result.addObject("date", date);
		
		return result;
	}
	
	@RequestMapping(value = "booking/newBooking", method = RequestMethod.POST)
	public ModelAndView makeBooking(@ModelAttribute Booking booking, BindingResult result){
		ModelAndView mv = new ModelAndView("booking/bookingSuccessful");
		mv.addObject("booking", booking);
		return mv;
	}
}
