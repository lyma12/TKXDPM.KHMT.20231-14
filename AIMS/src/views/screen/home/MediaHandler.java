package views.screen.home;

import entity.media.media;
import utils.utils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import common.exception.MediaNotAvailableException;
import entity.cart.Cart;
import entity.cart.CartMedia;
import views.screen.FXMLScreenHandler;

public class MediaHandler extends FXMLScreenHandler {

	protected ImageView image_media_home;
	protected Label title_media_home;
	protected Text price_media_home;
	protected Spinner<Integer> spinner_media_home;
	protected Button btn_add_to_cart;
	
	private static Logger LOGGER = utils.getLogger(MediaHandler.class.getName());
	private media media;

	public MediaHandler (String screenPath, media media, CartMedia mediaInCart)throws SQLException, IOException {
		super(screenPath);
		this.media = media;
		this.btn_add_to_cart = (Button) this.content.lookup("#btn_add_to_cart");
		this.image_media_home = (ImageView) this.content.lookup("#media_image_home");
		this.title_media_home = (Label) this.content.lookup("#title_media_home");
		this.title_media_home.setWrapText(true);
		this.price_media_home = (Text) this.content.lookup("#price_media_home");
		this.spinner_media_home = (Spinner<Integer>) this.content.lookup("#spinner_media_home");
		this.btn_add_to_cart.setOnMouseClicked(event ->{
			try {
				if(spinner_media_home.getValue() > media.getQuantity()) throw new MediaNotAvailableException();
				Cart cart = Cart.getCart();
				if(mediaInCart != null) {
					mediaInCart.setQuantity(mediaInCart.getQuantity() + 1);
					
				}else {
					CartMedia cartMedia = new CartMedia(media, cart, spinner_media_home.getValue(), media.getPrice());
					cart.getListMedia().add(cartMedia);
					LOGGER.info("Added" + cartMedia.getQuantity() + " " + media.getTitle() + " to cart!");
					media.setQuantity(media.getQuantity() - spinner_media_home.getValue());
				}
			}catch(MediaNotAvailableException exp) {
					try {
						String message = "Not enough media:\nRequired: " + spinner_media_home.getValue() + "\nAvail: " + media.getQuantity();
						LOGGER.severe(message);
					} catch (Exception e) {
						LOGGER.severe("Cannot add media to cart: ");
					}
			}catch(Exception exp) {
					LOGGER.severe("Cannot add media to cart: ");
					exp.printStackTrace();
			}
			
		});
		
		setMediaInfor();
	}
	
	private void setMediaInfor() throws SQLException {
		
		title_media_home.setText(media.getTitle());
		price_media_home.setText(utils.getCurrencyFormat(media.getPrice()));
		spinner_media_home.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, media.getQuantity(), 1));
		setImage(image_media_home, media.getImageURL());
	}
}
