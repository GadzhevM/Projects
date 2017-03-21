package businesstree.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import businesstree.cache.EmployeeCache;
import businesstree.dto.EmployeeNames;
import businesstree.dto.EmployeeSubtree;
import businesstree.entity.Employee;
import businesstree.lib.EmployeeToEmployeeNames;
import businesstree.tree.Tree;

@Service
@Transactional
public class CreateTree {

	private List<EmployeeSubtree> treeOfEmps;	
	
	@Autowired
	private EmployeeCache cache;
	
	@Autowired
	private EmployeeToEmployeeNames empToEmpNames;
	
	public List<EmployeeSubtree> getTree(long employeeId){
		
		/* ***********************************************
		 * return multidimensional ArrayList in 
		 * tree like form containing all elements 
		 * in subtree rooted by node with id = employeeId
		 * if employeeId = 0, the returned ArrayList will
		 * contain all elements
		 *************************************************/
		Employee root;
		
		if(employeeId > 0){
			root = this.cache.getEmployee(employeeId);
		}else{
			root = getRoot();
		}
		
		if(root.getChildren() != null && root.getChildren().size() > 0){
			
			treeOfEmps = new ArrayList<>();
			
			for(Employee child: root.getChildren()){
				EmployeeSubtree employee = new EmployeeSubtree();
				employee.setNames(this.empToEmpNames.getEmployeeNames(child));
				
				goThroughRootChildren(employee);
				
				this.treeOfEmps.add(employee);
			}
		}
		
		return this.treeOfEmps;		
	}
	
	private void goThroughRootChildren(EmployeeSubtree employee){
		
		//this.treeOfEmps = new ArrayList<EmployeeSubtree>();
				//this.treeOfEmps.add(new ArrayList<EmployeeNames>());
				
				//EmployeeNames treeRoot = this.empToEmpNames.getEmployeeNames(root);
				//this.treeOfEmps.get(0).add(treeRoot);
				
				//List<Employee> children = this.tree.getNodes().get(employee.getNames().getId()).getChildren();
				List<Employee> children = this.cache.getEmployee(employee.getNames().getId()).getChildren();
				
				if(children != null && children.size() > 0){
					
					employee.setChildren(new ArrayList<>());
					
					for(Employee child: children){
						EmployeeNames childNames = this.empToEmpNames.getEmployeeNames(child);
						EmployeeSubtree childSubtree = new EmployeeSubtree();
						childSubtree.setNames(childNames);
						employee.getChildren().add(childSubtree);
						
						goThroughRootChildren(childSubtree);				
					}
				}
	}
	
	/*
	private void createTree(Employee employee, int layer){

		List<Employee> employeeChildren = employee.getChildren();	
		
		if(employeeChildren.size() > 0){		//if node has children list
	
			if(layer > treeOfEmps.size()-1){
				treeOfEmps.add(new ArrayList<EmployeeNames>());
			}	
			
			for(Employee emp : employeeChildren){
				this.treeOfEmps.get(layer).add(this.empToEmpNames.getEmployeeNames(emp));
				
				createTree(emp, layer+1);
			}		
		}
	}
	*/
	
	public List<EmployeeNames> getChildren(long id){
		Employee parent = this.cache.getEmployee(id);		
		
		List<EmployeeNames> children = new ArrayList<>();
		
		for(Employee child : parent.getChildren()){
			children.add(this.empToEmpNames.getEmployeeNames(child));
		}
		
		return children;
	}
	
	
	public Employee getRoot(){
		
		Employee root = null;
		
		for(Long id : this.cache.getKyes()){
			Employee emp = this.cache.getEmployee(id);
			if(emp.getParent() == null){
				root =  emp;
				break;
			}
		}
		
		return root;
	}
}
