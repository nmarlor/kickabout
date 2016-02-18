package nmarlor.kickabout.account;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import nmarlor.kickabout.pitch.PitchAvailability;

@Entity
@Table(name = "bookings")
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="account_id", foreignKey=@ForeignKey(name="booking_account_id_fk"))
	private Account account;
	
	@OneToOne
	@JoinColumn(name="pitch_availability_id", foreignKey=@ForeignKey(name="bookings_pitch_availability_id_fk"))
	private PitchAvailability pitchAvailability;
	
	@Column
	private Integer cost;

// TODO - Need to check if calendar is correct data type
//	@Column
//	private Date date;
	
	@Column
	private Calendar date;
	
	@Column
	private String name;

	public Long getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public PitchAvailability getPitchAvailability() {
		return pitchAvailability;
	}

	public void setPitchAvailability(PitchAvailability pitchAvailability) {
		this.pitchAvailability = pitchAvailability;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

//	public Date getDate() {
//		return date;
//	}
//
//	public void setDate(Date date) {
//		this.date = date;
//	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}
}
