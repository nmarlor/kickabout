package nmarlor.kickabout.company;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nmarlor.kickabout.account.Account;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService{
	
	@Autowired
	private CompanyDAO companyDAO;

	@Override
	public List<Company> getAccountsCompanies(Account account) {
		List<Company> accountsCompanies = companyDAO.findAccountsCompanies(account);
		return accountsCompanies;
	}

}
