package nmarlor.kickabout.pitch;

import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

public interface PitchLocationDAO extends GenericDAO<PitchLocation, Long>
{
	public List<PitchLocation> findAllLocationsByTownCityOrPostcode (String name);
}
