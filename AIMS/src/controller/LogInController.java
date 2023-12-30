package controller;

import java.io.IOException;
import java.sql.SQLException;

import entity.user.AuthenticationService;
import entity.user.User;
import javafx.stage.Stage;
import utils.configs;
import views.screen.LogIn.LogInScreen;

public class LogInController extends BaseController {
	public void processLogIn(Stage stage) throws IOException {
		LogInScreen logInScreen = new LogInScreen(stage, "" );
		
	}
	public void validateCredentials(String email, String password) throws SQLException {
		User user = AuthenticationService.authenticate(email, password);
	}
}
