package nmarlor.kickabout.pitch;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AvailabilityServiceImpl implements AvailabilityService
{
	@Autowired
	private AvailabilityDAO availabilityDAO;
	
	@Override
	public void createAll(List<Availability> availabilities) 
	{
		for (Availability availability : availabilities) 
		{
			availabilityDAO.persist(availability);
		}
	}

}
