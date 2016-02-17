package nmarlor.kickabout.config;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestApplicationConfig.class, DefaultTestDataSourceConfig.class, JpaConfig.class, SecurityConfig.class})
public abstract class DAOTestsConfigurationAware {
}
