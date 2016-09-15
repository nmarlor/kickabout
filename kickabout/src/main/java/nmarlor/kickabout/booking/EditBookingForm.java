package nmarlor.kickabout.booking;

public class EditBookingForm {

	private Long bookingId;
	private String name;
	private String date;
	private String bookedFrom;
	private String bookedTo;
	
	public Long getBookingId() {
		return bookingId;
	}
	public String getName() {
		return name;
	}
	public String getDate() {
		return date;
	}
	public String getBookedFrom() {
		return bookedFrom;
	}
	public String getBookedTo() {
		return bookedTo;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setBookedFrom(String bookedFrom) {
		this.bookedFrom = bookedFrom;
	}
	public void setBookedTo(String bookedTo) {
		this.bookedTo = bookedTo;
	}
	
}
