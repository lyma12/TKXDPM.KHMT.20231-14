package application;
	
import java.io.IOException;
import java.security.SecureRandom;
import java.sql.SQLException;

import entity.user.AuthenticationService;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.configs;
import views.screen.home.HomeScreenHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			HomeScreenHandler homeHandler = new HomeScreenHandler(primaryStage, configs.HOME_PATH);
			homeHandler.setScreenTitle("Home Screen");
			homeHandler.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
