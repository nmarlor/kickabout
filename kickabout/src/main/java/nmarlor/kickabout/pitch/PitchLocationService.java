package nmarlor.kickabout.pitch;

import java.util.List;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.company.Company;

public interface PitchLocationService 
{
	public List<PitchLocation> findAll();
	
	public PitchLocation retrieve(Long locationId);
	
	public List<PitchLocation> findAllLocationsByTownOrCity(String name);
	
	public List<PitchLocation> findAllLocationsByCounty(String name);
	
	public List<PitchLocation> findPitchLocationsByCompany(Company company);
	
	public List<PitchLocation> findPitchLocationsForAccount(Account account);
	
	public void createPitchLocation(PitchLocation pitchLocation);
	
	public PitchLocation findByName(String name);
}
