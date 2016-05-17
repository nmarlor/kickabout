package nmarlor.kickabout.pitch;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PitchesServiceImpl implements PitchesService{
	
	@Autowired
	private PitchDAO pitchDAO;

	@Override
	public List<Pitch> findPitchesByLocation(PitchLocation location) {
		List<Pitch> pitches = pitchDAO.findPitchesByLocation(location);
		return pitches;
	}

	@Override
	public Pitch retrievePitch(Long pitchId) {
		return pitchDAO.find(pitchId);
	}

	@Override
	public void createPitch(Pitch pitch) {
		pitchDAO.persist(pitch);
	}

	@Override
	public void deletePitch(Pitch pitch) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pitch updatePitch(Pitch pitch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pitch findPitchByLocationAndNumber(PitchLocation location, Integer pitchNumber) {
		Pitch pitch = pitchDAO.findPitchByLocationAndPitchNumber(location, pitchNumber);
		return pitch;
	}

}
