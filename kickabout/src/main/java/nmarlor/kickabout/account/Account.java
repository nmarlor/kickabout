package nmarlor.kickabout.account;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Proxy(lazy = false)
@Table(name = "account",
		uniqueConstraints={@UniqueConstraint(columnNames={"email"}, name="email_UK")})
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
	
	@Column
	private String telephone;

	private String role = "ROLE_USER";

    protected Account() {

	}
	
	public Account(String email, String name, String password, String telephone, String role) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.telephone = telephone;
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
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
