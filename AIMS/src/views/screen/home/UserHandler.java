package views.screen.home;

import java.io.IOException;

import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import utils.configs;
import views.screen.FXMLScreenHandler;

public class UserHandler extends ItemManagerHandler {
	
	@FXML
	protected Label userName;
	@FXML
	protected Label ho_ten;
	@FXML
	protected Label email;
	@FXML
	protected Button updateBtn;
	@FXML
	protected Button deleteBtn;
	
	public UserHandler(User user) throws IOException {
		super(configs.USER_SCREEN);
		this.userName.setText(user.getName());
		this.ho_ten.setText(user.getHoten());
		this.email.setText(user.getEmail());
	}
}
