package ra.projectmd4.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
    // 1. Cấu hình datasource chứa các thông tin kết nối database
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test_project?createDatabaseIfNotExist=true");
        dataSource.setUsername("root");
        dataSource.setPassword("MySQLPass30rd");
        return dataSource;
    }

    //2. Cấu hình Hibernate Properties
    public Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update"); //tự động thực hiện các câu lệnh DDl nếu cần thiết
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect"); //thay cho driver class name --> tìm tới phiên bản tương thích (hiện tại là mySQL8)
        properties.setProperty("hibernate.show_sql", "true"); // hiển thị các câu lệnh DML mà ORM sinh ra, hiển thij cấc câu SQL lên
        properties.setProperty("hibernate.format_sql", "true"); // định dạng SQL cho dễ đọc
        return properties;
    }


    //3. Cấu hình Entity Manager Factory, nhà máy quản lý các entity
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan("ra.projectmd4.model.entity");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactory.setJpaProperties(additionalProperties());
        return entityManagerFactory;
    }

    //chứa các thao tác tương tác với bên thứ 3 ở trên
    @Bean
    @Qualifier(value = "entityManager")
    public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
        return entityManagerFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
