package views.screen.home;

import java.io.IOException;

import controller.HomeControl.HomeManagerController;
import entity.user.User;
import javafx.stage.Stage;
import utils.configs;

public class HomeScreenManagerHandler extends HomeScreen {

	public HomeScreenManagerHandler(Stage stage, User user, HomeManagerController controller) throws IOException {
		super(stage, configs.HOME_MANAGER_SCREEN);
		this.setBController(controller);
		this.setProfile(user, stage);
	}

}
