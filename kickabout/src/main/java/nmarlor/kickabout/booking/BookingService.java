package nmarlor.kickabout.booking;

import java.util.List;

import nmarlor.kickabout.account.Account;

public interface BookingService 
{
	public void createBooking(Booking booking);
	
	public List<Booking> findBookingsForAccount(Account account);
	
	public Booking retrieve(Long bookingId);
	
	public void deleteBooking(Booking booking);
}
