package nmarlor.kickabout.pitch;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class AvailabilityDAOImpl extends HibernateJPABase<Availability, Long> implements AvailabilityDAO{

	@Override
	public Availability findByLocationAndDay(PitchLocation location, String day) {
		Search search = new Search();
		search.addFilterEqual("day", day);
		search.addFilterEqual("pitchLocation", location);
		return super.searchUnique(search);
	}

}
