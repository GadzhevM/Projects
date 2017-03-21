package businesstree.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.cache.annotation.Cacheable;

@Entity
//@Cacheable(cacheNames = {"employee"})
@DynamicUpdate
@DynamicInsert
@Table(schema = "business")
public class Employee  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Long id;
	
	private String firstName;
	private String lastName;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Employee parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private List<Employee> children;
	
	private Double salary;
	private double addition;
	private String location;
	private Timestamp dateOfUpdate;
	
	public Employee() {	}
	
	public Employee(String firstName, String lastname, String location, Double salary) {
		this.salary = salary;
		this.firstName = firstName;
		this.lastName = lastname;
		this.location = location;
		this.dateOfUpdate = new Timestamp(new Date().getTime());
	}

	public Long getId() {
		return id;
	}
	
	public Employee getParent() {
		return parent;
	}

	public void setParent(Employee parent) {
		this.parent = parent;
	}
	
	public List<Employee> getChildren() {
		return children;
	}
	
	public void setChildren(List<Employee> children) {
		this.children = children;
	}
	
	public double getSalary(){
		return salary;
	}
	
	public void setSalary(double salary){
		this.salary = salary;
	}
	
	public double getAddition(){
		return addition;
	}

	public void setAddition(double addition){
		this.addition = addition;
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

	public Timestamp getDateOfUpdate() {
		return dateOfUpdate;
	}

	public void setDateOfUpdate(Timestamp dateOfUpdate) {
		this.dateOfUpdate = dateOfUpdate;
	}
	
	
	@Override
	public boolean equals(Object o){
		if(!(o instanceof Employee)) return false;
		if(this == o) return true;
		
		Employee e = (Employee)o;
		return this.id.equals(e.getId());
	}
	
	
	@Override
	public int hashCode(){
		return id.hashCode();
	}
	
	@Override
	public String toString(){
		return "Name: " + this.firstName + " "+this.lastName+", Location: " +this.location;
	}
}
