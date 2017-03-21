package businesstree.entity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import businesstree.entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	/* *******************************************************************
	 * select all records from database where value of location 
	 * property of the domain class match the method argument, 
	 * ignore case and order the returned records by employee name
	 * 
	 * the parsed properties in the method name (Location, EmployeeName)
	 * must match the properties name in the domain class (uncapitalized)
	 * (location, employeeName)
	 * ******************************************************************/
	
	//List<Employee> findByLocationIgnoreCaseOrderByEmployeeNameAsc(String location);

	@Query("select e from #{#entityName} e where e.parent = null")
	Employee findTreeRoot();
	
	@Modifying
	@Query("delete from #{#entityName} e where e.id = ?1")
	void deleteEmployee(Long id);
}
