package nmarlor.kickabout.pitch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "facilities")
public class Facilities {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="pitch_location_id", foreignKey=@ForeignKey(name="facilities_pitch_location_id_fk"))
	private PitchLocation pitchLocation;
	
	@Column(name="facility")
	private String facility;
	
	@Column(name="available")
	private Boolean available;

	public Long getId() {
		return id;
	}

	public PitchLocation getPitchLocation() {
		return pitchLocation;
	}

	public String getFacility() {
		return facility;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPitchLocation(PitchLocation pitchLocation) {
		this.pitchLocation = pitchLocation;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
}
