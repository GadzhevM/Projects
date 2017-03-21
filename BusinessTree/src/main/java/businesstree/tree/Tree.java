package businesstree.tree;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import businesstree.dto.EmployeeNames;
import businesstree.dto.EmployeeSubtree;
import businesstree.entity.Employee;
import businesstree.services.*;

public class Tree {
	
	@Autowired
	private InsertInTree inTree;
	
	@Autowired
	private RemoveNode removeNode;
	
	@Autowired
	private ShiftNodes shiftNodes;
	
	@Autowired
	private CreateTree createTree;
	
	@Autowired
	private EditEmployee editEmployee;	
	
	
	public boolean insert(Employee employee, long parentId){
		
		/* ********************************************
		 * insert new employee in the tree structure, 
		 * with parent employee identified by parentId, 
		 * if succeed return true else return false
		 **********************************************/	
		return this.inTree.insert(employee, parentId);
	} 
	
	public boolean changeFristName(long employeeId, String newFirstName){
		
		/* ******************************************************
		 * change the first name of employee with id = employeeId
		 * if succeed return true otherwise return false
		 ********************************************************/	
		return this.editEmployee.changeFirstName(employeeId, newFirstName);
	}
	
	public boolean changeLastName(long employeeId, String newLastName){
		
		/* *****************************************************
		 * change the last name of employee with id = employeeId
		 * if succeed return true otherwise return false
		 *******************************************************/	
		return this.editEmployee.changeLastName(employeeId, newLastName);
	}
	
	public boolean changeLocation(long employeeId, String newLocation){
		
		/* ****************************************************
		 * change the location of employee with id = employeeId
		 * if succeed return true otherwise return false
		 ******************************************************/	
		return this.editEmployee.changeLocation(employeeId, newLocation);
	}
	
	public boolean changeSalary(long employeeId, double newSalary){
		
		/* **************************************************
		 * change the salary of employee with id = employeeId
		 * if succeed return true otherwise return false
		 ****************************************************/
		return this.editEmployee.changeSalary(employeeId, newSalary);
	}
	
	public boolean changeAddition(long employeeId, double newAddition){
		
		/* ****************************************************
		 * change the addition of employee with id = employeeId
		 * if succeed return true otherwise return false
		 ******************************************************/
		return this.editEmployee.changeAddition(employeeId, newAddition);
	}
	
	public boolean attachTo(long employeeId, long newParentId){
		
		/* ***************************************
		 * move existing node with id = employeeId 
		 * attach it to node with id = newParentId
		 *****************************************/
		return shiftNodes.attachTo(employeeId, newParentId);
	}
	
	public void exchangeEmployeesPosition(long empId1, long empId2){
		
		/* ******************************** ****************
		 * exchange parents of node with id = empId1 and 
		 * node with id = empId2 and their children id lists
		 ***************************************************/
		shiftNodes.exchangeNodes(empId1, empId2);
	} 
	
	public void exchangeSubtreesPosition(long empId1, long empId2){
		
		/* *******************************************************************
		 * exchange parents of node with id = empId1 and node with id = empId2
		 *********************************************************************/		
		shiftNodes.exchangeSubtrees(empId1, empId2);
	} 
	
	public void remove(long empToDeleteId, long empToReplaceId){
		
		/* *******************************************************
		 * empToReplaceId is the id of the node which parent 
		 * will become parent of the node with id = empToDeleteId, 
		 * and will inherit the children of the deleted node too
		 * *******************************************************/
		removeNode.remove(empToDeleteId, empToReplaceId);
	}
	
	public void remove(long employeeId){
		
		/* *****************************************
		 * children of the node with id = employeeId 
		 * will become children of the tree's root
		 * *****************************************/
		removeNode.remove(employeeId, 0);
	}
	
	public List<EmployeeSubtree> getTree(){
		return createTree.getTree(0L);
	}
	
	public List<EmployeeSubtree> getSubTree(long employeeId){
		return createTree.getTree(employeeId);
	}
	
	public List<EmployeeNames> getChidlren(long id){
		return this.createTree.getChildren(id);
	}
	
	public Employee getRootId(){
		return createTree.getRoot();
	}
}
