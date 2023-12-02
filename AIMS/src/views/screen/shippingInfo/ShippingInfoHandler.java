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
import view.screen.BaseScreenHandler;

public class ShippingInfoHandler extends BaseScreenHandler{
	private Logger LOGGER = utils.getLogger(ShippingInfoHandler.class.getName());
	
	protected Button btn_return;
	protected TextField name;
	protected TextField phone;
	protected ComboBox<String> province;
	protected TextArea instructions;
	protected CheckBox rush_order;
	protected Text shippingFees;
	protected Button confirm;
	protected Text name_error;
	protected Text phone_error;
	protected Text province_error;
	protected Text not_support_rush_order;
	private boolean send = false;
	
	protected HashMap<String, String> messages = new HashMap<String, String>();
	protected order order;

	public ShippingInfoHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		this.btn_return = (Button) this.content.lookup("#shipping_btn_return");
		this.name = (TextField) this.content.lookup("#shipping_name");
		this.phone = (TextField) this.content.lookup("#shipping_phone");
		this.province = (ComboBox<String>) this.content.lookup("#shipping_menu_province");
		this.instructions = (TextArea) this.content.lookup("#shipping_instructions");
		this.rush_order = (CheckBox) this.content.lookup("#shipping_rush_order");
		this.shippingFees = (Text) this.content.lookup("#shipping_fees");
		this.confirm = (Button) this.content.lookup("#shipping_confirm");
		this.name_error = (Text) this.content.lookup("#name_error");
		this.phone_error = (Text) this.content.lookup("#phone_error");
		this.province_error = (Text) this.content.lookup("#province_error");
		this.not_support_rush_order = (Text) this.content.lookup("#not_support_rush_order");
		this.not_support_rush_order.setVisible(false);
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
		if(!send) {
		messages.put("name", name.getText());
		messages.put("phone", phone.getText());
		messages.put("instructions", instructions.getText());
		messages.put("province", province.getValue());
		if(this.rush_order.isSelected()) messages.put("rush_order", "true" );
		else{
			messages.put("rush_order", "false");
			order.resetLstMediaRushOrder();
		}
		
		
		try {
			// process and validate delivery info
			getBController().processDeliveryInfo(messages, order, this);
			this.name_error.setVisible(false);
			this.phone_error.setVisible(false);
			this.province_error.setVisible(false);
		} catch (InvalidDeliveryInfoException e) {
			String m = e.getMessage();
			if(m.equals("name")) this.name_error.setVisible(true);
			else this.name_error.setVisible(false);
			if(m.equals("phone")) this.phone_error.setVisible(true);
			else this.phone_error.setVisible(false);
			if(m.equals("province")) this.province_error.setVisible(true);
			else this.province_error.setVisible(false);
			send = false;
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
	public HashMap<String, String> getMessage(){
		return this.messages;
	}
	
	public void setOrder(order order) {
		this.order = order;
	}
	
	
}
