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
@Table(name = "sports")
public class Sports {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="pitch_id", foreignKey=@ForeignKey(name="sport_pitch_id_fk"))
	private Pitch pitch;
	
	@Column(name="sport")
	private String sport;
	
	@Column(name="available")
	private Boolean available;

	public Long getId() {
		return id;
	}

	public Pitch getPitch() {
		return pitch;
	}

	public String getSport() {
		return sport;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}
	
}
