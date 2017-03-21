package businesstree.lib;

import businesstree.dto.EmployeeNames;
import businesstree.entity.Employee;

public class EmployeeToEmployeeNames {
	
	public EmployeeNames getEmployeeNames(Employee employee){
		return new EmployeeNames(employee.getId(), employee.getFirstName(), employee.getLastName());
	}
}
