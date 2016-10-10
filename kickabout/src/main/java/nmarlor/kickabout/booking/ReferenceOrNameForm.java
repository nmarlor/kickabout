package nmarlor.kickabout.booking;

import nmarlor.kickabout.account.Account;

public class ReferenceOrNameForm 
{
	private String search;
	private Account account;
	private String date;

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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
