package businesstree.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import businesstree.cache.EmployeeCache;
import businesstree.entity.Employee;
import businesstree.entity.repositories.EmployeeRepository;
import businesstree.lib.EmployeeToEmployeeNames;
import businesstree.tree.Tree;

@Configuration
@EnableWebMvc
@EnableCaching
@EnableJpaRepositories(basePackages = "businesstree.entity.repositories")
@ComponentScan(basePackages={"businesstree.controller", "businesstree.services"})
@EntityScan(basePackages = "businesstree.entity")
public class TreeConfig {
	
	@Autowired
	private EmployeeRepository eRepository;
	
	@Autowired
	private EmployeeCache cache;
	
	@Bean(name="tree")
	@Transactional
	public Tree getTree(){
		
		Tree tree = new Tree();
		Iterable<Employee> allEmployees = this.eRepository.findAll();
		
		for(Employee emp : allEmployees){
			System.out.println(emp.getChildren());
			cache.cachingEmployee(emp.getId(), emp);	
		}
		
		return tree;
	}
	
	
	@Bean
	public EmployeeToEmployeeNames getEmployeeNames(){
		return new EmployeeToEmployeeNames();
	}
}
