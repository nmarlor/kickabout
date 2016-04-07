package nmarlor.kickabout.booking;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class BookingDAOImpl extends HibernateJPABase<Booking, Long> implements BookingDAO
{

	@Override
	public List<Booking> findBookingsForAccount(Account account) {
		Search search = new Search(Booking.class);
		search.addFilterEqual("account", account);
		return super.search(search);
	}

}
