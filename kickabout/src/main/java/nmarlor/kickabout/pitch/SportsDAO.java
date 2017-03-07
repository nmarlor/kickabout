package nmarlor.kickabout.pitch;

import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

public interface SportsDAO extends GenericDAO<Sports, Long>
{
	List<Sports> findSportsByPitch(Pitch pitch);
	
	List<Sports> findSportsByName(String sport);
	
	List<Sports> findAvailableSportsByPitch(Pitch pitch);
}
