package nmarlor.kickabout.booking;

import java.sql.Date;

import nmarlor.kickabout.account.Account;

public class ReferenceOrNameForm 
{
	private String search;
	private Account account;
	private Date date;

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
