package common.exception;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AimsException extends RuntimeException {
	public AimsException() {
		
	}

    public AimsException(String message, String exception) {
    	super(message);

	}

	public AimsException(String message) {
		super(message);
		Stage stage = new Stage();
		stage.setTitle("Error");
		Label label = new Label("Thông báo");
		StackPane vbox = new StackPane();
		vbox.setPrefHeight(100);
		vbox.setPrefWidth(300);
		Scene scene = new Scene(vbox, 300, 100);
        stage.setScene(scene);
        vbox.getChildren().add(label);
        label.setText(message);
        stage.showAndWait();
	}
}