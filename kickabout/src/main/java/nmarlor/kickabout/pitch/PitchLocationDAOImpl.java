package nmarlor.kickabout.pitch;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.company.Company;
import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class PitchLocationDAOImpl extends HibernateJPABase<PitchLocation, Long> implements PitchLocationDAO{

	@Override
	public List<PitchLocation> findAllLocationsByTownCityOrPostcode(String name) {
		Search search = new Search(PitchLocation.class);
		search.addFilterEqual("city", name);
		//TODO need to find a way of searching all parameters
//		search.addFilterEqual("county", name);
//		search.addFilterCustom("city", name, "county", name);
		return super.search(search);
	}

	@Override
	public List<PitchLocation> findAllLocationsByCompany(Company company) {
		Search search = new Search(PitchLocation.class);
		search.addFilterEqual("company", company);
		return super.search(search);
	}

}
