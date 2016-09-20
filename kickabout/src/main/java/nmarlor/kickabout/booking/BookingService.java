package nmarlor.kickabout.booking;

import java.sql.Date;
import java.util.List;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.pitch.Pitch;

public interface BookingService 
{
	public void createBooking(Booking booking);
	
	public List<Booking> findBookingsForAccount(Account account);
	
	public Booking retrieve(Long bookingId);
	
	public void delete(Booking booking);
	
	public List<Booking> findBookingsByPitchAndDate(Pitch pitch, Date date);
	
	public void update(Booking booking);
	
	public List<Booking> findAllByDate(Date date);
}
