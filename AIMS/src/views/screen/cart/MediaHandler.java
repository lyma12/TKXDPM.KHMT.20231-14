package views.screen.cart;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

import common.exception.MediaUpdateException;
import common.exception.ViewCartException;
import entity.cart.Cart;
import entity.cart.CartMedia;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import utils.utils;
import views.screen.FXMLScreenHandler;

public class MediaHandler extends FXMLScreenHandler {
	private static Logger LOGGER = utils.getLogger(MediaHandler.class.getName());
	
	private CartScreenHandler cartScreen;
	private CartMedia cartMedia;
	private ImageView image;
	private Text title;
	private Text price;
	private Text errorMessage;
	private Spinner<Integer> spinner;
	private Button delete;
	

	public MediaHandler(String screenPath, CartScreenHandler cartScreen) throws IOException {
		super(screenPath);
		this.cartScreen = cartScreen;
		this.delete = (Button) this.content.lookup("#btn_item_cart");
		this.errorMessage = (Text) this.content.lookup("#label_error_cart");
		this.image = (ImageView) this.content.lookup("#image_item_cart");
		this.price = (Text) this.content.lookup("#cart_item_price");
		this.title = (Text) this.content.lookup("#cart_item_tile");
		this.spinner  = (Spinner) this.content.lookup("#spinner_item_cart");
	}
	
	public void setMedia(CartMedia cartMedia) {
		this.cartMedia = cartMedia;
		setMediaInfor();
	}
	private void setMediaInfor() {
		File file = new File(cartMedia.getMedia().getImageURL());
		Image im = new Image(file.toURI().toString());
		this.image.setImage(im);
		this.title.setText(this.cartMedia.getMedia().getTitle());
		this.price.setText(utils.getCurrencyFormat(cartMedia.getPrice()));
		this.delete.setOnMouseClicked(
				e -> {
					try {
						Cart.getCart().removeCartMedia(cartMedia); // update user cart
						cartScreen.updateCart(); // re-display user cart
						LOGGER.info("Deleted " + cartMedia.getMedia().getTitle() + " from the cart");
					} catch (SQLException exp) {
						exp.printStackTrace();
						throw new ViewCartException();
					}
				}
				);
		initializeSpinner();
	}
	private void initializeSpinner() {
		this.spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL);
		try {
		    this.spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, this.cartMedia.getMedia().getQuantity(), this.cartMedia.getQuantity()));
			spinner.setOnMouseClicked( e -> {
				try {
					int numOfProd = this.spinner.getValue();
					if(numOfProd == 0) {
						Cart.getCart().removeCartMedia(cartMedia); // update user cart
						cartScreen.updateCart(); // re-display user cart
						LOGGER.info("Deleted " + cartMedia.getMedia().getTitle() + " from the cart");
					}
					else {
						int remainQuantity = cartMedia.getMedia().getQuantity();
						LOGGER.info("NumOfProd: " + numOfProd + " -- remainOfProd: " + remainQuantity);
						if (numOfProd > remainQuantity){
							LOGGER.info("product " + cartMedia.getMedia().getTitle() + " only remains " + remainQuantity + " (required " + numOfProd + ")");
							this.errorMessage.setText("Sorry, Only " + remainQuantity + " remain in stock");
							spinner.getValueFactory().setValue(remainQuantity);
							numOfProd = remainQuantity;
						}

						// update quantity of mediaCart in useCart
						cartMedia.setQuantity(numOfProd);

						// update the total of mediaCart
						price.setText(utils.getCurrencyFormat(numOfProd*cartMedia.getPrice()));

						// update subtotal and amount of Cart
						cartScreen.updateCartAmount();
					}
				} catch (SQLException e1) {
					throw new MediaUpdateException(Arrays.toString(e1.getStackTrace()).replaceAll(", ", "\n"));
				}
				
			});
		}catch(SQLException exp) {
			exp.printStackTrace();
		}
	}
}
