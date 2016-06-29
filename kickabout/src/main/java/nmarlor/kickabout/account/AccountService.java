package nmarlor.kickabout.account;

public interface AccountService 
{
	public Account retrieveAccount(Long accountId);
	
	public Account updateAccount(Account account);
	
	public void deleteAccount(Account account);
}
