package nmarlor.kickabout.pitch;

import java.io.Serializable;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Table(name="availability")
public class Availability implements Serializable{

	private static final long serialVersionUID = 3077455968749250497L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name="pitch_location_id", foreignKey=@ForeignKey(name="availability_pitch_location_id_fk"))
	private PitchLocation pitchLocation;
	
	@Column(name = "day")
	private String day;
	
	@Column(name = "available_from")
	private Time availableFrom;
	
	@Column(name = "available_to")
	private Time availableTo;

	public Long getId() {
		return id;
	}

	public PitchLocation getPitchLocation() {
		return pitchLocation;
	}

	public String getDay() {
		return day;
	}

	public Time getAvailableFrom() {
		return availableFrom;
	}

	public Time getAvailableTo() {
		return availableTo;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPitchLocation(PitchLocation pitchLocation) {
		this.pitchLocation = pitchLocation;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setAvailableFrom(Time availableFrom) {
		this.availableFrom = availableFrom;
	}

	public void setAvailableTo(Time availableTo) {
		this.availableTo = availableTo;
	}
	
}
