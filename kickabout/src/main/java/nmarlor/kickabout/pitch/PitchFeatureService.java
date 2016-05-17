package nmarlor.kickabout.pitch;

import java.util.List;

public interface PitchFeatureService 
{
	public List<PitchFeature> findPitchFeaturesByPitch(Pitch pitch);
	
	public PitchFeature update(PitchFeature pitchFeature);
	
	public PitchFeature retrieve(Long featureId);
	
	public void delete(PitchFeature pitchFeature);
	
	public void create(PitchFeature pitchFeature);
	
	public PitchFeature findPitchFeatureByPitchAndFeature(Pitch pitch, String feature);
}
