package nmarlor.kickabout.pitch;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class PitchAvailabilityDAOImpl extends HibernateJPABase<PitchAvailability, Long> implements PitchAvailabilityDAO
{
	@Override
	public List<PitchAvailability> findPitchAvailabilityByPitchAndDate(Pitch pitch, Date date) {
		Search search = new Search(PitchAvailability.class);
		search.addFilterEqual("pitch", pitch);
		search.addFilterEqual("date", date);
		return super.search(search);
	}

}
