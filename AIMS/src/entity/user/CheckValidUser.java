package entity.user;

import common.exception.InvalidEmail;
import common.exception.InvalidPassword;

public class CheckValidUser {
	public static void checkValidInfo(String password, String email) {
		password = getString(password);
		email = getString(email);
		if(checkPassword(password)) throw new InvalidPassword("Hãy nhập mật khẩu của bạn!");
		if(checkEmail(email)) throw new InvalidEmail("Hãy nhập email của bạn!");
	}
	private static boolean checkPassword(String password) {
		if(password == null || password == "") return true;
		else return false;
	}
	private static boolean checkEmail(String email) {
		if(email != null) {
			String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$";  // Biểu thức chính quy kiểm tra email
		    return !email.matches(regex);
		}
		return true;
	}
	private static String getString(String text) {
		text = text.replaceAll("\\s", "");
		text = text.replaceAll("\n", "");
		return text;
	}
}
