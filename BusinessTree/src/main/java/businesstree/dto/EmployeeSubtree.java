package businesstree.dto;

import java.util.List;

public class EmployeeSubtree {
	
	private EmployeeNames names;
	private List<EmployeeSubtree> children;
	
	public EmployeeNames getNames() {
		return names;
	}
	public void setNames(EmployeeNames names) {
		this.names = names;
	}
	
	public List<EmployeeSubtree> getChildren() {
		return children;
	}
	public void setChildren(List<EmployeeSubtree> children) {
		this.children = children;
	}
}
