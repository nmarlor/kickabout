package nmarlor.kickabout.booking;

import java.sql.Date;
import java.sql.Time;

public class BookVenueForm 
{
	private String searchedName;
	private String searchedSport;
	
	private Long locationId;
	private String date;
	private String time;
	private String endTime;
	private String duration;
	private String sport;
	private String environment;
	private String surface;
	
	private Long pitchId;
	private Date bookingDate;
	
	private Time formattedTime;
	private Time formattedEndTime;
	
	private String name;
	private String email;
	
	public Long getLocationId() {
		return locationId;
	}
	public String getDate() {
		return date;
	}
	public String getTime() {
		return time;
	}
	public String getDuration() {
		return duration;
	}
	public String getSport() {
		return sport;
	}
	public String getEnvironment() {
		return environment;
	}
	public String getSurface() {
		return surface;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public void setSurface(String surface) {
		this.surface = surface;
	}
	public String getSearchedName() {
		return searchedName;
	}
	public String getSearchedSport() {
		return searchedSport;
	}
	public void setSearchedName(String searchedName) {
		this.searchedName = searchedName;
	}
	public void setSearchedSport(String searchedSport) {
		this.searchedSport = searchedSport;
	}
	public Long getPitchId() {
		return pitchId;
	}
	public void setPitchId(Long pitchId) {
		this.pitchId = pitchId;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
	public Time getFormattedTime() {
		return formattedTime;
	}
	public Time getFormattedEndTime() {
		return formattedEndTime;
	}
	public void setFormattedTime(Time formattedTime) {
		this.formattedTime = formattedTime;
	}
	public void setFormattedEndTime(Time formattedEndTime) {
		this.formattedEndTime = formattedEndTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
