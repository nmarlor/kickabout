package nmarlor.kickabout.pitch;

import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

public interface PitchFeatureDAO extends GenericDAO<PitchFeature, Long>
{
	List<PitchFeature> findPitchFeaturesByPitch(Pitch pitch);
}
