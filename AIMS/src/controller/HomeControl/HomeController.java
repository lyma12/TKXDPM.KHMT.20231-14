package controller.HomeControl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import controller.BaseController;
import controller.LogInController;
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
	public List<User> getAllUser() throws SQLException{
		return User.getAllUser();
	}
	public List<media> getListMediaByType(String type) throws SQLException { // Functional Cohesion
		return new media().getMediaByType(type);
	}

	
	@Override
	public void showHomeScreen(User user, Stage stage) throws IOException {
		HomeScreenHandler homeScreen = new HomeScreenHandler(stage, configs.HOME_PATH, user);
		this.display(homeScreen, homeScreen, "HomeScreen");
	}
	@Override
	public void showHomeScreen(Stage stage) throws IOException{
		HomeScreenHandler homeScreen = new HomeScreenHandler(stage, configs.HOME_PATH);
		this.display(homeScreen, homeScreen, "Home Screen");
	}
	
}
