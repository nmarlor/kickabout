package nmarlor.kickabout.company;

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

import org.hibernate.annotations.Proxy;

import nmarlor.kickabout.account.Account;

@Entity
@Proxy(lazy=false)
@Table(name="companies")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="account_id", foreignKey=@ForeignKey(name="company_account_id_fk"))
	private Account account;
	
	@Column
	private String name;
	
	@OneToOne
	@JoinColumn(name="company_details_id", foreignKey=@ForeignKey(name="company_company_details_id_fk"))
	private CompanyDetails companyDetails;
	
	public Long getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CompanyDetails getCompanyDetails() {
		return companyDetails;
	}

	public void setCompanyDetails(CompanyDetails companyDetails) {
		this.companyDetails = companyDetails;
	}
}
