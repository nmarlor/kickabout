package nmarlor.kickabout.pitch;

import java.util.List;

public interface PitchesService 
{
	public List<Pitch> findPitchesByLocation(PitchLocation location);
	
	public Pitch retrievePitch(Long pitchId);

	public void createPitch(Pitch pitch);
	
	public void deletePitch(Pitch pitch);
	
	public Pitch updatePitch(Pitch pitch);
	
	public Pitch findPitchByLocationAndNumber(PitchLocation location, Integer pitchNumber);
}
