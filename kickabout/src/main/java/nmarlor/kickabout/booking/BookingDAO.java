package nmarlor.kickabout.booking;

import java.sql.Date;
import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.pitch.Pitch;
import nmarlor.kickabout.pitch.PitchLocation;

public interface BookingDAO extends GenericDAO<Booking, Long>
{
	List<Booking> findBookingsForAccount(Account account);
	
	List<Booking> findBookingsByPitchAndDate(Pitch pitch, Date date);
	
	List<Booking> findAllByDate(Date date);
	
	List<Booking> findByReferenceOrName(String toSearch);
	
	List<Booking> findForLocationByReferenceOrName(PitchLocation location, String toSearch);
}
