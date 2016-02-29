package nmarlor.kickabout.pitch;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="pitches")
public class Pitch implements Serializable{

	private static final long serialVersionUID = -6011741843198883825L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="pitch_location_id", foreignKey=@ForeignKey(name="pitch_pitch_location_id_fk"))
	private PitchLocation pitchLocation;
	
	@Column(name="pitch_number")
	private Integer pitchNumber;
	
	@Column(name = "pitch_size")
	@Enumerated(EnumType.STRING)
	private PitchSize pitchSize;
	
	@Column
	private BigDecimal cost;
	
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

	public void setPitchLocation(PitchLocation pitchLocation) {
		this.pitchLocation = pitchLocation;
	}

	public Integer getPitchNumber() {
		return pitchNumber;
	}

	public void setPitchNumber(Integer pitchNumber) {
		this.pitchNumber = pitchNumber;
	}

	public PitchSize getPitchSize() {
		return pitchSize;
	}

	public void setPitchSize(PitchSize pitchSize) {
		this.pitchSize = pitchSize;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public Time getAvailableFrom() {
		return availableFrom;
	}

	public void setAvailableFrom(Time availableFrom) {
		this.availableFrom = availableFrom;
	}

	public Time getAvailableTo() {
		return availableTo;
	}

	public void setAvailableTo(Time availableTo) {
		this.availableTo = availableTo;
	}
	
}
