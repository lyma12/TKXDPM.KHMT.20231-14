package views.screen.home;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import controller.HomeControl.HomeAdminController;
import entity.media.media;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import utils.configs;

public class HomeScreenAdminHandler extends HomeScreen {
	@FXML
	protected ListView listView;
	
	public HomeScreenAdminHandler(Stage stage, User user, HomeAdminController controller) throws IOException {
		super(stage, configs.HOME_ADMIN_SCREEN);
		this.setBController(controller);
		this.setProfile(user, stage);
		showList();
	}
	public void showList() {
		try {
			List<User> u = this.getBController().getAllUser();
			for (Object item : u) {
				User user = (User) item;
				UserHandler userHandler = new UserHandler(user);
				this.listView.getItems().add(userHandler);
				this.listView.setCellFactory(
						listView -> new ListCell<UserHandler>() {
							public void updateItem(UserHandler item, boolean empty) {
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
