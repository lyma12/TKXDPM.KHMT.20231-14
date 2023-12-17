/*
    Coupling trong class MediaHandler:
	- class orderMedia: tương tác với đối tượng của lớp orderMedia thông qua truyền tham số
	- class PlaceOrderController 
*/
package views.screen.invoice;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import controller.PlaceOrderController;
import entity.order.orderMedia;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import utils.utils;
import views.screen.FXMLScreenHandler;

public class MediaHandler extends FXMLScreenHandler {

	private static Logger LOGGER = utils.getLogger(MediaHandler.class.getName());
	
	protected ImageView image;
	protected Text title;
	protected Text shipping;
	protected Text number;
	
	private orderMedia media;
	private boolean rush_order = false;
	
	//coupling class orderMedia
	public MediaHandler(String screenPath, orderMedia media, boolean rush_order) throws IOException {
		super(screenPath);
		this.image = (ImageView) this.content.lookup("#invoice_item_image");
		this.title = (Text) this.content.lookup("#invoice_item_title");
		this.shipping = (Text) this.content.lookup("#invoice_item_shipping");
		this.number = (Text) this.content.lookup("#invoice_item_number");
		this.media = media;
		this.rush_order = rush_order;
		setInfo();
	}
	private void setInfo() {
		this.setImage(image, this.media.getMedia().getImageURL());
		this.title.setText(this.media.getMedia().getTitle());
		String shipping_note = "";
		if(this.rush_order) shipping_note = "Sản phẩm được vận chuyển nhanh";
		else shipping_note = "Sản phẩm được vận chuyển thường";
		this.shipping.setText(shipping_note);
		this.number.setText(Integer.toString(this.media.getQuantity()));
	}
}
