package com.springmvcdemo.core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;

public class Util {

	public static Connection getConnection  (String host,int port,String schema,String user,String password) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");  
		String url="jdbc:mysql://${host}:${port}/${db_name}?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&useSSL=false&useLegacyDatetimeCode=false&allowPublicKeyRetrieval=true&serverTimezone="+Calendar.getInstance().getTimeZone().getID();
		url=url.replace("${host}",  host);
		url=url.replace("${db_name}", schema);
		url=url.replace("${port}",  port+"");
		Connection conn=DriverManager.getConnection(url,user,password);  
		return conn;
	}
	
	public static void closeConnection(Connection conn) {
		if(conn==null) return;
		try {
			if(!conn.getAutoCommit()) conn.commit();
		}catch (Exception e) {
//			e.printStackTrace();
		}
		try {
			if(!conn.isClosed()) {
				conn.close();
			}
		}catch (Exception e) {
			//e.printStackTrace();
		}
	}
}
