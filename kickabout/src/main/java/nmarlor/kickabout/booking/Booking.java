package nmarlor.kickabout.booking;

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

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

import nmarlor.kickabout.account.Account;
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
	
	@Column(name = "booked_from")
	private Time bookedFrom;
	
	@Column(name = "booked_to")
	private Time bookedTo;
	
	@Column
	private BigDecimal cost;

	@Column(name = "date")
	private Date date;
	
	@Column
	private String name;
	
	@Column
	private String email;

	public Long getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	public Time getBookedFrom() {
		return bookedFrom;
	}

	public void setBookedFrom(Time bookedFrom) {
		this.bookedFrom = bookedFrom;
	}

	public Time getBookedTo() {
		return bookedTo;
	}

	public void setBookedTo(Time bookedTo) {
		this.bookedTo = bookedTo;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public PitchAvailability getPitchAvailability() {
		return pitchAvailability;
	}

	public void setPitchAvailability(PitchAvailability pitchAvailability) {
		this.pitchAvailability = pitchAvailability;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
