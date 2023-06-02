package by.itacademy.spring;


import by.itacademy.spring.database.properties.DatabaseProperties;
import by.itacademy.spring.database.repository.CompanyRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.core.SpringProperties;


@SpringBootApplication
@ConfigurationPropertiesScan
public class ApplicationRunner {
    public static void main(String[] args) {
        var context = SpringApplication.run(ApplicationRunner.class, args);
        var company = context.getBean("companyRepository", CompanyRepository.class);
        System.out.println(context.getBean(DatabaseProperties.class));
    }
}
