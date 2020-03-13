package com.springmvcdemo.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvcdemo.core.dao.EmployeeDao;
import com.springmvcdemo.core.pojo.Employee;

@Controller
public class EmployeeController {

	 @Autowired    
	 EmployeeDao employeeDao;//will inject dao from XML file    
	        
    /*It displays a form to input data, here "command" is a reserved request attribute  
     *which is used to display object data into form  
     */    
    @RequestMapping("/empform")    
    public String showform(Model m){    
        m.addAttribute("command", new Employee());  
        return "empform";   
    }
    
    /*It saves object into database. The @ModelAttribute puts request data  
     *  into model object. You need to mention RequestMethod.POST method   
     *  because default request is GET*/    
    @RequestMapping(value="/save",method = RequestMethod.POST)    
    public ModelAndView  save(@ModelAttribute("emp") Employee emp,RedirectAttributes rm){    
        employeeDao.save(emp);   
        rm.addFlashAttribute("message", "Saved");
        rm.addAttribute("nonflash", "nonflashvalue");
        return new ModelAndView("redirect:/viewemp");//will redirect to viewemp request mapping    
    }
    
    /* It provides list of employees in model object */    
    @RequestMapping("/viewemp")    
    public ModelAndView viewemp(ModelAndView m){    
        List<Employee> list=employeeDao.getEmployees();    
        return new ModelAndView("viewemp","list",list);    
    }
    
    /* It displays object data into form for the given id.   
     * The @PathVariable puts URL data into variable.*/    
    @RequestMapping(value="/editemp/{id}")    
    public String edit(@PathVariable int id, Model m){    
    	Employee emp=employeeDao.getEmpById(id);    
        m.addAttribute("command",emp);  
        return "empeditform";    
    }
    
    /* It updates model object. */    
    @RequestMapping(value="/editsave",method = RequestMethod.POST)    
    public String editsave(@ModelAttribute("emp") Employee emp){    
        employeeDao.update(emp);    
        return "redirect:/viewemp";    
    }
    
    /* It deletes record for the given id in URL and redirects to /viewemp */    
    @RequestMapping(value="/deleteemp/{id}",method = RequestMethod.GET)    
    public String delete(@PathVariable int id){    
        employeeDao.delete(id);    
        return "redirect:/viewemp";    
    }     
}
