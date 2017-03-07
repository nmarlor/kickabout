package nmarlor.kickabout.pitch;

import java.util.List;

public interface AvailabilityService 
{
	public void createAll(List<Availability> availabilities);
	
	public Availability findByLocationAndDay(PitchLocation pitchLocation, String dayOfWeek);
}
