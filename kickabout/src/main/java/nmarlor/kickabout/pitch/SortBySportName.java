package nmarlor.kickabout.pitch;

import java.util.Comparator;

public class SortBySportName implements Comparator<Sports>{

	@Override
	public int compare(Sports sport1, Sports sport2) {
		return sport1.getSport().compareTo(sport2.getSport());
	}

}
