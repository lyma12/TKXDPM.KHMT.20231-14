package views.screen.LogIn;

import java.io.IOException;
import java.sql.SQLException;

import common.exception.AimsException;
import common.exception.InvalidEmail;
import common.exception.InvalidPassword;
import common.exception.InvalidUser;
import controller.LogInController;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;
import utils.configs;

/*
 * admin: email linhlinh17122002@gmail.com password my_password
('admin1', 'admin', 'linhlinh17122002@gmail.com', 'Dam Thi Ngay'),
('manager1',  'product_manager', 'danchandata@gmail.com', 'Dam  Thi Liep' ),
('admin2',  'both', 'lyma292002@gmail.com', 'Nguyen Quan' )
 */
public class LogInScreen extends BaseScreenHandler {
	private TextField emailText;
	private PasswordField passwordText;
	private TextField passwordTextShow;
	private Button confirmBtn;
	private ToggleButton showBtn;

	public LogInScreen(Stage stage) throws IOException {
		super(stage, configs.LOGIN_SCREEN);
		this.emailText = (TextField) this.content.lookup("#email");
		this.passwordTextShow = (TextField) this.content.lookup("#passwordShowField");
		this.passwordText = (PasswordField) this.content.lookup("#password");
		this.confirmBtn = (Button) this.content.lookup("#confirm");
		this.confirmBtn.setOnMouseClicked(e -> LogIn());
		this.content.lookup("#btnReturn").setOnMouseClicked(e ->{
			this.getPreviousScreen().show();
		});
		this.showBtn = (ToggleButton) this.content.lookup("#showPassword");
		this.showBtn.setOnMouseClicked(e ->{
			if(this.showBtn.isSelected()) {
				this.passwordTextShow.setText(this.passwordText.getText());
				this.passwordTextShow.setVisible(true);
				this.passwordText.setVisible(false);
			}
			else {
				this.passwordText.setText(this.passwordTextShow.getText());
				this.passwordText.setVisible(true);
				this.passwordTextShow.setVisible(false);
			}
		});
	}
	
	private void LogIn() {
		if (this.emailText.getStyle().contains("-fx-border-color: red")) {
            this.emailText.setStyle(this.emailText.getStyle().replace("-fx-border-color: red;", ""));
        }
		if (this.passwordText.getStyle().contains("-fx-border-color: red")) {
            this.passwordText.setStyle(this.passwordText.getStyle().replace("-fx-border-color: red;", ""));
        }
		try {
			String ps = this.passwordText.getText();
			if(this.showBtn.isSelected()) ps = this.passwordTextShow.getText();
			this.getBController().validateCredentials(this.emailText.getText(), ps, stage);
		} catch (SQLException e) {
			
		} catch (InvalidPassword p) {
			this.passwordText.setStyle("-fx-border-color: red; -fx-border-width: 2px");
		} catch (InvalidUser user) {
			this.emailText.setStyle("-fx-border-color: red; -fx-border-width: 2px");
			this.passwordText.setStyle("-fx-border-color: red; -fx-border-width: 2px");
		} catch (InvalidEmail email) {
			this.emailText.setStyle("-fx-border-color: red; -fx-border-width: 2px");
		} catch (IOException io) {
			
		} catch(AimsException ai) {
			
		}
	}
	public LogInController getBController() {
		return (LogInController) super.getBController();
	}

}
