package nmarlor.kickabout.pitch;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

public interface AvailabilityDAO extends GenericDAO<Availability, Long>{
	public Availability findByLocationAndDay(PitchLocation location, String day);
}
