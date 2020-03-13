package com.springmvcdemo.core;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.springmvcdemo.core.script.CreateDb;

@org.springframework.context.annotation.Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.springmvcdemo.core")
public class Configuration extends WebMvcConfigurerAdapter  {
	
	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	
	@Bean
    public ViewResolver viewResolver() {
		System.out.println("**viewResolver**");
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
	 }
	
	@Bean(name = "dataSource")
	@DependsOn("CreateDb")
    public DataSource dataSource() {
		System.out.println("***DataSource***");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/emp_crud");
        dataSource.setUsername("admin");
        dataSource.setPassword("The@1234");
        return dataSource;
    }
	
	@Bean(name = "jdbcTemplate")
	@DependsOn("dataSource")
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		System.out.println("***jdbcTemplate***");
		return new JdbcTemplate(dataSource);
	}
	
	@Bean(name = "CreateDb")
	public CreateDb createDbIfnNotExist() throws Exception {
		System.out.println("***createDbIfnNotExist***");
		return new CreateDb();
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor());
    }
	
	@PostConstruct
	public void init() {
	    requestMappingHandlerAdapter.setIgnoreDefaultModelOnRedirect(true);
	}
}
