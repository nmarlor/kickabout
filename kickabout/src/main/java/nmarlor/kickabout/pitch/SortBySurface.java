package nmarlor.kickabout.pitch;

import java.util.Comparator;

public class SortBySurface implements Comparator<Pitch> {

	@Override
	public int compare(Pitch pitch1, Pitch pitch2) {
		return pitch1.getSurface().compareTo(pitch2.getSurface());
	}

}
