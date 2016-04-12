package nmarlor.kickabout.pitch;

import java.util.List;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.company.Company;

public interface PitchLocationService 
{
	public List<PitchLocation> findAll();
	
	public PitchLocation retrieve(Long locationId);
	
	public List<PitchLocation> findAllLocationsByTownCityOrPostcode(String name);
	
	public List<PitchLocation> findPitchLocationsByCompany(Company company);
	
	public List<PitchLocation> findPitchLocationsForAccount(Account account);
}
