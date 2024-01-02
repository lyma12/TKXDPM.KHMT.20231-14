/*
    Coupling trong class RushOrderScreenHandler:
    - class order: tương tác với các đối tượng của class order thông qua truyền tham số và truy xuất phương thức
    - class orderMedia: tương tác với các đối tượng của class orderMedia thông qua truyền tham số và truy xuất phương thức
	- class RushOrderController
*/
package views.screen.shippingInfo;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import common.exception.InvalidDeliveryInfoException;
import controller.RushOrderController;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.configs;
import utils.utils;
import views.screen.BaseScreenHandler;
import views.screen.cart.MediaHandler;
import entity.order.order;
import entity.order.orderMedia;
import entity.shipping.Shipment;

public class RushOrderScreenHandler extends BaseScreenHandler {
	private Logger LOGGER = utils.getLogger(RushOrderScreenHandler.class.getName());
	
	private order order;
	protected Button btn_return;
	protected Button confirm;
	protected ComboBox<String> province;
	protected TextArea instruction;
	protected ComboBox<String> hour;
	protected ComboBox<String> minute;
	protected ComboBox<String> APM;
	protected ListView<RushOrderItem> listView;
	protected Shipment shippingInfo;
	protected Text validate_error;
	

	public RushOrderScreenHandler(Stage stage, String screenPath, order order, Shipment shippingInfo) throws IOException {
		super(stage, screenPath);
		this.order = order;
		this.btn_return = (Button) this.content.lookup("#rush_order_return");
		this.confirm = (Button) this.content.lookup("#rush_order_confirm");
		this.province = (ComboBox<String>) this.content.lookup("#rush_order_province");
		this.instruction = (TextArea) this.content.lookup("#rush_order_intruction");
		this.hour = (ComboBox<String>) this.content.lookup("#rush_order_hour");
		this.minute = (ComboBox<String>) this.content.lookup("#rush_order_minute");
		this.APM = (ComboBox<String>) this.content.lookup("#rush_order_B");
		this.listView = (ListView<RushOrderItem>) this.content.lookup("#rush_order_listView");
		this.validate_error = (Text) this.content.lookup("#validate_error");
		this.shippingInfo = shippingInfo;
		this.validate_error.setVisible(false);
		this.btn_return.setOnMouseClicked(e ->{
			LOGGER.info("Return Cart Screen");
			this.getPreviousScreen().show();
		});
		
		this.province.getItems().addAll(configs.DISTRICT_HA_NOI);
		this.hour.getItems().addAll(configs.HOURS);
		this.minute.getItems().addAll(configs.MINUTES);
		this.APM.getItems().addAll(configs.AMP);
		
		this.confirm.setOnMouseClicked(e -> insertDeliveryRushOrder());
		setScreenRushOrder();
	}
	private void setScreenRushOrder() {
		String instructions = this.shippingInfo.getInstructions();
		if(instructions != null && !instructions.isBlank()) this.instruction.setText(instructions);
		List<orderMedia> lstMedia = this.order.getLstOrderMediaRushOrder();
		
		try {
		for(orderMedia media: lstMedia) {
			RushOrderItem rushOrderItem = new RushOrderItem(configs.RUSHORDER_ITEM_SCREEN_PATH, media, this);
			this.listView.getItems().add(rushOrderItem);
		}
		this.listView.setCellFactory(
				listView -> new ListCell<RushOrderItem>() {
					public void updateItem(RushOrderItem item, boolean empty) {
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
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public RushOrderController getBController() {
		return (RushOrderController) super.getBController();
	}
	
	private void insertDeliveryRushOrder() {
		this.validate_error.setVisible(false);
		try {
			this.getBController().validateDeliveryInfo(shippingInfo, this.hour.getValue(), this.minute.getValue(), this.APM.getValue(), this.instruction.getText(), this.province.getValue(), this.stage, this.order);
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		} catch (InvalidDeliveryInfoException e1) {
			this.validate_error.setVisible(true);
		}
	}
	public void addLstMediaRushOrder(orderMedia media) {
		this.order.addOrderMediaRushOrder(media);
	}
	
	public void deleteMediaRushOrder(orderMedia media) {
		this.order.removeOrderMediaRushOrder(media);
	}
}
