/*
    Coupling trong class RushOrderItem:
    - class orderMedia: tương tác với các đối tượng của class orderMedia thông qua truyền tham số và truy xuất thuộc tính
*/
package views.screen.shippingInfo;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import entity.order.orderMedia;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import utils.utils;
import views.screen.FXMLScreenHandler;

public class RushOrderItem extends FXMLScreenHandler {
	
	private static Logger LOGGER = utils.getLogger(RushOrderItem.class.getName());
	private orderMedia media;
	private RushOrderScreenHandler rushOrderScreen;
	protected ImageView image;
	protected Text title;
	protected CheckBox checkBox;

    //coupling class orderMedia
	public RushOrderItem(String screenPath, orderMedia media, RushOrderScreenHandler rushOrderScreen) throws IOException {
		super(screenPath);
		this.media = media;
		this.rushOrderScreen = rushOrderScreen;
		this.checkBox = (CheckBox) this.content.lookup("#rush_order_item_check");
		this.image = (ImageView) this.content.lookup("#rush_order_item");
		this.title = (Text) this.content.lookup("#rush_order_item_title");
		setItemInfo();
	}
	private void setItemInfo() {
		File file = new File(media.getMedia().getImageURL());
		Image im = new Image(file.toURI().toString());
		this.checkBox.setSelected(true);
		this.image.setImage(im);
		this.title.setText(this.media.getMedia().getTitle());
		this.checkBox.selectedProperty().addListener(e ->{
			if(this.checkBox.isSelected()) this.rushOrderScreen.addLstMediaRushOrder(media);
			else this.rushOrderScreen.deleteMediaRushOrder(media);
		});
	}
}
