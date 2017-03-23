package nmarlor.kickabout.booking;

import java.util.Comparator;

public class SortByBookingDateAndTime implements Comparator<Booking>{
	
	@Override
	public int compare(Booking booking1, Booking booking2) {
		int i = booking1.getDate().compareTo(booking2.getDate());
		if (i != 0) return i;
		
		i = booking1.getBookedFrom().compareTo(booking2.getBookedTo());
		if (i != 0) return i;
		
		return i;
	}

}
