package views.screen.home;

import javafx.scene.control.ListCell;
import controller.SearchMediaController;
import controller.ViewCartController;
import controller.HomeControl.HomeController;
import entity.user.User;
import utils.configs;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import common.exception.ViewCartException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.utils;
import views.screen.BaseScreenHandler;
import views.screen.cart.CartScreenHandler;

public class HomeScreenHandler extends HomeScreen implements Initializable {
	@FXML
	private Button btn_cart;
	@FXML
	private ListView<Group_Media> listview_group_media;
	@FXML
	private Button searchBtn;
	@FXML
	private TextField searchText;
	private User user;
	public static Logger LOGGER = utils.getLogger(HomeScreenHandler.class.getName());
	protected AnchorPane content; 

	public HomeScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
	}
	public HomeScreenHandler(Stage stage, String screenPath, User user, HomeController controller) throws IOException {
		super(stage, screenPath);
		this.user = user;
		this.setBController(controller);
	}
	public HomeController getBController() {
        return (HomeController) super.getBController();
    }
	@Override
	public void show() {
		super.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if(this.getBController() == null) setBController(new HomeController());
		this.setProfile(this.user, stage);
		try {
			List<String> typeOfMedia = getBController().getAllTypeMedia();
			for(String item: typeOfMedia) {
				Group_Media m1 = new Group_Media(configs.HOME_MEDIA_PATH, item, this);
				this.listview_group_media.getItems().add(m1);
			}
			this.listview_group_media.setCellFactory(
					listView -> new ListCell<Group_Media>() {
						public void updateItem(Group_Media item, boolean empty) {
							super.updateItem(item, empty);
							if(item != null || !empty) {
								setGraphic(item.getContent());
							}
							else {
								setGraphic(null);
							}
						}
					}
					);
		}catch (SQLException | IOException e){
			LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
		}
		this.btn_cart.setOnMouseClicked(e -> {
            CartScreenHandler cartScreen;
            
            
           try {
                LOGGER.info("User clicked to view cart");
                cartScreen = new CartScreenHandler(this.stage, configs.CART_SCREEN_PATH);
                cartScreen.setHomeScreenHandler(this);
                cartScreen.setBController(new ViewCartController());
                cartScreen.requestToViewCart(this);
            } catch (IOException e1) {
            	e1.printStackTrace();
                throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
            }
        });
		this.searchBtn.setOnMouseClicked( e ->{
			try {
	    	SearchMediaController controller = new SearchMediaController();
	    	controller.processSearch(this.getStage(), this, this.searchText.getText());
	    	}catch(IOException e1) {
	    		e1.printStackTrace();
	    	}catch(SQLException e1) {
	    		e1.printStackTrace();
	    	}
		});
	
	}
	
	

}
