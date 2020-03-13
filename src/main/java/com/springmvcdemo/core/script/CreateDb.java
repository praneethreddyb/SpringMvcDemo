package com.springmvcdemo.core.script;

import java.sql.Connection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.springmvcdemo.core.util.Util;

public class CreateDb {

	private Connection conn;
	
	public CreateDb() throws Exception {
		try {
			conn=Util.getConnection("localhost", 3306, "emp_crud", "admin", "The@1234");
		}catch (Exception e) {
			if(e.getMessage().contains("Unknown database")) {
				createMysqlDbSchemaIfNotExists("localhost", 3306, "admin", "The@1234", "emp_crud");
				conn=Util.getConnection("localhost", 3306, "emp_crud", "admin", "The@1234");
				createDb();
				Util.closeConnection(conn);
			}
		}
	}
	public void createDb() {
		JdbcTemplate jdbcTemplate=new JdbcTemplate(new SingleConnectionDataSource(conn, true));
		String query="CREATE TABLE `employee` (\r\n" + 
				"  `pk_id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,\r\n" + 
				"  `name` VARCHAR(255) NULL,\r\n" + 
				"  `email` VARCHAR(160) NULL,\r\n" + 
				"  `designation` VARCHAR(60) NULL,\r\n" + 
				"  `salary` DECIMAL(65,2) NULL,\r\n" + 
				"  PRIMARY KEY (`pk_id`))";
		try{
			jdbcTemplate.execute(query);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createMysqlDbSchemaIfNotExists(String host,int port,String user,String password,String schema)throws Exception {
		MysqlDataSource mysqlDs=new MysqlDataSource();
		mysqlDs.setServerName(host);
		mysqlDs.setPort(port); 
		mysqlDs.setUser(user);
		mysqlDs.setPassword(password);
		JdbcTemplate jdbcTemplate=new JdbcTemplate(mysqlDs);
		jdbcTemplate.execute("CREATE SCHEMA IF NOT EXISTS `"+schema+"`  DEFAULT CHARSET=utf8"); 
		if(!mysqlDs.getConnection().isClosed()) mysqlDs.getConnection().close();
	}
}
