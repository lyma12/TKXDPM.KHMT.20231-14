package entity.user;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import common.exception.InvalidEmail;
import common.exception.InvalidPassword;
import entity.db.AIMSDB;
import utils.configs;

public class AuthenticationService {
	public static User authenticate(String email, String password) throws SQLException {
		if(checkPassword(email, password)) {
			User user = User.findUser(email);
			System.out.println(user.toString());
			return user;
		}
		return null;
	}
	private static boolean checkPassword(String email, String password) throws SQLException {
		Statement pstm = AIMSDB.getConnection().createStatement();
		String sql = "SELECT password, salt FROM User WHERE User.email = '" + email + "'";
	    ResultSet res = pstm.executeQuery(sql);
	    if (res.next()) {
	        String pwd = res.getString("password");
	        String salt = res.getString("salt");
	        String check = configs.hmacSHA512(salt, password) + salt;
	        if(pwd.equals(check)) {
	        	System.out.print("password success");
	        	return true;
	        }
	        else {
	        	throw new InvalidPassword("Sai mật khẩu");
	        }
	    } else {
	        throw new InvalidEmail("Tài khoản không đúng. Vui lòng nhập lại!");
	    }
	}
}
