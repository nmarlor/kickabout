package nmarlor.kickabout.pitch;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nmarlor.kickabout.account.Account;
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
	public List<PitchLocation> findAllLocationsByTownOrCity(String name) {
		return pitchLocationDAO.findAllLocationsByTownOrCity(name);
	}
	
	@Override
	public List<PitchLocation> findAllLocationsByCounty(String name) {
		return pitchLocationDAO.findAllLocationsByCounty(name);
	}

	@Override
	public List<PitchLocation> findPitchLocationsByCompany(Company company) {
		List<PitchLocation> locationsForCompany = pitchLocationDAO.findAllLocationsByCompany(company);
		return locationsForCompany;
	}

	@Override
	public List<PitchLocation> findPitchLocationsForAccount(Account account) {
		List<PitchLocation> locationsForAccount = pitchLocationDAO.findAllLocationsByAccount(account);
		return locationsForAccount;
	}

	@Override
	public void createPitchLocation(PitchLocation pitchLocation) {
		pitchLocationDAO.persist(pitchLocation);
	}

	@Override
	public PitchLocation findByName(String name) {
		return pitchLocationDAO.findByName(name);
	}

}
