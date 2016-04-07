package nmarlor.kickabout.booking;

import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

import nmarlor.kickabout.account.Account;

public interface BookingDAO extends GenericDAO<Booking, Long>
{
	List<Booking> findBookingsForAccount(Account account);
}
