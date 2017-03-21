package businesstree.dto;

//import java.util.List;

public class EmployeeDetails {
	
	private long id;
	private String firstName;
	private String lastName;
	private EmployeeNames parent;
	//private List<EmployeeNames> children;
	private String location;
	private double salary;
	private double addition;
	private double totalSalary;
		
	public EmployeeDetails(long id, String firstName, String lastName, EmployeeNames parent,
			String location, double salary, double addition,
			double totalSalary) {

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.parent = parent;
		//this.children = children;
		this.location = location;
		this.salary = salary;
		this.addition = addition;
		this.totalSalary = totalSalary;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
	public double getAddition() {
		return addition;
	}
	public void setAddition(double addition) {
		this.addition = addition;
	}
	
	public EmployeeNames getParent() {
		return parent;
	}
	public void setParent(EmployeeNames parent) {
		this.parent = parent;
	}
	
	/*
	public List<EmployeeNames> getChildren() {
		return children;
	}
	public void setChildren(List<EmployeeNames> children) {
		this.children = children;
	}
	*/
	
	public double getTotalSalary() {
		return totalSalary;
	}

	public void setTotalSalary(double totalSalary) {
		this.totalSalary = totalSalary;
	} 
}
