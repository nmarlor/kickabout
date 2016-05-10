package nmarlor.kickabout.booking;

import java.math.BigDecimal;

public class BookingForm {

	private Long pitchId;
	private BigDecimal cost;
	private String bookedFrom;
	private String bookedTo;
	private String email;
	private String name;
	private String date;
	
	public Long getPitchId() {
		return pitchId;
	}
	public void setPitchId(Long pitchId) {
		this.pitchId = pitchId;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getBookedFrom() {
		return bookedFrom;
	}
	public String getBookedTo() {
		return bookedTo;
	}
	public void setBookedFrom(String bookedFrom) {
		this.bookedFrom = bookedFrom;
	}
	public void setBookedTo(String bookedTo) {
		this.bookedTo = bookedTo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}
