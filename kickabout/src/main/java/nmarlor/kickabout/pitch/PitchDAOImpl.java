package nmarlor.kickabout.pitch;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class PitchDAOImpl extends HibernateJPABase<Pitch, Long> implements PitchDAO
{
	@Override
	public List<Pitch> findPitchesByLocation(PitchLocation pitchLocation)
	{
		Search search = new Search(Pitch.class);
		search.addFilterEqual("pitchLocation", pitchLocation);
		return super.search(search);
	}

	@Override
	public Pitch findPitchByLocationAndPitchNumber(PitchLocation pitchLocation, Integer pitchNumber) {
		Search search = new Search(Pitch.class);
		search.addFilterEqual("pitchLocation", pitchLocation);
		search.addFilterEqual("pitchNumber", pitchNumber);
		return super.searchUnique(search);
	}
}
