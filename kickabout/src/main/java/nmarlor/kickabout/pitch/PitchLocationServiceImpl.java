package nmarlor.kickabout.pitch;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nmarlor.kickabout.company.Company;

@Service
@Transactional
public class PitchLocationServiceImpl implements PitchLocationService {
	
	@Autowired
	private PitchLocationDAO pitchLocationDAO;

	@Override
	public List<PitchLocation> findAll() {
		return pitchLocationDAO.findAll();
	}

	@Override
	public PitchLocation retrieve(Long locationId) {
		return pitchLocationDAO.find(locationId);
	}

	@Override
	public List<PitchLocation> findAllLocationsByTownCityOrPostcode(String name) {
		return pitchLocationDAO.findAllLocationsByTownCityOrPostcode(name);
	}

	@Override
	public List<PitchLocation> findPitchLocationsByCompany(Company company) {
		List<PitchLocation> locationsForCompany = pitchLocationDAO.findAllLocationsByCompany(company);
		return locationsForCompany;
	}

}
