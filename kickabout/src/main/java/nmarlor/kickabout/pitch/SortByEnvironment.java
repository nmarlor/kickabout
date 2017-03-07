package nmarlor.kickabout.pitch;

import java.util.Comparator;

public class SortByEnvironment implements Comparator<Pitch>{

	@Override
	public int compare(Pitch pitch1, Pitch pitch2) {
		return pitch1.getEnvironment().compareTo(pitch2.getEnvironment());
	}

}
