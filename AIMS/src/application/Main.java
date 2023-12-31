package application;
	
import java.io.IOException;
<<<<<<< Updated upstream
=======
import java.security.SecureRandom;
import java.sql.SQLException;

import controller.LogInController;
import entity.user.AuthenticationService;
>>>>>>> Stashed changes
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.configs;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
<<<<<<< Updated upstream

		/*	// initialize the scene
			StackPane root = (StackPane) FXMLLoader.load(getClass().getResource(configs.SPLASH_SCREEN_PATH));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();

			// Load splash screen with fade in effect
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), root);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);

			// Finish splash with fade out effect
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);

			// After fade in, start fade out
			fadeIn.play();
			fadeIn.setOnFinished((e) -> {
				fadeOut.play();
			});

			// After fade out, load actual content
			fadeOut.setOnFinished((e) -> {
				try {*/
					HomeScreenHandler homeHandler = new HomeScreenHandler(primaryStage, configs.HOME_PATH);
					homeHandler.setScreenTitle("Home Screen");
					homeHandler.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			/*});
		} catch (Exception e) {
			e.printStackTrace();
		}*/
=======
			HomeScreenHandler homeHandler = new HomeScreenHandler(primaryStage, configs.HOME_PATH);
			homeHandler.setScreenTitle("Home Screen");
			homeHandler.show();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
>>>>>>> Stashed changes
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
