package nmarlor.kickabout.company;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.account.Account;
import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class CompanyDAOImpl extends HibernateJPABase<Company, Long> implements CompanyDAO{

	@Override
	public List<Company> findAccountsCompanies(Account account) {
		Search search = new Search(Company.class);
		search.addFilterEqual("account", account);
		return super.search(search);
	}

}
