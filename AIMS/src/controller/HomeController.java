package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
<<<<<<< Updated upstream:AIMS/src/controller/HomeController.java
=======

import controller.BaseController;
import controller.LogInController;
>>>>>>> Stashed changes:AIMS/src/controller/HomeControl/HomeController.java
import entity.media.Book;
import entity.media.media;
import entity.user.User;
import javafx.stage.Stage;
import utils.configs;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;
import controller.HomeControl.IHomeController;


public class HomeController extends BaseController implements IHomeController {

	public List<media> getAllMedia() throws SQLException { // Functional Cohesion
		return new media().getAllMedia();
	}

	public List<String> getAllTypeMedia() throws SQLException { // Functional Cohesion
		return new media().getTypeMedia();
	}

	public List<media> getListMediaByType(String type) throws SQLException { // Functional Cohesion
		return new media().getMediaByType(type);
	}
	public void LogIn(BaseScreenHandler homeScreen) {
		try {
			LogInController logInController = new LogInController();
			logInController.processLogIn(homeScreen.getStage(), homeScreen);
			}
			catch(IOException e) {
				e.printStackTrace();
			}
	}
	public void LogOut(Stage stage) {
		try {
			System.out.print("hi");
			HomeScreenHandler homeHandler = new HomeScreenHandler(stage, configs.HOME_PATH);
			homeHandler.setScreenTitle("Home Screen");
			homeHandler.show();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public void showHomeScreen(User user, Stage stage) throws IOException {
		if(user == null) {
			HomeScreenHandler homeScreen = new HomeScreenHandler(stage, configs.HOME_PATH);
			this.display(homeScreen, homeScreen, "Home Screen");
		}
		else {
			HomeScreenHandler homeScreen = new HomeScreenHandler(stage, configs.HOME_PATH, user, this);
			this.display(homeScreen, homeScreen, "HomeScreen");
		}
		
	}
	
}
