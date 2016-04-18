package nmarlor.kickabout.pitch;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class PitchFeatureDAOImpl extends HibernateJPABase<PitchFeature, Long> implements PitchFeatureDAO
{
	@Override
	public List<PitchFeature> findPitchFeaturesByPitch(Pitch pitch) {
		Search search = new Search(PitchFeature.class);
		search.addFilterEqual("pitch", pitch);
		return super.search(search);
	}

	@Override
	public PitchFeature findPitchFeatureByPitchAndFeature(Pitch pitch, String feature) {
		Search search = new Search(PitchFeature.class);
		search.addFilterEqual("pitch", pitch);
		search.addFilterEqual("feature", feature);
		return super.searchUnique(search);
	}
}
