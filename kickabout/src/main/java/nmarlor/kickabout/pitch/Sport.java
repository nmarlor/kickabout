package nmarlor.kickabout.pitch;

public class Sport 
{
	private Long id;
	private String sport;
	private Boolean available;
	
	public Long getId() {
		return id;
	}
	public String getSport() {
		return sport;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setSport(String sport) {
		this.sport = sport;
	}
	public Boolean getAvailable() {
		return available;
	}
	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
}
