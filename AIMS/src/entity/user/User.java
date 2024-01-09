package entity.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.db.AIMSDB;

public class User {
    private String name;
    private String email;
    private String address;
    private String phone;
    private String role;
    private String ho_ten;
    private int userId;
    
    public User findUser(String email) throws SQLException {
    	Statement pstm = AIMSDB.getConnection().createStatement();
		String sql = "SELECT * FROM User WHERE User.email = '" + email + "'";
	    ResultSet res = pstm.executeQuery(sql);
	    if(res.next()) {
	    	return new User()
	    			.setRole(res.getString("role"))
	    			.setPhone(res.getString("phone"))
	    			.setusername(res.getString("username"))
	    			.setEmail(res.getString("email"))
	    			.setAddress(res.getString("address"))
	    			.setUserId(res.getInt("id"))
	    			.setHoten(res.getString("ho_ten"));
	    }
    	return null;
    }
    
    public static List<User> getAllUser() throws SQLException {
    	Statement pstm = AIMSDB.getConnection().createStatement();
		String sql = "SELECT * FROM User";
	    ResultSet res = pstm.executeQuery(sql);
	    List<User> r = new ArrayList<User>();
	    while(res.next()) {
	    	User u =  new User()
	    			.setRole(res.getString("role"))
	    			.setPhone(res.getString("phone"))
	    			.setusername(res.getString("username"))
	    			.setEmail(res.getString("email"))
	    			.setAddress(res.getString("address"))
	    			.setUserId(res.getInt("id"))
	    			.setHoten(res.getString("ho_ten"));
	    	r.add(u);
	    }
    	return r;
    }
    
    public User() {
    	super();
    }

    public User(String name, String email, String address, String phone, String role){
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
    }
    protected User setHoten(String ho_ten) {
    	this.ho_ten = ho_ten;
    	return this;
    }
    protected User setRole(String role) {
    	this.role = role;
    	return this;
    }
    
    
    // override toString method
    @Override
    public String toString() {
        return "{" +
            "  username='" + name + "'" +
            ", ho ten ='" + ho_ten + "'" +
            ", email='" + email + "'" +
            ", address='" + address + "'" +
            ", phone='" + phone + "'" +
            ", role='" + role + "'" +
            "}";
    }

    // getter and setter
    public String getName() {
        return this.name;
    }

    protected User setusername(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return this.email;
    }
    public User setUserId(int id) {
    	this.userId = id;
    	return this;
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
    public String getRole() {
    	return this.role;
    }
    public int getUserId() {
    	return this.userId;
    }
    public String getHoten() {
    	return this.ho_ten;
    }
}
