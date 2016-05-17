package nmarlor.kickabout.company;

import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

import nmarlor.kickabout.account.Account;

public interface CompanyDAO extends GenericDAO<Company, Long>
{
	List<Company> findAccountsCompanies(Account account);
}
