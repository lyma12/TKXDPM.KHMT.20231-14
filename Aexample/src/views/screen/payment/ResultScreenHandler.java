package views.screen.payment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import view.screen.BaseScreenHandler;

public class ResultScreenHandler extends BaseScreenHandler {

	private String result;
	private String message;
	private Label pageTitle;
	private Label resultLabel;
	private Button okButton;
	private Label messageLabel;

	public ResultScreenHandler(Stage stage, String screenPath, String result, String message) throws IOException {
		super(stage, screenPath);
		this.pageTitle = (Label) this.content.lookup("#pageTitle");
		this.resultLabel = (Label) this.content.lookup("#resultLabel");
		this.okButton = (Button) this.content.lookup("#okButton");
		this.messageLabel = (Label) this.content.lookup("#messageLabel");
		this.okButton.setOnMouseClicked(e -> {
			try {
				this.confirmPayment();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		resultLabel.setText(result);
		messageLabel.setText(message);
	}

	void confirmPayment() throws IOException {
		homeScreenHandler.show();
	}

}
