package entity.user;

import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.db.AIMSDB;
import utils.configs;

public class User {
	private int id;
    private String username;
    private String ho_ten;
    private String email;
    private String address;
    private String phone;
    private String role;
    
    public static User findUser(String email) throws SQLException {
    	Statement pstm = AIMSDB.getConnection().createStatement();
		String sql = "SELECT * FROM User WHERE User.email = '" + email + "'";
	    ResultSet res = pstm.executeQuery(sql);
	    if(res.next()) {
	    	return new User()
	    			.setusername(res.getString("username"))
	    			.setEmail(res.getString("email"))
	    			.setAddress(res.getString("address"))
	    			.setHoten(res.getString("ho_ten"))
	    			.setRole(res.getString("role"))
	    			.setPhone(res.getString("phone"));
	    }
	    System.out.print("r");
    	return null;
    }
    
    public User() {

    }
    public User(String name, String email, String address, String phone, String password, String role){
        this.username = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        if(role.equals("both")|| role.equals("admin")||role.equals("product_manager")) {
        	this.role = role;
        }
        else this.role = "none";
        String salt = this.getSalt();
        password = configs.hmacSHA512(salt, password) + salt;
        PreparedStatement pstm = null;
        try {
            pstm = AIMSDB.getConnection().prepareStatement("INSERT INTO User (email, password, salt, address, phone, role) VALUES (?, ?, ?, ?, ?, ? )");
            pstm.setString(1, email);
            pstm.setString(2, password);
            pstm.setString(3, salt);
            pstm.setString(4, address);
            pstm.setString(5, phone);
            pstm.setString(6, this.role);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null) {
                    pstm.close();
                }
                if (AIMSDB.getConnection() != null) {
                    AIMSDB.getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        
    }
    
    
    // override toString method
    @Override
    public String toString() {
        return "{" +
            "  username='" + username + "'" +
            ", email='" + email + "'" +
            ", address='" + address + "'" +
            ", phone='" + phone + "'" +
            "}";
    }

    // getter and setter
    public String getName() {
        return this.username;
    }
    private String getSalt() {
    	return new SecureRandom().generateSeed(16).toString();
    }

    protected User setusername(String name) {
        this.username = name;
        return this;
    }
    protected User setHoten(String ho_ten) {
    	this.ho_ten = ho_ten;
    	return this;
    }
    protected User setRole(String role) {
    	this.role = role;
    	return this;
    }

    public String getEmail() {
        return this.email;
    }

    protected User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return this.address;
    }

    protected User setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return this.phone;
    }

    protected User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

}
