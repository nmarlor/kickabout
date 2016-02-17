package nmarlor.kickabout.pitch;

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
public class Pitch {

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
}
