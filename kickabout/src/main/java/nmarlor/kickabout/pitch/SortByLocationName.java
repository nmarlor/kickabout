package nmarlor.kickabout.pitch;

import java.util.Comparator;

public class SortByLocationName implements Comparator<PitchLocation> {

	@Override
	public int compare(PitchLocation location1, PitchLocation location2) {
		return location1.getCompany().compareTo(location2.getCompany());
	}

}
