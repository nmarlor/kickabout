package nmarlor.kickabout.account;

import java.util.List;

import com.googlecode.genericdao.dao.jpa.GenericDAO;

public interface AccountDAO extends GenericDAO<Account, Long>
{
	List<Account> findAllAdminAccounts(String adminRole);
}
