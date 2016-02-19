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
	
	@Autowired
	private PitchLocationDAO pitchLocationDAO;

	@Override
	public List<Pitch> findPitchesByLocationId(Long locationId) {
		PitchLocation location = pitchLocationDAO.find(locationId);
		List<Pitch> pitches = pitchDAO.findPitchesByLocation(location);
		return pitches;
	}

	@Override
	public Pitch retrievePitch(Long pitchId) {
		return pitchDAO.find(pitchId);
	}

	@Override
	public void createPitch() {
		// TODO Auto-generated method stub
		
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

}
