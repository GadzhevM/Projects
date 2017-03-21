package businesstree.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import businesstree.exceptions.BadRequest;
import businesstree.tree.Tree;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class EditEmployeeController {
	
	@Autowired
	private Tree tree;
	
	@RequestMapping(value = "/editFName", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Void> editFName (@RequestBody EditEmployee employee){
		
		boolean isChanged = this.tree.changeFristName(employee.getId(), employee.getEditedValue());
		
		if(isChanged){
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			throw new BadRequest();
		}
	}
	
	@RequestMapping(value = "/editLName", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Void> editLName (@RequestBody EditEmployee employee){
		
		boolean isChanged = this.tree.changeLastName(employee.getId(), employee.getEditedValue());
		
		if(isChanged){
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			throw new BadRequest();
		}
	}
	
	@RequestMapping(value = "/editLocation", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Void> editLocation (@RequestBody EditEmployee employee){
		
		boolean isChanged = this.tree.changeLocation(employee.getId(), employee.getEditedValue());
		
		if(isChanged){
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			throw new BadRequest();
		}
		
	}
	
	@RequestMapping(value = "/editSalary", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Void> editSalary (@RequestBody EditEmployee employee){
		
		boolean isChanged = this.tree.changeSalary(employee.getId(), Double.parseDouble(employee.getEditedValue()));
		
		if(isChanged){
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			throw new BadRequest();
		}
		
	}
	
	@RequestMapping(value = "/editAddition", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Void> editAddition (@RequestBody EditEmployee employee){
		
		boolean isChanged = this.tree.changeAddition(employee.getId(), Double.parseDouble(employee.getEditedValue()));
		
		if(isChanged){
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			throw new BadRequest();
		}
		
	}
	
	@RequestMapping(value = "/changeParent", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Void> changeParent (@RequestBody EditEmployee employee){
		
		System.out.println(employee.getId() + ", "+ Long.parseLong(employee.getEditedValue()));
		
		boolean isChanged = this.tree.attachTo(employee.getId(), Long.parseLong(employee.getEditedValue()));
		
		if(isChanged){
			return new ResponseEntity<>(HttpStatus.OK);
		}else{
			throw new BadRequest();
		}
		
	}
}

class EditEmployee{
	
	private long id;
	private String editedValue;
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getId(){
		return this.id;
	}
	
	public void setEditedValue(String editedValue){
		this.editedValue = editedValue;
	}
	
	public String getEditedValue(){
		return this.editedValue;
	}
}
