package views.screen.home;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import controller.HomeControl.HomeBothController;
import entity.media.media;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import utils.configs;

public class HomeScreenBothHandler extends HomeScreen {
	@FXML
	protected ListView<ItemManagerHandler> listView;
	@FXML
	protected Label listUser;
	@FXML
	protected Label listMediaManager;
	private List<media> listMedia;
	private List<User> listUserHandler;
	
	

	public HomeScreenBothHandler(Stage stage, User user, HomeBothController controller) throws IOException {
		super(stage, configs.HOME_BOTH_SCREEN);
		this.setBController(controller);
		this.setProfile(user, stage);
		try {
			this.listMedia = this.getBController().getAllMedia();
			this.listUserHandler = this.getBController().getAllUser();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		showUserList();
		this.listUser.setOnMouseClicked( e -> {
			this.listMediaManager.setTextFill(Color.BLACK);
			showUserList();
		});
		this.listMediaManager.setOnMouseClicked(e ->{
			this.listUser.setTextFill(Color.BLACK);
			showMediaList();
		});
		
	}
	
	public void showUserList() {
		this.listUser.setTextFill(Color.BLUE);
		this.listView.getItems().clear();
		try {
			for (Object item : this.listUserHandler) {
				User user = (User) item;
				ItemManagerHandler userHandler = new UserHandler(user);
				this.listView.getItems().add(userHandler);
				this.listView.setCellFactory(
						listView -> new ListCell<ItemManagerHandler>() {
							public void updateItem(ItemManagerHandler item, boolean empty) {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void showMediaList() {
		this.listMediaManager.setTextFill(Color.BLUE);
		this.listView.getItems().clear();
		try {
			for (Object item : this.listMedia) {
				media media = (media) item;
				ItemManagerHandler mediaHandler = new MedaiItemHandler(media);
				this.listView.getItems().add(mediaHandler);
				this.listView.setCellFactory(
						listView -> new ListCell<ItemManagerHandler>() {
							public void updateItem(ItemManagerHandler item, boolean empty) {
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
