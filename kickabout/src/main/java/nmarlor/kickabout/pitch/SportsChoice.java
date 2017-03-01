package nmarlor.kickabout.pitch;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sports_choice")
public class SportsChoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="sport")
	private String sport;

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
}
