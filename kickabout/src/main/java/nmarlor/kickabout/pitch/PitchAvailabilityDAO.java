package nmarlor.kickabout.pitch;

import java.sql.Date;
import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

public interface PitchAvailabilityDAO extends GenericDAO<PitchAvailability, Long> 
{
	List<PitchAvailability> findPitchAvailabilityByPitchAndDate(Pitch pitch, Date date);
}
