package nmarlor.kickabout.pitch;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SportsChoiceServiceImpl implements SportsChoiceService{

	@Autowired
	private SportsChoiceDAO sportsChoiceDAO;
	
	@Override
	public List<SportsChoice> findAll() {
		return sportsChoiceDAO.findAll();
	}

}
