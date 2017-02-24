package nmarlor.kickabout.pitch;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SportsServiceImpl implements SportsService{

	@Autowired
	private SportsDAO sportsDAO;
	
	@Override
	public void createSport(Sports sport) {
		sportsDAO.persist(sport);
	}

	@Override
	public List<Sports> findSportsByPitch(Pitch pitch) {
		List<Sports> pitchSports = sportsDAO.findSportsByPitch(pitch);
		return pitchSports;
	}

	@Override
	public Sports retrieveSport(Long sportId) {
		Sports sport = sportsDAO.find(sportId);
		return sport;
	}

	@Override
	public Sports update(Sports sport) {
		return sportsDAO.save(sport);
	}

	@Override
	public List<Sports> findSportsByName(String sport) {
		return sportsDAO.findSportsByName(sport);
	}

}
