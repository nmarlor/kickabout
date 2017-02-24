package nmarlor.kickabout.pitch;

import java.util.Set;

public class LocationForm 
{
	private String name;
	private Long locationId;
	private Set<Long> locationIds;
	private Long accountId;
	private String sport;
	
	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Long> getLocationIds() {
		return locationIds;
	}

	public void setLocationIds(Set<Long> locationIds) {
		this.locationIds = locationIds;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}
	
}