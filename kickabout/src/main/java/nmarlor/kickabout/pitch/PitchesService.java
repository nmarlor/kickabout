package nmarlor.kickabout.pitch;

import java.util.List;

public interface PitchesService 
{
	public List<Pitch> findPitchesByLocationId(Long locationId);
	
	public Pitch retrievePitch(Long pitchId);

	public void createPitch();
	
	public void deletePitch(Pitch pitch);
	
	public Pitch updatePitch(Pitch pitch);
}
