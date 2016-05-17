package nmarlor.kickabout.booking;

import java.sql.Date;
import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.pitch.Pitch;

public interface BookingDAO extends GenericDAO<Booking, Long>
{
	List<Booking> findBookingsForAccount(Account account);
	
	List<Booking> findBookingsByPitchAndDate(Pitch pitch, Date date);
}
