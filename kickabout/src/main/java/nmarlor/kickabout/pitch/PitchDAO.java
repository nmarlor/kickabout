package nmarlor.kickabout.pitch;

import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

public interface PitchDAO extends GenericDAO<Pitch, Long>
{
	List<Pitch> findPitchesByLocation(PitchLocation pitchLocation);
	
	public Pitch findPitchByLocationAndPitchNumber(PitchLocation pitchLocation, Integer pitchNumber);
}
