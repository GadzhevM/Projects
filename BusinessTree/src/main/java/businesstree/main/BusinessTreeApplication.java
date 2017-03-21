package businesstree.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan(basePackages = {"businesstree.configuration", "businesstree.cache"})
public class BusinessTreeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusinessTreeApplication.class, args);
	}
}
