package businesstree.dto;

public class AddEmployee {
	
	private long id;
	private String firstName;
	private String lastName;
	private long parentId;
	private String location;
	private double salary;
	private double addition;
	
	public AddEmployee(){}
	
	public AddEmployee(long id, String firstName, String lastName, long parentId, String location, double salary,
			double addition) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.parentId = parentId;
		this.location = location;
		this.salary = salary;
		this.addition = addition;
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
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
}
