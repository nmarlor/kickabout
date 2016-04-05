package nmarlor.kickabout.account;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nmarlor.kickabout.booking.Booking;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
@NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email")
public class Account implements java.io.Serializable {

	public static final String FIND_BY_EMAIL = "Account.findByEmail";

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String name;

	@Column
	private String email;
	
	@JsonIgnore
	@Column
	private String password;
	
	@OneToOne(mappedBy="account", cascade = CascadeType.ALL)
	private Wallet wallet;
	
	@OneToMany(mappedBy="account", orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	private Set<Booking> bookings = new HashSet<Booking>();

	private String role = "ROLE_USER";

    protected Account() {

	}
	
	public Account(String email, String name, String password, String role) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.role = role;
	}

	public Long getId() {
		return id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		wallet.setAccount(this);
		this.wallet = wallet;
	}

	public Set<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(Set<Booking> bookings) {
		this.bookings = bookings;
	}
}
