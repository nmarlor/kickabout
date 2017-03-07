package nmarlor.kickabout.pitch;

import java.util.List;

public interface FacilitiesService 
{
	public List<Facilities> findFacilitiesForLocation(PitchLocation pitchLocation);
}
