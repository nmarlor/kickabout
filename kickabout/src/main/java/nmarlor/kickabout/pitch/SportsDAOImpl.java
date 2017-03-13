package nmarlor.kickabout.pitch;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class SportsDAOImpl extends HibernateJPABase<Sports, Long> implements SportsDAO {

	@Override
	public List<Sports> findSportsByPitch(Pitch pitch) {
		Search search = new Search(Sports.class);
		search.addFilterEqual("pitch", pitch);
		return super.search(search);
	}

	@Override
	public List<Sports> findSportsByName(String sport) {
		Search search = new Search(Sports.class);
		search.addFilterEqual("sport", sport);
		search.addFilterEqual("available", true);
		return super.search(search);
	}

	@Override
	public List<Sports> findAvailableSportsByPitch(Pitch pitch) {
		Search search = new Search(Sports.class);
		search.addFilterEqual("pitch", pitch);
		search.addFilterEqual("available", true);
		return super.search(search);
	}

	@Override
	public List<Sports> findSportsByNameAndPitch(String sport, Pitch pitch) {
		Search search = new Search(Sports.class);
		search.addFilterEqual("sport", sport);
		search.addFilterEqual("pitch", pitch);
		search.addFilterEqual("available", true);
		return super.search(search);
	}

}
