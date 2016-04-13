package nmarlor.kickabout.pitch;

import java.math.BigDecimal;
import java.sql.Time;

public class NewPitchForm {

	private long locationId;
	private Integer pitchNumber;
	private Time availableFrom;
	private Time availableTo;
	private BigDecimal cost;
	private String pitchSize;
	
	public long getLocationId() {
		return locationId;
	}
	public Integer getPitchNumber() {
		return pitchNumber;
	}
	public Time getAvailableFrom() {
		return availableFrom;
	}
	public Time getAvailableTo() {
		return availableTo;
	}
	public BigDecimal getCost() {
		return cost;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	public void setPitchNumber(Integer pitchNumber) {
		this.pitchNumber = pitchNumber;
	}
	public void setAvailableFrom(Time availableFrom) {
		this.availableFrom = availableFrom;
	}
	public void setAvailableTo(Time availableTo) {
		this.availableTo = availableTo;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public String getPitchSize() {
		return pitchSize;
	}
	public void setPitchSize(String pitchSize) {
		this.pitchSize = pitchSize;
	}
	
}
