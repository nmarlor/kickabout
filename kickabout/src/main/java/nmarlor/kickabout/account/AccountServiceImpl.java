package nmarlor.kickabout.account;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AccountServiceImpl implements AccountService{

	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public Account retrieveAccount(Long accountId) {
		return accountDAO.find(accountId);
	}

	@Override
	public Account updateAccount(Account account) {
		return accountDAO.save(account);
	}

	@Override
	public void deleteAccount(Account account) {
		accountDAO.remove(account);
		accountDAO.findAll();
	}

	@Override
	public List<Account> findAll() {
		return accountDAO.findAll();
	}

	@Override
	public List<Account> findAllAdmins(String adminRole) {
		List<Account> adminAccounts = accountDAO.findAllAdminAccounts(adminRole);
		return adminAccounts;
	}

}
