package views.screen.home;

import java.io.IOException;

import controller.HomeControl.HomeController;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;

public class HomeScreen extends BaseScreenHandler {
	@FXML
	private Text username;
	@FXML
	private ImageView logo;
	@FXML
	private Pane paneProfile;
	@FXML
	private Button btnLogIn;
	@FXML
	private ImageView imageLogIn;
	

	public HomeScreen(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
	}
	public HomeController getBController() {
        return (HomeController) super.getBController();
    }
	
	protected void setProfile(User user, Stage stage) {
		if(user != null) {
			this.paneProfile.setVisible(true);
			if(this.logo != null) this.logo.setVisible(false);
			this.username.setText(user.getName());
			this.setImage(imageLogIn, "assets/images/logout.png");
			this.btnLogIn.setOnMouseClicked(e -> this.getBController().LogOut(stage));
		}
		else {
			this.paneProfile.setVisible(false);
			if(this.logo != null) this.logo.setVisible(true);
			this.setImage(imageLogIn, "assets/images/login-.png");
			this.btnLogIn.setOnMouseClicked(e -> this.getBController().LogIn(this));
		}
	}
}
