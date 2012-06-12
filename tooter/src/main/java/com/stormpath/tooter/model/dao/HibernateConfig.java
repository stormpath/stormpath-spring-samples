package com.stormpath.tooter.model.dao;

import com.stormpath.tooter.model.Customer;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

import javax.sql.DataSource;

@Configuration
public class HibernateConfig {


    @Bean
    public SessionFactory sessionFactory() {
        return new LocalSessionFactoryBuilder(datasource())
                .addAnnotatedClasses(Customer.class)
                .buildSessionFactory();
    }

    @Bean
    public DataSource datasource() {
        EmbeddedDatabaseFactoryBean bean = new EmbeddedDatabaseFactoryBean();
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("schema.sql"));
        bean.setDatabasePopulator(databasePopulator);
        bean.afterPropertiesSet(); // necessary because EmbeddedDatabaseFactoryBean is a FactoryBean
        return bean.getObject();
    }


}