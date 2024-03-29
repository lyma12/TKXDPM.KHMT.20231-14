package controller;

import java.io.IOException;
import java.sql.SQLException;

import common.exception.AimsException;
import controller.HomeControl.HomeAdminController;
import controller.HomeControl.HomeBothController;
import controller.HomeControl.HomeController;
import controller.HomeControl.HomeManagerController;
import controller.HomeControl.IHomeController;
import entity.user.AuthenticationService;
import entity.user.CheckValidUser;
import entity.user.User;
import javafx.stage.Stage;

public class LogInController extends BaseController {
	public void validateCredentials(String email, String password, Stage stage) throws SQLException, IOException {
		CheckValidUser.checkValidInfo(password, email);
		User user = new AuthenticationService().authenticate(email, password);
		IHomeController homeController;
		if(user.getRole().contains("admin")) {
			homeController = new HomeAdminController();
			homeController.showHomeScreen(user, stage);
		}
		else if(user.getRole().contains("product_manager")) {
			homeController = new HomeManagerController();
			homeController.showHomeScreen(user, stage);
		}
		else if(user.getRole().contains("none")) {
			homeController = new HomeController();
			homeController.showHomeScreen(user, stage );
		}else if(user.getRole().contains("both")) {
			homeController = new HomeBothController();
			homeController.showHomeScreen(user, stage);
		}
		else throw new AimsException("Lỗi dữ liệu. Vui lòng đăng nhập lại!");
	}
	
}
