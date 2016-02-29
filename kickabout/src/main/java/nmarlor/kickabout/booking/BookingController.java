package nmarlor.kickabout.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingController {
	
	@Autowired
	BookingService bookingService;
}
