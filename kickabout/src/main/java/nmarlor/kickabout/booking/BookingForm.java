package nmarlor.kickabout.booking;

import java.math.BigDecimal;
import java.sql.Time;

public class BookingForm {

	private Long pitchId;
	private BigDecimal cost;
	private Time bookedFrom;
	private Time bookedTo;
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
	public Time getBookedFrom() {
		return bookedFrom;
	}
	public void setBookedFrom(Time bookedFrom) {
		this.bookedFrom = bookedFrom;
	}
	public Time getBookedTo() {
		return bookedTo;
	}
	public void setBookedTo(Time bookedTo) {
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
