package nmarlor.kickabout.pitch;

import java.util.Calendar;

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

import nmarlor.kickabout.account.Booking;

@Entity
@Table(name="pitch_availability")
public class PitchAvailability {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="pitch_id", foreignKey=@ForeignKey(name="pitch_availability_pitch_id_fk"))
	private Pitch pitch;
	
	@OneToOne(mappedBy="pitchAvailability", cascade = CascadeType.ALL)
	private Booking	booking;

// TODO - Need to check if Calendar is correct Type
//	@Column(name = "booked_from")
//	private Date bookedFrom;

//	@Column(name = "booked_to")
//	private Date bookedTo;
	
	@Column(name = "booked_from")
	private Calendar bookedFrom;
	
	@Column(name = "booked_to")
	private Calendar bookedTo;

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

//	public Date getBookedFrom() {
//		return bookedFrom;
//	}
//
//	public void setBookedFrom(Date bookedFrom) {
//		this.bookedFrom = bookedFrom;
//	}

	public Calendar getBookedFrom() {
		return bookedFrom;
	}

	public void setBookedFrom(Calendar bookedFrom) {
		this.bookedFrom = bookedFrom;
	}

//	public Date getBookedTo() {
//		return bookedTo;
//	}
//
//	public void setBookedTo(Date bookedTo) {
//		this.bookedTo = bookedTo;
//	}

	public Calendar getBookedTo() {
		return bookedTo;
	}

	public void setBookedTo(Calendar bookedTo) {
		this.bookedTo = bookedTo;
	}
}
