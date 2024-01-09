package application;
	
import java.io.IOException;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.configs;
import views.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;
import javafx.application.Application;
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
//		try {
//	    	BaseScreenHandler prev = new BaseScreenHandler(configs.CART_MEDIA_PATH);
//	    	SearchMediaController controller = new SearchMediaController();
//	    	controller.processSearch(primaryStage, prev);
//	    	}catch(IOException e) {
//	    		e.printStackTrace();
//	    	}catch(SQLException e) {
//	    		e.printStackTrace();
//	    	}
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
}
