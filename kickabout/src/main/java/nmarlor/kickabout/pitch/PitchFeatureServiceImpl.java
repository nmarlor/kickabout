package nmarlor.kickabout.pitch;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PitchFeatureServiceImpl implements PitchFeatureService{

	@Autowired
	private PitchFeatureDAO pitchFeatureDAO;
	
	@Override
	public List<PitchFeature> findPitchFeaturesByPitch(Pitch pitch) {
		List<PitchFeature> pitchFeatures = pitchFeatureDAO.findPitchFeaturesByPitch(pitch);
		return pitchFeatures;
	}

	@Override
	public PitchFeature update(PitchFeature pitchFeature) {
		return pitchFeatureDAO.save(pitchFeature);
	}

	@Override
	public PitchFeature retrieve(Long featureId) {
		return pitchFeatureDAO.find(featureId);
	}

	@Override
	public void delete(PitchFeature pitchFeature) {
		pitchFeatureDAO.remove(pitchFeature);
	}

	@Override
	public void create(PitchFeature pitchFeature) {
		pitchFeatureDAO.persist(pitchFeature);
	}

	@Override
	public PitchFeature findPitchFeatureByPitchAndFeature(Pitch pitch, String feature) {
		PitchFeature pitchFeature = pitchFeatureDAO.findPitchFeatureByPitchAndFeature(pitch, feature);
		return pitchFeature;
	}

}
