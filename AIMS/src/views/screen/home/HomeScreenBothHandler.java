package views.screen.home;

import java.io.IOException;

import controller.HomeControl.HomeBothController;
import entity.user.User;
import javafx.stage.Stage;
import utils.configs;

public class HomeScreenBothHandler extends HomeScreen {

	public HomeScreenBothHandler(Stage stage, User user, HomeBothController controller) throws IOException {
		super(stage, configs.HOME_BOTH_SCREEN);
		this.setBController(controller);
		this.setProfile(user, stage);
	}

}
