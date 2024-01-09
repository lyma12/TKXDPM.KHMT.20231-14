package views.screen.home;

import java.io.IOException;

import controller.LogInController;
import controller.HomeControl.HomeController;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.configs;
import views.screen.BaseScreenHandler;
import views.screen.LogIn.LogInScreen;

public class HomeScreen extends BaseScreenHandler {
	@FXML
	protected Text username;
	@FXML
	protected ImageView logo;
	@FXML
	protected Pane paneProfile;
	@FXML
	protected Button btnLogIn;
	@FXML
	protected ImageView imageLogIn;
	@FXML
	protected TextField searchText;
	@FXML
	protected Button searchBtn;

	public HomeScreen(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		setProfile(stage);
	}
	public HomeScreen(Stage stage, String screenPath, User user) throws IOException {
		super(stage, screenPath);
		setProfile(user, stage);
	}
	public HomeController getBController() {
        return (HomeController) super.getBController();
    }
	
	protected void setProfile(User user, Stage stage) {
		this.paneProfile.setVisible(true);
		if(this.logo != null) this.logo.setVisible(false);
		this.username.setText(user.getName());
		this.setImage(imageLogIn, "assets/images/logout.png");
		this.btnLogIn.setOnMouseClicked(e ->{
			try {
				HomeScreenHandler homeHandler = new HomeScreenHandler(stage, configs.HOME_PATH);
				homeHandler.setScreenTitle("Home Screen");
				homeHandler.show();
			}catch(IOException e1) {
				e1.printStackTrace();
			}
		});
	}
	protected void setProfile(Stage stage) {
		this.paneProfile.setVisible(false);
		if(this.logo != null) this.logo.setVisible(true);
		this.setImage(imageLogIn, "assets/images/login-.png");
		this.btnLogIn.setOnMouseClicked(e -> {
			try {
				LogInScreen logInScreen = new LogInScreen(stage);
				logInScreen.setPreviousScreen(this);
				logInScreen.setHomeScreenHandler(this);
				logInScreen.setScreenTitle("Log In Screen");
				logInScreen.setBController(new LogInController());
				logInScreen.show();
			}catch(IOException e1) {
				e1.printStackTrace();
			}
		});
	}
}
