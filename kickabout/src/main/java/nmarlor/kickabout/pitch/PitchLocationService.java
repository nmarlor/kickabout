package nmarlor.kickabout.pitch;

import java.util.List;

public interface PitchLocationService 
{
	public List<PitchLocation> findAll();
	
	public PitchLocation retrieve(Long locationId);
	
	public List<PitchLocation> findAllLocationsByTownCityOrPostcode(String name);
}
