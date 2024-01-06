package views.screen.home;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import controller.HomeControl.HomeManagerController;
import entity.media.media;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import utils.configs;

public class HomeScreenManagerHandler extends HomeScreen {
	@FXML
	protected ListView listView;

	public HomeScreenManagerHandler(Stage stage, User user, HomeManagerController controller) throws IOException {
		super(stage, configs.HOME_MANAGER_SCREEN);
		this.setBController(controller);
		this.setProfile(user, stage);
		showList();
	}
	public void showList() {
		try {
			List<media> m = this.getBController().getAllMedia();
			for (Object item : m) {
				media media = (media) item;
				MedaiItemHandler mediaHandler = new MedaiItemHandler(media);
				this.listView.getItems().add(mediaHandler);
				this.listView.setCellFactory(
						listView -> new ListCell<MedaiItemHandler>() {
							public void updateItem(MedaiItemHandler item, boolean empty) {
								super.updateItem(item, empty);
								if (item != null || !empty) {
									setGraphic(item.getContent());
								} else {
									setGraphic(null);
								}
							}
						});
				this.listView.setOrientation(Orientation.VERTICAL);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
