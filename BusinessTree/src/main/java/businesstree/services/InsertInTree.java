package businesstree.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import businesstree.cache.EmployeeCache;
import businesstree.entity.Employee;
import businesstree.entity.repositories.EmployeeRepository;
import businesstree.tree.Tree;

@Service
@Transactional
public class InsertInTree {

	//@Autowired
	//private Tree tree;
	@Autowired
	private EmployeeCache cache;
	
	@Autowired
	private EmployeeRepository eRepository;
	
	public boolean insert(Employee employee, Long parentId){
		
		/* ********************************************
		 * insert new employee in the tree structure, 
		 * with parent employee identified by parentId, 
		 * if succeed return true else return false
		 **********************************************/
		
		boolean isAdded;
		
		try{
			Employee parent = this.eRepository.getOne(parentId);
			employee.setParent(parent);
			
			this.eRepository.saveAndFlush(employee);
			
			this.cache.cachingEmployee(employee.getId(), employee);
			this.cache.getEmployee(parentId).getChildren().add(employee);
			
			isAdded = true;
			
		}catch(Exception e){			
			isAdded = false;
			e.printStackTrace();
		}
		
		return isAdded;
	}
				
}
