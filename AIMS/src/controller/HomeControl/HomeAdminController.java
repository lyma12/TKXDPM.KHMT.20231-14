package controller.HomeControl;

import java.io.IOException;
import entity.user.User;
import javafx.stage.Stage;
import views.screen.home.HomeScreenAdminHandler;

public class HomeAdminController extends HomeController implements IHomeController, IHomeAdminController {

	@Override
	public void showHomeScreen(User user, Stage stage) throws IOException {
		HomeScreenAdminHandler homeScreen = new HomeScreenAdminHandler(stage, user, this);
		this.display(homeScreen, homeScreen, "Home Screen");
	}
	

}
