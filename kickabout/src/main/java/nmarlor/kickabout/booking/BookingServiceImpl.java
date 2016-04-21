package nmarlor.kickabout.booking;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.pitch.Pitch;

@Service
@Transactional
public class BookingServiceImpl implements BookingService{

	@Autowired
	private BookingDAO bookingDAO;

	@Override
	public void createBooking(Booking booking) {
		bookingDAO.persist(booking);
	}

	@Override
	public List<Booking> findBookingsForAccount(Account account) {
		List<Booking> bookingsForAccount = bookingDAO.findBookingsForAccount(account);
		return bookingsForAccount;
	}

	@Override
	public Booking retrieve(Long bookingId) {
		return bookingDAO.find(bookingId);
	}

	@Override
	public void delete(Booking booking) {
		bookingDAO.remove(booking);
	}

	@Override
	public List<Booking> findBookingsByPitchAndDate(Pitch pitch, Date date) {
		List<Booking> bookings = bookingDAO.findBookingsByPitchAndDate(pitch, date);
		return bookings;
	}
}
