package nmarlor.kickabout.account;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import nmarlor.kickabout.company.Company;

@SuppressWarnings("serial")
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	public static final String FIND_BY_EMAIL = "Account.findByEmail";

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String name;
	
	@ManyToOne
	@JoinColumn(name="company_id", foreignKey=@ForeignKey(name="account_company_id_fk"))
	private Company company;

	@Column
	private String email;
	
	@JsonIgnore
	@Column
	private String password;

	private String role = "ROLE_USER";

    protected Account() {

	}
	
	public Account(String email, String password, String role) {
		this.email = email;
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
