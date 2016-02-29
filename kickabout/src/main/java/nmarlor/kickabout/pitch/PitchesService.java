package nmarlor.kickabout.pitch;

import java.util.List;

public interface PitchesService 
{
	public List<Pitch> findPitchesByLocation(PitchLocation location);
	
	public Pitch retrievePitch(Long pitchId);

	public void createPitch();
	
	public void deletePitch(Pitch pitch);
	
	public Pitch updatePitch(Pitch pitch);
}
