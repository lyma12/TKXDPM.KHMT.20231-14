package controller.HomeControl;

import java.io.IOException;

import entity.user.User;
import javafx.stage.Stage;
import views.screen.home.HomeScreenBothHandler;
import views.screen.home.HomeScreenManagerHandler;

public class HomeManagerController extends HomeController implements IHomeController {

	@Override
	public void showHomeScreen(User user, Stage stage) throws IOException {
		HomeScreenManagerHandler homeScreen = new HomeScreenManagerHandler(stage, user, this);
		this.display(homeScreen, homeScreen, "Home Screen");
	}

}
