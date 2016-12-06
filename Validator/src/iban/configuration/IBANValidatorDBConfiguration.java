package iban.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import iban.jdbc.IBANJDBCTemplate;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages="iban.controller")
public class IBANValidatorDBConfiguration {
	
	@Bean(name="dataSource")
	public DriverManagerDataSource getDataSource(){
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/Banks");
		dataSource.setUsername("postgres");
		dataSource.setPassword("root");
		
		return dataSource;
	}
	
	@Bean(name="ibanTemplate")
	public IBANJDBCTemplate ibanTemplate(){
		IBANJDBCTemplate template = new IBANJDBCTemplate();
		return template;
	}
}
