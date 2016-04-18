package nmarlor.kickabout.pitch;

import java.util.List;

public interface PitchFeatureService 
{
	public List<PitchFeature> findPitchFeaturesByPitch(Pitch pitch);
	
	public PitchFeature update(PitchFeature pitchFeature);
	
	public PitchFeature retrieve(Long featureId);
}
