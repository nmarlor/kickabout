package nmarlor.kickabout.pitch;

import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

public interface FacilitiesDAO extends GenericDAO<Facilities, Long>
{
	public List<Facilities> findByLocation(PitchLocation pitchLocation);
}
