package nmarlor.kickabout.booking;

import java.math.BigDecimal;
import java.sql.Date;
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

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.pitch.Pitch;

@Entity
@Proxy(lazy = false)
@Table(name = "bookings",
	uniqueConstraints={@UniqueConstraint(columnNames={"pitch_id", "date", "booked_from", "booked_to"}, name="booking_pitch_UK")})
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="account_id", foreignKey=@ForeignKey(name="booking_account_id_fk"))
	private Account account;
	
	@ManyToOne
	@JoinColumn(name="pitch_id", foreignKey=@ForeignKey(name="booking_pitch_id_fk"))
	private Pitch pitch;
	
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

	public Pitch getPitch() {
		return pitch;
	}

	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
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
