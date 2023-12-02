package views.screen.cart;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import common.exception.MediaNotAvailableException;
import common.exception.PlaceOrderException;
import common.exception.ViewCartException;
import controller.PlaceOrderController;
import controller.ViewCartController;
import enity.cart.Cart;
import enity.cart.CartMedia;
import entity.order.order;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.utils;
import utils.configs;
import view.screen.BaseScreenHandler;
import views.screen.home.HomeScreenHandler;

public class CartScreenHandler extends BaseScreenHandler {
	
	private Logger LOGGER = utils.getLogger(CartScreenHandler.class.getName());
	
	protected ListView<MediaHandler> cart_list_item;
	protected Button cart_btn_return;
	protected Text cart_subtotal;
	protected Text cart_amount;
	protected Text cart_vat;
	protected Button cart_btn_place_order;
	protected Text not_enough_product;
	protected Text cart_error;

	public CartScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		this.cart_amount = (Text) this.content.lookup("#cart_amount");
		this.cart_btn_place_order = (Button) this.content.lookup("#cart_btn_place_order");
		this.cart_btn_return = (Button) this.content.lookup("#cart_btn_return");
		this.cart_vat = (Text) this.content.lookup("#cart_vat");
		this.cart_subtotal = (Text) this.content.lookup("#cart_subtotal");
		this.cart_list_item = (ListView) this.content.lookup("#cart_list_item");
		this.not_enough_product = (Text) this.content.lookup("#not_enough_product");
		this.cart_error = (Text) this.content.lookup("#cart_error");
		this.not_enough_product.setVisible(false);
		this.cart_btn_return.setOnMouseClicked(e -> {
			homeScreenHandler.show();}
			);
		this.cart_btn_place_order.setOnMouseClicked(e -> {
			LOGGER.info("Place Order button clicked");
			try {
				requestToPlaceOrder();
			} catch (SQLException | IOException exp) {
				LOGGER.severe("Cannot place the order, see the logs");
				exp.printStackTrace();
				throw new PlaceOrderException(Arrays.toString(exp.getStackTrace()).replaceAll(", ", "\n"));
			}
			
			
		});
		
	}
	private void requestToPlaceOrder() throws SQLException, IOException{
		this.cart_error.setVisible(false);
		try {
			
			PlaceOrderController placeOrderController = new PlaceOrderController();
			placeOrderController.placeOrder(this);
		}catch(MediaNotAvailableException e) {
			this.not_enough_product.setVisible(true);
			e.printStackTrace();
		}catch(ViewCartException e1) {
			e1.printStackTrace();
			this.cart_error.setVisible(true);
		}
		
	}
	
	public ViewCartController getBController() {
		return (ViewCartController) super.getBController();
	}
	
	public void requestToViewCart(HomeScreenHandler homeScreen) {
		this.setPreviousScreen(homeScreen);
		this.setScreenTitle("Cart Screen");
		this.displayCart();
		this.show();
	}
	
	private void displayCart() {
		this.cart_list_item.getItems().clear();
		try {
			List<CartMedia> lstMedia = Cart.getCart().getListMedia();
			for(CartMedia item: lstMedia) {
				MediaHandler m1 = new MediaHandler(configs.CART_MEDIA_PATH, this);
				m1.setMedia(item);
				this.cart_list_item.getItems().add(m1);

			}
			this.cart_list_item.setCellFactory(
					listView -> new ListCell<MediaHandler>() {
						public void updateItem(MediaHandler item, boolean empty) {
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
			updateCartAmount();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void updateCart() throws SQLException{
		getBController().checkAvailabilityOfProduct();
		displayCart();
	}

	public void updateCartAmount(){
		// calculate subtotal and amount
		int subtotal = getBController().getCartSubtotal();
		int vat = (int)((configs.PERCENT_VAT/100)*subtotal);
		int amount = subtotal + vat;
		LOGGER.info("amount" + amount);

		// update subtotal and amount of Cart
		this.cart_subtotal.setText(utils.getCurrencyFormat(subtotal));
		this.cart_vat.setText(utils.getCurrencyFormat(vat));
		this.cart_amount.setText(utils.getCurrencyFormat(amount));
	}
}
