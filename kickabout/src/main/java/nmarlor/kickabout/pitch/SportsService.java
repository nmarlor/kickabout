package nmarlor.kickabout.pitch;

import java.util.List;

public interface SportsService 
{
	public void createSport(Sports sport);
	
	public List<Sports> findSportsByPitch(Pitch pitch);
	
	public Sports retrieveSport(Long sportId);
	
	public Sports update(Sports sport);
	
	public List<Sports> findSportsByName(String sport);
	
	public List<Sports> findAvailableSportsByPitch(Pitch pitch);
}
