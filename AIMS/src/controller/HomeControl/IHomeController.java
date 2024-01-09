package controller.HomeControl;

import java.io.IOException;

import entity.user.User;
import javafx.stage.Stage;

public interface IHomeController {
	public void showHomeScreen(User user, Stage stage)throws IOException;
	public void showHomeScreen(Stage stage)throws IOException;
}
