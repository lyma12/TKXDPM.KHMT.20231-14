package views.screen.home;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import controller.HomeControl.HomeController;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import utils.configs;
import entity.media.media;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import utils.utils;
import views.screen.BaseScreenHandler;
import views.screen.FXMLScreenHandler;

public class Group_Media extends FXMLScreenHandler {
	@FXML
	protected Text titleGroup;
	@FXML
	protected HBox listview_item_media;
	@FXML
	protected Button prevBtn;
	@FXML
	protected Button moreBtn;
	private HomeScreenHandler home;
	private List<media> itemList;
	private List<MediaHandler> itemHome;
	private int index;

	private static Logger LOGGER = utils.getLogger(Group_Media.class.getName());

	// procedural cohesion
	public Group_Media(String screenPath, String type, HomeScreenHandler home) throws SQLException, IOException {
		super(screenPath);
		this.listview_item_media = (HBox) this.content.lookup("#listview_item_media");
		this.home = home;
		index = 0;
		setGroupInfor(type, home.getBController());
	}
	
	private void addMediaHome(List<MediaHandler> items, int start){
        listview_item_media.getChildren().clear();
        for(int i = start; i < start + 7 && i <items.size(); i++) {
        	MediaHandler media = (MediaHandler) items.get(i);
        	media.getContent().setPrefWidth(210);
            listview_item_media.getChildren().add(media.getContent());
        }
        
    }
	
	
	// Functional Cohesion
	private void setGroupInfor(String type, HomeController bController) throws SQLException {
		this.titleGroup.setText(type.toUpperCase());
		this.prevBtn.setOnMouseClicked(e ->{
			if(index - 7 < 0) index = 0;
			else index -= 7;
			addMediaHome(itemHome, index);
		});
		this.moreBtn.setOnMouseClicked(e ->{
			if(index + 7 < itemHome.size()) index +=7;
			addMediaHome(itemHome, index);
		});
		
		try {
			itemList = bController.getListMediaByType(type);
			itemHome = new ArrayList<MediaHandler>();
			for (Object item : itemList) {
				media media = (media) item;
				MediaHandler mediaHandler = new MediaHandler(configs.HOME_MEDIA_ITEM, media, home.getBController().checkMediaInCart(media));
				this.itemHome.add(mediaHandler);
			}
			addMediaHome(itemHome, 0);
		} catch (SQLException | IOException e) {
			LOGGER.info("Errors occured: " + e.getMessage());
			e.printStackTrace();
		}

	}
}
