package tn.nat.cnss.reconstitutioncarriere.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import com.zaxxer.hikari.HikariDataSource;



@Configuration
@ComponentScan(basePackages={"tn.nat.cnss.reconstitutioncarriere.service","tn.nat.cnss.reconstitutioncarriere.repository","tn.nat.cnss.reconstitutioncarriere.security"})
@PropertySource("classpath:tn/nat/cnss/reconstitutioncarriere/properties/jdbc.properties")
@PropertySource("classpath:tn/nat/cnss/reconstitutioncarriere/properties/application.properties")
@EnableTransactionManagement
@EnableJpaRepositories({"tn.nat.cnss.reconstitutioncarriere.repository","tn.nat.cnss.reconstitutioncarriere.security"})
public class AppConfig {
	
	@Autowired
	Environment env;
	
	@Bean
	public DataSource dataSource() {
		
		/*
		// Simple DataSource
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();	 
	    dataSource.setDriverClassName(env.getProperty("db_driver"));
	    dataSource.setUsername(env.getProperty("db_user"));
	    dataSource.setPassword(env.getProperty("db_pwd"));
	    dataSource.setUrl(env.getProperty("db_url"));
	    */
	    
		
		/*
		// HikariCp pool
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(env.getProperty("db_driver"));
		dataSource.setUsername(env.getProperty("db_user"));
	    dataSource.setPassword(env.getProperty("db_pwd"));
	    dataSource.setJdbcUrl(env.getProperty("db_url"));	    
	    //TODO other Hikari Pool Configurations Here ...
	    return dataSource;
	    */
		
		PoolProperties p = new PoolProperties();
        p.setUrl(env.getProperty("db_url"));
        p.setDriverClassName(env.getProperty("db_driver"));
        p.setUsername(env.getProperty("db_user"));
        p.setPassword(env.getProperty("db_pwd"));
        p.setJmxEnabled(true);
        p.setTestWhileIdle(false);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1 from dual");
        p.setTestOnReturn(false);
        p.setValidationInterval(30000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(100);
        p.setInitialSize(10);
        p.setMaxWait(10000);
        p.setRemoveAbandonedTimeout(60);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(false);
        p.setJdbcInterceptors(
          "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
          "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        DataSource datasource = new org.apache.tomcat.jdbc.pool.DataSource(p);
        
        return datasource;
		 
	    
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "tn.nat.cnss.reconstitutioncarriere.model","tn.nat.cnss.reconstitutioncarriere.security" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	Properties additionalProperties() {
		Properties properties = new Properties();
		//properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		//properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
		//properties.setProperty("hibernate.show_sql", "true");
		//properties.setProperty("hibernate.format_sql", "true");
		//properties.setProperty("hibernate.use_sql_comments", "true");
		return properties;
	}
	

}
