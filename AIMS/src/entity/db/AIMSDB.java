package entity.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import entity.media.media;
import utils.utils;

public class AIMSDB {
	private static Logger LOGGER = utils.getLogger(Connection.class.getName());
	private static Connection connect;

    public static Connection getConnection() {
        if (connect != null) return connect;
        try {
//        	Class.forName("org.sqlite.JDBC");
//            String url = "jdbc:sqlite:assets/db/aims.db";
//            connect = DriverManager.getConnection(url);
        	Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/aims?useSSL=false";
            String password = "LinhLinh17122002";
            String user = "root";
            connect = DriverManager.getConnection(url,user, password );
            LOGGER.info("Connect database successfully");
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        } 
        return connect;
    }
    

    public static void main(String[] args) {
    	//AIMSDB.getConnection();
    	try {
			media m = new media();
			List<media> l = m.getAllMedia();
			for(media i : l) {
				LOGGER.info(i.toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        
    }
}
