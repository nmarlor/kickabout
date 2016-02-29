package nmarlor.kickabout.booking;

import org.springframework.stereotype.Repository;

import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class BookingDAOImpl extends HibernateJPABase<Booking, Long> implements BookingDAO
{

}
