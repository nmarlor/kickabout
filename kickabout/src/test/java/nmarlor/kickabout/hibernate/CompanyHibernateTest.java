package nmarlor.kickabout.hibernate;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import nmarlor.kickabout.company.Company;
import nmarlor.kickabout.lib.HibernateDAOTestBase;

public class CompanyHibernateTest extends HibernateDAOTestBase{
	
	private static final Long DBUNIT_COMPANY_ID = 1L;
	private static final String NAME = "Example company";

	@Before
	public void setup() {
	}
	
	@Test
	@DatabaseSetup(value = "classpath:dbunit/companies.xml")
	public void retrieveTest()
	{
		Company company = hibernateDAO.retrieve(Company.class, DBUNIT_COMPANY_ID);
		assertNotNull(company);
		
		assertEquals(NAME, company.getName());
	}
}
