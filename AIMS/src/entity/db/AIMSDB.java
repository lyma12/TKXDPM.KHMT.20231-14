package entity.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Logger;
import utils.utils;


public class AIMSDB {
	private static Logger LOGGER = utils.getLogger(Connection.class.getName());
	private static Connection connect;

    public static Connection getConnection() {
        if (connect != null) return connect;
        try {
        	Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:assets/db/aims_student.db";
            connect = DriverManager.getConnection(url);
            LOGGER.info("Connect database successfully");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        } 
        return connect;
    }
    //dvkm ovbt dpej ukcn

    public static void main(String[] args) {
    	AIMSDB.getConnection();
    	
    	
    }   
    
    
}