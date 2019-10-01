package org.fastgrow.teadms.server;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.fastgrow.teadms.server.repository.impl.GenericRepositoryImpl;
import org.hsqldb.jdbc.JDBCDriver;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.support.SharedEntityManagerBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
		basePackages = "org.fastgrow.teadms.server.repository",
		repositoryBaseClass = GenericRepositoryImpl.class)
@PropertySource(value = {"classpath:default-config.properties" })
@ComponentScan("org.fastgrow.teadms")
@EnableTransactionManagement
public class ApplicationConfiguration {
	
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public DataSource dataSource(@Value("${hsqldb.file}") String fileName) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(JDBCDriver.class.getName());
        dataSource.setJdbcUrl("jdbc:hsqldb:file:" + fileName);
        dataSource.setUser("SA");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public FactoryBean<EntityManagerFactory> entityManagerFactory(DataSource dataSource) {
    	HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    	vendorAdapter.setDatabase(Database.HSQL);
    	vendorAdapter.setGenerateDdl(true);
    	
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setDataSource(dataSource);
        emf.setPersistenceUnitName("PU");
        emf.setPackagesToScan("org.fastgrow.teadms.server.domain");

        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public FactoryBean<EntityManager> entityManager(EntityManagerFactory emf) {
        SharedEntityManagerBean em = new SharedEntityManagerBean();
        em.setEntityManagerFactory(emf);
        return em;
    }
}
