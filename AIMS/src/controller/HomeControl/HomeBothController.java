package controller.HomeControl;

import java.io.IOException;

import entity.user.User;
import javafx.stage.Stage;
import views.screen.home.HomeScreenBothHandler;

public class HomeBothController extends HomeController implements IHomeAdminController, IHomeManagerController {

	@Override
	public void showHomeScreen(User user, Stage stage) throws IOException {
		HomeScreenBothHandler homeScreen = new HomeScreenBothHandler(stage, user, this);
		this.display(homeScreen, homeScreen, "Home Screen");
	}
	
	@Override
	public void managerProduct() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void managerOrder() {
		// TODO Auto-generated method stub
		
	}

}
