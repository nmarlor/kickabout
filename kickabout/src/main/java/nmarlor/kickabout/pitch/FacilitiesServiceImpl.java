package nmarlor.kickabout.pitch;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FacilitiesServiceImpl implements FacilitiesService{
	
	@Autowired
	private FacilitiesDAO facilitiesDAO;

	@Override
	public List<Facilities> findFacilitiesForLocation(PitchLocation pitchLocation) {
		return facilitiesDAO.findByLocation(pitchLocation);
	}

}
