package nmarlor.kickabout.pitch;

import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.company.Company;

public interface PitchLocationDAO extends GenericDAO<PitchLocation, Long>
{
	public List<PitchLocation> findAllLocationsByTownCityOrPostcode (String name);
	
	public List<PitchLocation> findAllLocationsByCompany(Company company);
	
	public List<PitchLocation> findAllLocationsByAccount(Account account);
}
