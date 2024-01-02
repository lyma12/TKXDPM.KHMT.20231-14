/*
    Coupling trong class ShippingInfoHandler:
    - class order: tương tác với các đối tượng của class order thông qua truyền tham số và truy xuất phương thức
	- class PlaceOrderController
*/
package views.screen.shippingInfo;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import common.exception.InvalidDeliveryInfoException;
import common.exception.MediaOrProvinceNotSupportRushOrderException;
import common.exception.PlaceOrderException;
import controller.PlaceOrderController;
import entity.order.order;
import entity.shipping.Shipment;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.configs;
import utils.utils;
import views.screen.BaseScreenHandler;

public class ShippingInfoHandler extends BaseScreenHandler{
	private Logger LOGGER = utils.getLogger(ShippingInfoHandler.class.getName());
	
	protected Button btn_return;
	protected TextField name;
	protected TextField phone;
	protected ComboBox<String> province;
	protected TextField email;
	protected TextArea address;
	protected TextArea instructions;
	protected CheckBox rush_order;
	protected Text shippingFees;
	protected Button confirm;
	protected Text not_support_rush_order;
	private boolean send = false;
	protected Shipment shippingInfo;
	protected order order;     //coupling class order

	public ShippingInfoHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		this.btn_return = (Button) this.content.lookup("#shipping_btn_return");
		this.name = (TextField) this.content.lookup("#shipping_name");
		this.phone = (TextField) this.content.lookup("#shipping_phone");
		this.province = (ComboBox<String>) this.content.lookup("#shipping_menu_province");
		this.address = (TextArea) this.content.lookup("#address");
		this.email = (TextField) this.content.lookup("#email");
		this.instructions = (TextArea) this.content.lookup("#shipping_instructions");
		this.rush_order = (CheckBox) this.content.lookup("#shipping_rush_order");
		this.shippingFees = (Text) this.content.lookup("#shipping_fees");
		this.confirm = (Button) this.content.lookup("#shipping_confirm");
		this.not_support_rush_order = (Text) this.content.lookup("#not_support_rush_order");
		this.not_support_rush_order.setVisible(false);
		this.shippingInfo = new Shipment();
		final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
		name.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
		this.province.getItems().addAll(configs.PROVINCES);
		this.province.getSelectionModel().selectedItemProperty().addListener( (observable, oldValue, newValue) ->{
			if(configs.SHIPPINGFEES_FIRST_22.contains(newValue))this.shippingFees.setText(utils.getCurrencyFormat(22));
			else this.shippingFees.setText(utils.getCurrencyFormat(30));
		}
		);
		this.btn_return.setOnMouseClicked(e -> {
			LOGGER.info("Return Cart Screen");
			this.getPreviousScreen().show();
		});
		this.confirm.setOnMouseClicked(e -> {
			LOGGER.info("Place Order button clicked");
			try {
				submitDeliveryForm();
			}catch(IOException | InterruptedException exp) {
				exp.printStackTrace();
			}
			});
	}
	
	private void submitDeliveryForm()throws IOException, InterruptedException {
		if (this.name.getStyle().contains("-fx-border-color: red")) {
            this.name.setStyle(this.name.getStyle().replace("-fx-border-color: red;", ""));
        }
		if (this.phone.getStyle().contains("-fx-border-color: red")) {
            this.phone.setStyle(this.phone.getStyle().replace("-fx-border-color: red;", ""));
        }
		if (this.email.getStyle().contains("-fx-border-color: red")) {
            this.email.setStyle(this.email.getStyle().replace("-fx-border-color: red;", ""));
        }
		if (this.address.getStyle().contains("-fx-border-color: red")) {
            this.address.setStyle(this.address.getStyle().replace("-fx-border-color: red;", ""));
        }
		if (this.province.getStyle().contains("-fx-border-color: red")) {
            this.province.setStyle(this.province.getStyle().replace("-fx-border-color: red;", ""));
        }
		if(!send) {
		this.shippingInfo.setName(this.name.getText());
		this.shippingInfo.setEmail(this.email.getText());
		this.shippingInfo.setAddress(this.address.getText());
		this.shippingInfo.setInstruction(this.instructions.getText());
		this.shippingInfo.setPhone(this.phone.getText());
		this.shippingInfo.setProvince(this.province.getValue());
		if(this.rush_order.isSelected()) this.shippingInfo.setRushOrder(true);
		else{
			this.shippingInfo.setRushOrder(false);
			order.resetLstMediaRushOrder();
		}
		
		
		try {
			// process and validate delivery info
			getBController().processDeliveryInfo(this.shippingInfo, order, this);
			
		} catch (InvalidDeliveryInfoException e) {
			String m = e.getMessage();
			if(m.contains("email")) this.email.setStyle("-fx-border-color: red; -fx-border-width: 2px");
			if(m.contains("province")) this.province.setStyle("-fx-border-color: red; -fx-border-width: 2px");
			if(m.contains("name")) this.name.setStyle("-fx-border-color: red; -fx-border-width: 2px");
			if(m.contains("phone")) this.phone.setStyle("-fx-border-color: red; -fx-border-width: 2px");
			if(m.contains("address")) this.address.setStyle("-fx-border-color: red; -fx-border-width: 2px");
		} catch (MediaOrProvinceNotSupportRushOrderException exp1) {
			this.not_support_rush_order.setVisible(true);
			this.messages.put("rush_order", "false");
			send = false;
		}
		}
	}
	
	@Override
	public PlaceOrderController getBController() {
		return (PlaceOrderController) super.getBController();
	}
	
	public order getOrder() {
		return this.order;
	}
	
	public void setOrder(order order) {
		this.order = order;
	}
	
	
}
