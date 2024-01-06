package views.screen.search;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.CustomTextField;
import org.controlsfx.control.textfield.TextFields;

import common.exception.ViewCartException;
import controller.SearchMediaController;
import controller.ViewCartController;
import entity.media.Suggestion;
import entity.media.media;
import entity.user.User;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import utils.configs;
import views.screen.BaseScreenHandler;
import views.screen.cart.CartScreenHandler;
import views.screen.home.HomeScreenHandler;
import views.screen.home.MediaHandler;

public class SearchScreen extends BaseScreenHandler {
	@FXML
	protected ImageView btnReturn;
	@FXML
	protected ComboBox<String> searchByType;
	@FXML
	protected TextField searchText;
	@FXML
	protected Button btnSearch;
	@FXML
	protected ComboBox<String> sort;
	@FXML
	protected TextField minPrice;
	@FXML
	protected TextField maxPrice;
	@FXML
	protected RadioButton rushOrder;
	@FXML
	protected ListView listViewSuggestion;
	@FXML
	protected ListView<MediaHandler> mediaShow;
	@FXML
	protected Button more;
	@FXML
	protected Button btnCart;
	private List<String> sortType = Arrays.asList("Title", "Price");
	private Suggestion suggestions;
	private AutoCompletionBinding<String> binding;
	private List<media> listMedia;
	private String query;
	private User user;

	public SearchScreen(Stage stage, Suggestion suggestion, List<String> type, String text, User user) throws IOException, SQLException {
		super(stage, configs.SEARCH_SCREEN);
		type.add("All");
		this.suggestions = suggestion;
		this.searchText.setText(text);
		this.btnReturn.setOnMouseClicked( e -> this.getPreviousScreen().show());
		this.minPrice.setOnKeyReleased(event -> handlePriceChange(this.minPrice));
		this.maxPrice.setOnKeyReleased(event -> handlePriceChange(this.maxPrice));
		this.searchByType.getItems().setAll(type);
		this.sort.getItems().setAll(sortType);
		this.sort.setOnAction(e -> {
			this.sortMedia();
		});
		this.searchByType.setOnAction(e ->{
			this.search();
		});
		this.more.setOnMouseClicked(e -> getMoreMedia());
		this.btnSearch.setOnMouseClicked(e -> search());
		this.listMedia = new ArrayList<media>();
		this.btnCart.setOnMouseClicked(e ->{
			CartScreenHandler cartScreen;
	           try {
	                cartScreen = new CartScreenHandler(this.stage, configs.CART_SCREEN_PATH);
	                cartScreen.setHomeScreenHandler((HomeScreenHandler) this.getPreviousScreen());
	                cartScreen.setBController(new ViewCartController());
	                cartScreen.requestToViewCart(this.getHomeScreenHandler());
	                if(this.user != null) cartScreen.setUser(this.user);
	            } catch (IOException e1) {
	            	e1.printStackTrace();
	                throw new ViewCartException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
	            }
		});
		setSearchText();
	}
	
	
	private void sortMedia() {
		try {
		String sortValue = sort.getValue();
		if(sortValue.contains("Title")) {
			this.getBController().sortByTitle(this.listMedia);
			this.mediaShow.getItems().clear();
			showMedia();
			return;
		}
		if(sortValue.contains("Price")) {
			this.getBController().sortByPrice(this.listMedia);
			this.mediaShow.getItems().clear();
			showMedia();
			return;
		}
		}
		catch(IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void search() {
		this.listMedia.clear();
		if(!this.maxPrice.getText().isBlank()) {
			this.suggestions.setMaxPrice(Integer.parseInt(this.maxPrice.getText()));
		}
		if(!this.minPrice.getText().isBlank()) {
			this.suggestions.setMinPrice(Integer.parseInt(this.minPrice.getText()));
		}
		if(this.rushOrder.isSelected()) this.suggestions.setRushOrder(true);
		if(this.searchByType.getValue() != null) {
			if(!this.searchByType.getValue().contains("All")) this.suggestions.setType(this.searchByType.getValue());
			else this.suggestions.setType(null);
		}
		try {
			query = "'%" + this.searchText.getText() + "%'";
			this.listMedia = this.getBController().search( "'%" + this.searchText.getText() + "%'", this.listMedia.size());
			this.mediaShow.getItems().clear();
			if(this.sort.getValue() != null) {
				sortMedia();
			}
			else showMedia();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void showMedia() throws SQLException, IOException {
		for (Object item : this.listMedia) {
			media media = (media) item;
			MediaHandler mediaHandler = new MediaHandler(configs.HOME_MEDIA_ITEM, media, this.getBController().checkMediaInCart(media));
			this.mediaShow.getItems().add(mediaHandler);
			this.mediaShow.setCellFactory(
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
			this.mediaShow.setOrientation(Orientation.HORIZONTAL);
		}
	}
	
	private void getMoreMedia() {
		try {
			this.listMedia.addAll(this.getBController().search( query, this.listMedia.size()));
			this.mediaShow.getItems().clear();
			if(this.sort.getValue() != null) {
				sortMedia();
			}
			else showMedia();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public SearchMediaController getBController() {
		return (SearchMediaController) super.getBController();
	}
	private void setSearchText() {
		try {
			binding = TextFields.bindAutoCompletion(searchText, this.suggestions.getSuggestion());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void handlePriceChange(TextField text) {
	    try {
	        int textVal = Integer.parseInt(text.getText());
	        if (textVal <= 0) {
	            text.setText("1");
	        }
	        if(!this.maxPrice.getText().isBlank() && !this.minPrice.getText().isBlank()) {
	        	int minVal = Integer.parseInt(this.minPrice.getText());
		        int maxVal = Integer.parseInt(this.maxPrice.getText());
		        if(minVal > maxVal) {
		        	this.maxPrice.setText(Integer.toString(minVal));
		        }
	        }
	    } catch (NumberFormatException e) {
	        text.setText("");
	    }
	}

}
