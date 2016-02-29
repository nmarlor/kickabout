package nmarlor.kickabout.booking;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class BookingServiceImpl implements BookingService{

	@Autowired
	private BookingDAO bookingDAO;
}
