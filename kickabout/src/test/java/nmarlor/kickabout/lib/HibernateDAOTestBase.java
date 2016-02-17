package nmarlor.kickabout.lib;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;

import nmarlor.kickabout.config.DAOTestsConfigurationAware;

@SuppressWarnings("deprecation")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class })
@TransactionConfiguration(defaultRollback = true)
public class HibernateDAOTestBase extends DAOTestsConfigurationAware{
	
	private static final String TEST_USER_NAME = "testUser";

	@Autowired
	protected CRUDHibernateDAO hibernateDAO;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * The hibernate audit intercepter sets the created by and last update by to the currently 
	 * logged in user. So must set a test user to be logged in, ie set on the security context.
	 */
	@Before
	public void setupSecurityUser() {
		// create a test user and set on the security context
		Set<SimpleGrantedAuthority> auths = new HashSet<SimpleGrantedAuthority>();
		auths.add(new SimpleGrantedAuthority("test_auth"));
		Authentication authToken = new UsernamePasswordAuthenticationToken(TEST_USER_NAME,null, auths);
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}
	
	/**
	 * Clear the security context after each test.
	 */
	@After
	public void teardown() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	public void flushAndClear() {
		entityManager.flush();
		entityManager.clear();
	}
}
