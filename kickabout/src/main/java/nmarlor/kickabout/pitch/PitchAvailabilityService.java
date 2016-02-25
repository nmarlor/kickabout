package nmarlor.kickabout.pitch;

import java.util.Date;
import java.util.List;

public interface PitchAvailabilityService 
{
	public List<PitchAvailability> findPitchAvailabilityByPitchAndDate(Pitch pitch, Date date);
	
	public PitchAvailability retrievePitchAvailability(Long availabilityId);
}
