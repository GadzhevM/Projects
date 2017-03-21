package businesstree.dto;

public class EmployeeInfo{
	
	private long id;
	private String firstName;
	private String lastName;
	private String location;
	private double salary;
	private double addition;
	
	public EmployeeInfo(long id, String firstName, String lastName, String location, double salary,
			double addition) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
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
}
