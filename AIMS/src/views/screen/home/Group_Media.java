package views.screen.home;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import com.mysql.cj.x.protobuf.MysqlxDatatypes.Scalar.String;

import javafx.scene.control.Cell;
import utils.configs;
import controller.HomeController;
import entity.media.media;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import utils.utils;
import views.screen.BaseScreenHandler;
import views.screen.FXMLScreenHandler;

public class Group_Media extends FXMLScreenHandler {
	@FXML
	protected Text titleGroup;
	@FXML
	protected ListView<MediaHandler> listview_item_media;
	private HomeScreenHandler home;

	private static Logger LOGGER = utils.getLogger(Group_Media.class.getName());

	// procedural cohesion
	public Group_Media(String screenPath, String type, HomeScreenHandler home) throws SQLException, IOException {
		super(screenPath);
		this.listview_item_media = (ListView) this.content.lookup("#listview_item_media");
		this.home = home;
		setGroupInfor(type, home.getBController());
	}

	// Functional Cohesion
	private void setGroupInfor(String type, HomeController bController) throws SQLException {
		this.titleGroup.setText(type);
		try {
			List<media> itemList = bController.getListMediaByType(type);
			for (Object item : itemList) {
				media media = (media) item;
				MediaHandler mediaHandler = new MediaHandler(configs.HOME_MEDIA_ITEM, media, home);
				this.listview_item_media.getItems().add(mediaHandler);
				this.listview_item_media.setCellFactory(
						listView -> new ListCell<MediaHandler>() {
							public void updateItem(MediaHandler item, boolean empty) {
								super.updateItem(item, empty);
								if (item != null || !empty) {
									setGraphic(item.getContent());
								} else {
									setGraphic(null);
								}
							}
						});
				this.listview_item_media.setOrientation(Orientation.HORIZONTAL);
			}
		} catch (SQLException | IOException e) {
			LOGGER.info("Errors occured: " + e.getMessage());
			e.printStackTrace();
		}

	}
}
