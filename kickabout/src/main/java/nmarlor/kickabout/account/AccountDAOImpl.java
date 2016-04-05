package nmarlor.kickabout.account;

import org.springframework.stereotype.Repository;

import nmarlor.kickabout.lib.HibernateJPABase;

@Repository
public class AccountDAOImpl extends HibernateJPABase<Account, Long> implements AccountDAO{

}
