package nmarlor.kickabout.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;

public class DefaultTestDataSourceConfig implements DataSourceConfig {

	@Value("${dataSource.driverClassName}")
    private String driver;
    @Value("${dataSource.url}")
    private String url;
    @Value("${dataSource.username}")
    private String username;
    @Value("${dataSource.password}")
    private String password;
    @Value("${hibernate.dialect}")
    private String dialect;
    
    @Bean(name="dataSource")
    @Override
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    
	@Bean(name="dbUnitDatabaseConfig")
	public DatabaseConfigBean dbUnitConfig()
	{
		DatabaseConfigBean databaseConfigBean = new DatabaseConfigBean();
		return databaseConfigBean;
	}
	@Bean(name="dbUnitDatabaseConnection")
	public DatabaseDataSourceConnectionFactoryBean dbUnitConnectionFactory()
	{
		DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection = new DatabaseDataSourceConnectionFactoryBean();
		dbUnitDatabaseConnection.setDataSource(dataSource());
		dbUnitDatabaseConnection.setDatabaseConfig(dbUnitConfig());
		return dbUnitDatabaseConnection;
	}
}
