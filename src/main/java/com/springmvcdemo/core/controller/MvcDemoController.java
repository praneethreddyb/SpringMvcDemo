package com.springmvcdemo.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MvcDemoController {

	@RequestMapping(value ="/",method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        model.addAttribute("message", "Home Page");
        return "home";
    }
	 
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public String sayHelloAgain(ModelMap model) {
        model.addAttribute("greeting", "Praneeth Kumar Reddy");
        return "welcome";
    }
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView modalAndView() {
    	ModelAndView mv=new ModelAndView();
    	mv.addObject("test", "test");
    	mv.addObject("test1", "test1");
    	mv.setViewName("test");
        return mv;
    }
	
}
