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

}
