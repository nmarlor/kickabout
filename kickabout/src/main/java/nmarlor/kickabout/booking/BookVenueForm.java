package nmarlor.kickabout.booking;

public class BookVenueForm 
{
	private String searchedName;
	private String searchedSport;
	
	private Long locationId;
	private String date;
	private String time;
	private String duration;
	private String sport;
	private String environment;
	private String surface;
	
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
	
}
