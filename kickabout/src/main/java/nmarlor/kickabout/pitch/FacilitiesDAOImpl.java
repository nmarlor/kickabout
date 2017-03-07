package nmarlor.kickabout.pitch;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class FacilitiesDAOImpl extends HibernateJPABase<Facilities, Long> implements FacilitiesDAO{

	@Override
	public List<Facilities> findByLocation(PitchLocation pitchLocation) {
		Search search = new Search(Facilities.class);
		search.addFilterEqual("pitchLocation", pitchLocation);
		search.addFilterEqual("available", true);
		return super.search(search);
	}

}
