package io.spoon.radip.config;

import io.spoon.radip.domain.user.User;
import io.spoon.radip.repository.UserRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@EntityScan(basePackageClasses = {
        User.class
})
@EnableJpaRepositories(basePackageClasses = {
        UserRepository.class
})
public class DatabaseConfig {

}
