package views.screen.home;

import java.io.IOException;

import controller.HomeControl.HomeAdminController;
import entity.user.User;
import javafx.stage.Stage;
import utils.configs;

public class HomeScreenAdminHandler extends HomeScreen {

	public HomeScreenAdminHandler(Stage stage, User user, HomeAdminController controller) throws IOException {
		super(stage, configs.HOME_ADMIN_SCREEN);
		this.setBController(controller);
		this.setProfile(user, stage);
	}
	
}
