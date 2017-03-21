package businesstree.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import businesstree.cache.EmployeeCache;
import businesstree.entity.Employee;
import businesstree.entity.repositories.EmployeeRepository;
import businesstree.tree.Tree;

@Service
@Transactional
public class ShiftNodes {
	
	@Autowired
	//private Tree tree;
	private EmployeeCache cache;
	
	@Autowired
	private EmployeeRepository eRepository;
	
	public boolean attachTo(long employeeId, long newParentId){
		
		/* ********************************************************
		 * move node with id = nodeID to new position, 
		 * attaching it to another exist node with id = newParentID 
		 **********************************************************/
		
		try{
			Employee 
				newParent = this.eRepository.getOne(newParentId),
				employee = this.eRepository.getOne(employeeId);
		
			employee.setParent(newParent);
			this.eRepository.flush();
			
			employee = this.cache.getEmployee(employeeId);
			newParent = this.cache.getEmployee(newParentId);
			
			// remove this employee from his parent list of children 
			employee.getParent().getChildren().remove(employee);
			
			// set new parent of this employee
			
			employee.setParent(newParent);
			
			// add this employee to his new parent list of children
			newParent.getChildren().add(employee);
				
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void exchangeSubtrees(long employeeId1, long employeeId2){
		
		/* **********************************************************
		 * exchange position of two subtrees rooted by nodes
		 * with id employeeId1 and employeeId2, if non of these nodes
		 * do not contain the other in its subtree
		 ************************************************************/
		
		if(!checkForIndependence(this.cache.getEmployee(employeeId1), this.cache.getEmployee(employeeId2))
				&& !checkForIndependence(this.cache.getEmployee(employeeId2), this.cache.getEmployee(employeeId1))){
			
			/* **************************************************************************************************
			 * use Employee objects in the cache for independence check, using these from the persistence context
			 * will cause load of the whole subtrees just for checking and then eligible them for GC
			 * **************************************************************************************************/
			
			Employee
				emp1 = this.eRepository.getOne(employeeId1),
				emp2 = this.eRepository.getOne(employeeId2),
				parent = emp1.getParent();
			
			this.cache.getEmployee(employeeId1).getParent().getChildren().remove(emp1);	// List's remove method uses equals method to find the object to be removed
			emp1.setParent(emp2.getParent());
			this.cache.getEmployee(employeeId2).getParent().getChildren().remove(emp2);
			emp2.setParent(parent);
			
			this.eRepository.flush();
			
			// update the cache
			
			parent = this.cache.getEmployee(employeeId1).getParent();
			emp1 = this.cache.getEmployee(employeeId1);
			emp2 = this.cache.getEmployee(employeeId2);
			
			emp1.setParent(emp2.getParent());
			emp2.setParent(parent);
			emp1.getParent().getChildren().add(emp1);
			parent.getChildren().add(emp2);
		}		
	}
	
	public void exchangeNodes(long emp1Id, long emp2Id){
		
		/* **********************************
		 * exchange position of two employees
		 * whit id emp1Id and emp2Id 
		 ************************************/
		
		Employee
			emp1 = this.eRepository.getOne(emp1Id),
			emp2 = this.eRepository.getOne(emp2Id);
		
		if(emp1.getParent().equals(emp2)){
			
			/* ***********************************
			 * check for parent-child relationship 
			 * ***********************************/
			
			Employee tempEmployee = emp1;
			emp1 = emp2;
			emp2 = tempEmployee;
		}
		
		List<Employee> children = emp1.getChildren();
		changeParent(emp2.getChildren(), emp1);
		
		Employee parent = emp1.getParent();
		
		if(emp1.equals(emp2.getParent())) emp1.setParent(emp2);
		else emp1.setParent(emp2.getParent());
		
		emp2.setParent(parent);
		
		this.eRepository.save(emp1);
		this.eRepository.save(emp2);
		
		changeParent(children, emp2);
		
		this.eRepository.flush();
		
		// update the cache
		
		emp1 = this.cache.getEmployee(emp1Id);
		emp2 = this.cache.getEmployee(emp2Id);
		parent = parent.getParent();
		
		parent.getChildren().remove(emp1);
		emp1.setParent(emp2.getParent());
		emp1.getParent().getChildren().add(emp1);
		emp2.getParent().getChildren().remove(emp2);
		emp2.setParent(parent);
		parent.getChildren().add(emp2);
	}
	
	public void changeParent(List<Employee> children, Employee newParent){
		for(Employee child : children){
			child.setParent(newParent);
		}
	}
	
	private boolean checkForIndependence(Employee emp1, Employee emp2){
		
		/* ***********************************************
		 * check both nodes for independence of each other  
		 *************************************************/
		
		boolean inSubtree = false;
		
		if(emp1.getChildren().size() > 0){
			for(Employee child : emp1.getChildren()){
				if(!child.equals(emp2)){
					inSubtree = checkForIndependence(child, emp2);
				}else{
					inSubtree = true;
				}
				if(inSubtree){				//if match a child with id = method parameter id,
					break;					//stop searching and return true through all recursive calls
				}
			}
		}

		return inSubtree;
	}
}

