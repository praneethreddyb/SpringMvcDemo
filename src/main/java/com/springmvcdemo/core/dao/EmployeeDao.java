package com.springmvcdemo.core.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springmvcdemo.core.pojo.Employee;

@Repository(value = "employeeDao")
public class EmployeeDao {
	@Autowired
	JdbcTemplate template;    
    
	public void setTemplate(JdbcTemplate template) {    
	    this.template = template;    
	}    
	public int save(Employee p){    
	    String sql="insert into employee(name,salary,designation) values('"+p.getName()+"',"+p.getSalary()+",'"+p.getDesignation()+"')";    
	    return template.update(sql);    
	}    
	public int update(Employee p){    
	    String sql="update employee set name='"+p.getName()+"', salary="+p.getSalary()+",designation='"+p.getDesignation()+"' where id="+p.getId()+"";    
	    return template.update(sql);    
	}    
	public int delete(int id){    
	    String sql="delete from employee where id="+id+"";    
	    return template.update(sql);    
	}    
	public Employee getEmpById(int id){    
	    String sql="select * from employee where id=?";    
	    return template.queryForObject(sql, new Object[]{id},new BeanPropertyRowMapper<Employee>(Employee.class));    
	}    
	public List<Employee> getEmployees(){    
//	    return template.query("select * from employee",new RowMapper<Employee>(){    
//	        public Employee mapRow(ResultSet rs, int row) throws SQLException {    
//	        	Employee e=new Employee();    
//	            e.setId(rs.getInt(1));    
//	            e.setName(rs.getString(2));    
//	            e.setSalary(rs.getFloat(3));    
//	            e.setDesignation(rs.getString(4));    
//	            return e;    
//	        }    
//	    });   
		return new ArrayList<>();
	}    
}
