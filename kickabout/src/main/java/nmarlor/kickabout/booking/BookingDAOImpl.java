package nmarlor.kickabout.booking;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.lib.HibernateJPABase;
import nmarlor.kickabout.pitch.Pitch;
import nmarlor.kickabout.pitch.PitchLocation;

@Repository
public class BookingDAOImpl extends HibernateJPABase<Booking, Long> implements BookingDAO
{

	@Override
	public List<Booking> findBookingsForAccount(Account account) {
		Search search = new Search(Booking.class);
		search.addFilterEqual("account", account);
		return super.search(search);
	}

	@Override
	public List<Booking> findBookingsByPitchAndDate(Pitch pitch, Date date) {
		Search search = new Search(Booking.class);
		search.addFilterEqual("pitch", pitch);
		search.addFilterEqual("date", date);
		return super.search(search);
	}

	@Override
	public List<Booking> findAllByDate(Date date) {
		Search search = new Search(Booking.class);
		search.addFilterEqual("date", date);
		return super.search(search);
	}

	@Override
	public List<Booking> findByReferenceOrName(String toSearch) {
		Search referenceSearch = new Search(Booking.class);
		referenceSearch.addFilterEqual("bookingReference", toSearch);
		
		List<Booking> bookings = super.search(referenceSearch);
		
		if (bookings.isEmpty()) 
		{
			Search nameSearch = new Search(Booking.class);
			nameSearch.addFilterEqual("name", toSearch);
			List<Booking> bookingsForName = super.search(nameSearch);
			return bookingsForName;
		}
		
		return bookings;
	}

	@Override
	public List<Booking> findForLocationByReferenceOrName(PitchLocation location, String toSearch) {
		Search referenceSearch = new Search(Booking.class);
		referenceSearch.addFilterEqual("pitch.pitchLocation", location);
		referenceSearch.addFilterEqual("bookingReference", toSearch);
		
		List<Booking> bookings = super.search(referenceSearch);
		
		if (bookings.isEmpty()) 
		{
			Search nameSearch = new Search(Booking.class);
			nameSearch.addFilterEqual("pitch.pitchLocation", location);
			nameSearch.addFilterEqual("name", toSearch);
			List<Booking> bookingsForName = super.search(nameSearch);
			return bookingsForName;
		}
		
		return bookings;
	}

}
