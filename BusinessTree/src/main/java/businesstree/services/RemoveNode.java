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
public class RemoveNode {
	
	@Autowired
	private EmployeeCache cache;
	
	@Autowired
	private ShiftNodes shiftNodes;
	
	@Autowired
	private EmployeeRepository eRepository;
	
	public void remove(long empToDeleteId, long empToReplaceId){
			
		/* *****************************************************************************
		 * Remove employee with id empToDeleteId, if empToReplaceId is greater than 0, 
		 * the deleted employee will be replaced by employee with id empToReplaceId, 
		 * all of the children of the deleted employee, will be adopted by the employee
		 * to replace, which will retain the whole of its subtree.
		 * If empToReplaceId is equals to 0, then all of the children 
		 * of the deleted employee will be added to the tree's root children list
		 ******************************************************************************/
			
		Employee 
			empToDelete = this.eRepository.getOne(empToDeleteId),
			empToReplace;

		List<Employee> children = empToDelete.getChildren();
		
		if(empToReplaceId > 0){
			empToReplace = this.eRepository.getOne(empToReplaceId);
			empToReplace.setParent(empToDelete.getParent());
			
			/* *******************************************************************
			 * remove the employee to replace from the children list of its parent
			 * and change its parent with the parent of the employee to delete
			 * *******************************************************************/
			this.cache.getEmployee(empToReplaceId).getParent().getChildren().remove(this.cache.getEmployee(empToReplaceId));
			this.cache.getEmployee(empToReplaceId).setParent(this.cache.getEmployee(empToDeleteId).getParent());
		}else{
			empToReplace = this.eRepository.findTreeRoot();
		}
		
		if(children != null && children.size() > 0){
			this.shiftNodes.changeParent(children, empToReplace);
			this.eRepository.flush();
			
			/* ***********************************************
			 * change the parent to all of the children of the 
			 * deleted employee with the employee to replace
			 * ***********************************************/
			this.shiftNodes.changeParent(this.cache.getEmployee(empToDeleteId).getChildren(), this.cache.getEmployee(empToReplaceId));
			
			/* ********************************************
			 * add the children of the deleted employee to 
			 * the children list of the employee to replace
			 * ********************************************/
			this.cache.getEmployee(empToReplaceId).getChildren().addAll(this.cache.getEmployee(empToDeleteId).getChildren());
		}
		
		this.eRepository.deleteEmployee(empToDeleteId);
		this.cache.remmoveEmployee(empToDeleteId);
	}
}
