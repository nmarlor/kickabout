package nmarlor.kickabout.pitch;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.company.Company;
import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class PitchLocationDAOImpl extends HibernateJPABase<PitchLocation, Long> implements PitchLocationDAO{

	@Override
	public List<PitchLocation> findAllLocationsByTownOrCity(String name) {
		Search search = new Search(PitchLocation.class);
		search.addFilterEqual("city", name);
		return super.search(search);
	}
	
	@Override
	public List<PitchLocation> findAllLocationsByCounty(String name) {
		Search search = new Search(PitchLocation.class);
		search.addFilterEqual("county", name);
		return super.search(search);
	}

	@Override
	public List<PitchLocation> findAllLocationsByCompany(Company company) {
		Search search = new Search(PitchLocation.class);
		search.addFilterEqual("company", company);
		return super.search(search);
	}

	@Override
	public List<PitchLocation> findAllLocationsByAccount(Account account) {
		Search search = new Search(PitchLocation.class);
		search.addFilterEqual("account", account);
		return super.search(search);
	}

}
