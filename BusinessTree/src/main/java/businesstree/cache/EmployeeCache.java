package businesstree.cache;

import java.util.ArrayList;
import java.util.List;

import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import businesstree.entity.Employee;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Component
public class EmployeeCache {
	
	@Autowired
	private CacheManager cm;
	
	public Employee getEmployee(long id){
		Cache cache = this.cm.getCache("employee");
		return (Employee)cache.get(id).getObjectValue();
	}
	
	
	@CacheResult(cacheName="employee")
	public Employee cachingEmployee(@CacheKey Long id, Employee emp){
		return emp;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Long> getKyes(){
		return this.cm.getCache("employee").getKeys();
	}
	
	
	public boolean remmoveEmployee(long id){
		
		Cache cache = this.cm.getCache("employee");
		boolean isRemoved;
		
		try{
			cache.remove(id);
			isRemoved = true;
		}catch(Exception e){
			e.printStackTrace();
			isRemoved = false;
		}
		
		return isRemoved;
	}
	
	
	public boolean updateCache(Long id, Employee employee){
		
		boolean isUpdated;
		Cache cache = this.cm.getCache("employee");
		
		try{
			Element element = new Element(id, employee);
			cache.put(element);
			isUpdated = true;
		}catch(Exception e){
			e.printStackTrace();
			isUpdated = false;
		}
		return isUpdated;
	}
	
	
	public List<Employee> getAll(){
		
		Cache cache = this.cm.getCache("employee");
		List<Employee> list = new ArrayList<>();
		
		for(Element element : cache.getAll(cache.getKeys()).values()){
			list.add((Employee)element.getObjectValue());
		}
		
		return list;
	}
}
