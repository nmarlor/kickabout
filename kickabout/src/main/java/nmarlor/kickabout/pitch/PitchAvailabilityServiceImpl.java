package nmarlor.kickabout.pitch;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PitchAvailabilityServiceImpl implements PitchAvailabilityService{

	@Autowired
	PitchAvailabilityDAO pitchAvailabilityDAO;
	
	@Override
	public List<PitchAvailability> findPitchAvailabilityByPitchAndDate(Pitch pitch, Date date) {
		List<PitchAvailability> pitchAvailabilities = pitchAvailabilityDAO.findPitchAvailabilityByPitchAndDate(pitch, date);
		return pitchAvailabilities;
	}

	@Override
	public PitchAvailability retrievePitchAvailability(Long availabilityId) {
		return pitchAvailabilityDAO.find(availabilityId);
	}

}
