package nmarlor.kickabout.pitch;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.CascadeType;
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
import javax.persistence.UniqueConstraint;

import nmarlor.kickabout.booking.Booking;

@Entity
@Table(name="pitch_availability",
		uniqueConstraints={@UniqueConstraint(columnNames={"pitch_id", "date", "booked_from", "booked_to"}, name="pitch_availability_UK")})
public class PitchAvailability {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="pitch_id", foreignKey=@ForeignKey(name="pitch_availability_pitch_id_fk"))
	private Pitch pitch;
	
	@OneToOne(mappedBy="pitchAvailability", cascade = CascadeType.ALL)
	private Booking	booking;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "booked_from")
	private Time bookedFrom;
	
	@Column(name = "booked_to")
	private Time bookedTo;

	public Long getId() {
		return id;
	}
	
	public Pitch getPitch() {
		return pitch;
	}

	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

}
