package nmarlor.kickabout.account;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.search.Search;

import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class AccountDAOImpl extends HibernateJPABase<Account, Long> implements AccountDAO{

	@Override
	public List<Account> findAllAdminAccounts(String adminRole) {
		Search search = new Search(Account.class);
		search.addFilterEqual("role", adminRole);
		return super.search(search);
	}

}
