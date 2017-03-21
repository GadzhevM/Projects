package businesstree.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import businesstree.cache.EmployeeCache;
import businesstree.entity.Employee;
import businesstree.entity.repositories.EmployeeRepository;

@Service
@Transactional
public class EditEmployee {
	
	@Autowired
	private EmployeeCache cache;
	
	@Autowired
	private EmployeeRepository eRepository;
	
	public boolean changeSalary(Long employeeId, double newSalary){
		boolean isChanged;
		
		try{
			Employee employee = this.eRepository.getOne(employeeId);
			employee.setSalary(newSalary);
			
			this.cache.getEmployee(employeeId).setSalary(newSalary);
			
			isChanged = true;
			
		}catch(Exception e){
			e.printStackTrace();
			isChanged = false; 
		}
		
		return isChanged;
	}
	
	public boolean changeAddition(long employeeId, double addition){
		boolean isChanged;
		
		try{
			Employee employee = this.eRepository.getOne(employeeId);
			employee.setAddition(addition);
			
			this.cache.getEmployee(employeeId).setAddition(addition);
			
			isChanged = true;
			
		}catch(Exception e){
			e.printStackTrace();
			isChanged = false;
		}
		
		return isChanged;
	}
	
	public boolean changeLocation(long employeeId, String newLocation){
		boolean isChanged;
		
		try{
			Employee employee = this.eRepository.getOne(employeeId);
			employee.setLocation(newLocation);
			
			this.cache.getEmployee(employeeId).setLocation(newLocation);
			
			isChanged = true;
			
		}catch(Exception e){
			e.printStackTrace();
			isChanged = false;
		}
		
		return isChanged;
	}
	
	public boolean changeFirstName(long employeeId, String newFirstName){
		boolean isChanged;
		
		try{
			Employee employee = this.eRepository.getOne(employeeId);
			employee.setFirstName(newFirstName);
			
			this.cache.getEmployee(employeeId).setFirstName(newFirstName);
			
			isChanged = true;
			
		}catch(Exception e){
			e.printStackTrace();
			isChanged = false;
		}
		
		return isChanged;
	}
	
	public boolean changeLastName(long employeeId, String newLastName){
		boolean isChanged;
		
		try{
			Employee employee = this.eRepository.getOne(employeeId);
			employee.setLastName(newLastName);
			
			this.eRepository.flush();
			
			this.cache.getEmployee(employeeId).setLastName(newLastName);

			//this.cache.updateCache(employeeId, employee);

			isChanged = true;
			
		}catch(Exception e){
			e.printStackTrace();
			isChanged = false;
		}
		
		return isChanged;
	}
}
