package nmarlor.kickabout.pitch;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy = false)
@Table(name="pitches",
		uniqueConstraints={@UniqueConstraint(columnNames={"pitch_location_id", "pitch_number"}, name="pitch_UK")})
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
	private String pitchSize;
	
	@Column
	private BigDecimal cost;
	
	@Column(name = "available_from")
	private Time availableFrom;
	
	@Column(name = "available_to")
	private Time availableTo;
	
	@Column(name = "image", columnDefinition = "LONGBLOB")
	private byte[] image;
	
	@Column(name = "surface")
	private String surface;
	
	@Column(name = "environment")
	private String environment;

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

	public String getPitchSize() {
		return pitchSize;
	}

	public void setPitchSize(String pitchSize) {
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

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getSurface() {
		return surface;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	
}
