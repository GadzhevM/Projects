package businesstree.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import businesstree.cache.EmployeeCache;
import businesstree.dto.AddEmployee;
import businesstree.dto.EmployeeDetails;
import businesstree.dto.EmployeeInfo;
import businesstree.dto.EmployeeNames;
import businesstree.dto.EmployeeSubtree;
import businesstree.entity.Employee;
import businesstree.lib.EmployeeToEmployeeNames;
import businesstree.tree.Tree;

@RestController
@CrossOrigin(origins="http://localhost:4200")
public class BusinessTreeController {
	
	@Autowired
	private Tree tree;
	
	@Autowired
	private EmployeeCache cache;
	
	@Autowired 
	private EmployeeToEmployeeNames empToEmpNames;
	
	
	@RequestMapping(value = "/searchEmployee", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeInfo> getEmploye(@RequestParam(value = "employee", required=false)String employeeName){
		
		/* ***********************************************************************
		 *	search employee by name, return list of all employees which first name
		 *  or last name match fully or partially the string in the request  
		 * ***********************************************************************/
		
		List<EmployeeInfo> resoultList = null;
		
		if(employeeName != null){
			
			employeeName = employeeName.toUpperCase();
			resoultList = new ArrayList<>();
				
			for(Employee employee : this.cache.getAll()){
				
				if(employee.getFirstName().toUpperCase().startsWith(employeeName) || 
							employee.getLastName().toUpperCase().startsWith(employeeName)){
					
					resoultList.add(new EmployeeInfo(employee.getId(), employee.getFirstName(), 
							employee.getLastName(), employee.getLocation(), 
							employee.getSalary(), employee.getAddition()));
				}
			}
		}
		return resoultList;
	}
	
	@RequestMapping(value = "/searchEmployeeById", method = RequestMethod.GET)
	public @ResponseBody EmployeeDetails getEmployeById(@RequestParam(value = "id", required=false)String employeeToFind){
		
		EmployeeDetails employeeDetails = null;
		
		if(employeeToFind != null){
			
			long employeeId = Long.parseLong(employeeToFind);
				
			for(Employee employee : this.cache.getAll()){
				
				if(employee.getId() == employeeId){
					EmployeeNames parentInfo;
					
					/* *******************************************************************
					 * check if the employee has a parent (the tree's root has no parent),
					 * if has, create object of type EmployeeNames 
					 * *******************************************************************/
					if(employee.getParent() != null){
						Employee parent = employee.getParent();
						parentInfo = this.empToEmpNames.getEmployeeNames(parent);
					}else{
						parentInfo = null;
					}
					
					double totalSalary = employee.getSalary();
					
					for(Employee child: employee.getChildren()){
						totalSalary += child.getSalary() * child.getAddition()/100;			// adding addition from child to employee salary to compute the total salasy 
					}
					
					/* *********************************************************
					 * create object of type EmployeeDetails, which is going
					 * to be returned and has whole of the employee information,
					 * but children list and its subtree
					 * *********************************************************/
					employeeDetails = new EmployeeDetails(employee.getId(), employee.getFirstName(), employee.getLastName(),
							parentInfo, employee.getLocation(), employee.getSalary(), employee.getAddition(), totalSalary);
				}
			}
		}

		return employeeDetails;
	}
	
	@RequestMapping(value = "/getChildren", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeNames> getChildren(@RequestParam(value = "id", required=true)long employeeId){
		return this.tree.getChidlren(employeeId);
	}
	
	
	@RequestMapping(value = "/getAll")
	public @ResponseBody List<EmployeeNames> getEmployees(){
		
		List<EmployeeNames> resoultList = new ArrayList<>();

		for(Employee employee : this.cache.getAll()){
			
			if(employee.getParent() == null){
				
				/* set the tree's root as first element of the resulted list */
				
				resoultList.add(0, new EmployeeNames(employee.getId(), employee.getFirstName(), 
							employee.getLastName()));
			}else{
				resoultList.add(new EmployeeNames(employee.getId(), employee.getFirstName(), 
							employee.getLastName()));
			}
		}
		
		return resoultList;
	}
	
	@RequestMapping(value="/getEmployeeSubordinates", method = RequestMethod.GET)
	public @ResponseBody List<EmployeeSubtree> getSubordinates(@RequestParam(value = "employee_id", required = true) String stringOfID){
		
		/* ***********************************************************************
		 * the method return multidimensional List, if method parameter getTree 
		 * has value false, then the list contains only one sublist, which is 
		 * an list of the employee "children" and it's the first element 
		 * of the returned multidimensional list, else if method parameter getTree
		 * has value true, the returned multidimensional list contains all of the 
		 * employee's subordinates ant it's represent tree like form structure
		 * ***********************************************************************/
		
		return this.tree.getSubTree(Long.parseLong(stringOfID));
	}
	
	@RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
	public @ResponseBody String addEmployee(@RequestBody AddEmployee employeeToAdd){
		
		/* ************************************
		 * adding new employee in the structure
		 * ************************************/
		
		String msg = null;
		
		Employee employee = new Employee(employeeToAdd.getFirstName(), employeeToAdd.getLastName(), 
										employeeToAdd.getLocation(), employeeToAdd.getSalary());
		
		if(employeeToAdd.getAddition()!= 0) {employee.setAddition(employeeToAdd.getAddition());}

		if(tree.insert(employee, employeeToAdd.getParentId())){
			msg = "New employee is added!";
		}else{
			msg = "Somethings goes wrong, can't add this employe!";
		}
		
		return msg;
	}
}
