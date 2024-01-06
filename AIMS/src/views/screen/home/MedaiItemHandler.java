package views.screen.home;

import java.io.IOException;

import entity.media.media;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import utils.configs;
import views.screen.FXMLScreenHandler;

public class MedaiItemHandler extends ItemManagerHandler {
	@FXML
	private ImageView image;
	@FXML
	private Label title;
	@FXML
	private Label price;
	public  MedaiItemHandler(media m) throws IOException {
		super(configs.MANAGER_MEDIA);
		setImage(image, m.getImageURL());
		this.title.setText(m.getTitle());
		this.price.setText(Integer.toString(m.getPrice()));
	}
}
