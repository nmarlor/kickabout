package nmarlor.kickabout.pitch;

import org.springframework.stereotype.Repository;

import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class AvailabilityDAOImpl extends HibernateJPABase<Availability, Long> implements AvailabilityDAO{

}
