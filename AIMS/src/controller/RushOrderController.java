/*
    Coupling trong class RushOrderController:
    - class order: tương tác với các đối tượng của class order thông qua phương thức getListOrderMedia, addOrderMediaRushOrder, ...
    - class orderMedia: tương tác với các đối tượng của class orderMedia thông qua truyền tham số
	- class RushOrderScreenHandler: tương tác với các đối tượng của class RushOrderScreenHandler thông qua truyền tham số, phương
	                                thức setPreviousScree, setBController, ...
	- class ShippingInfoHandler: tương tác với các đối tượng của class ShippingInfoHandler thông qua truyền tham số
*/
package controller;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import common.exception.InvalidDeliveryInfoException;
import common.exception.MediaOrProvinceNotSupportRushOrderException;
import entity.order.order;
import entity.order.orderMedia;
import javafx.application.Platform;
import javafx.stage.Stage;
import utils.configs;
import views.screen.BaseScreenHandler;
import views.screen.shippingInfo.RushOrderScreenHandler;
import views.screen.shippingInfo.ShippingInfoHandler;

public class RushOrderController extends BaseController {
	private static Logger LOGGER = utils.utils.getLogger(RushOrderController.class.getName());
	//data coupling class orderMedia
	private boolean checkMediaSupportRushOrder(orderMedia media) {
		return media.getMedia().getSupportRushOrder();

	
	public boolean checkDeliveryToRushOrder(order order, HashMap<String, String> message) {    //functional cohesion
		String province = message.get("province");
		if(!configs.PROVINCE_SUPPORT_RUSH_ORDER.contains(province)) return true;
		//data coupling class order
		List<orderMedia> lst = order.getlstOrderMedia();
		for(orderMedia item: lst) {
			if(checkMediaSupportRushOrder(item)) {
				order.addOrderMediaRushOrder(item);
				order.removeOrderMedia(item);
			}
		}
		if(order.getLstOrderMediaRushOrder().size() <= 0) return true;
		return false;
	}
	public void validateDeliveryInfo(HashMap<String, String> info, Stage stage, order order) throws InterruptedException, IOException{
    	if(validateDistrict(info.get("district"))) throw new InvalidDeliveryInfoException();
    	else if(validateHours(info.get("hour"), info.get("minute"), info.get("AMP"))) throw new InvalidDeliveryInfoException();
    	else {
    		order.setShippingFees(this.calculateShippingFee(order));
    		stage.close();
    	}
    }
	
	private boolean validateDistrict(String district) {
		if(district == "" || district == null) return true;
		return false;
	}
	private boolean validateHours(String hour,String minute, String AMP ) {
		if(hour.isBlank() || hour.isEmpty()) return true;
		if(minute.isBlank() || minute.isEmpty()) return true;
		if(AMP.isBlank() || AMP.isEmpty()) return true;
		LocalTime now = LocalTime.now();
		LOGGER.info(now.toString());
		if(AMP.equals("PM")) {
			int hourInt = Integer.parseInt(hour) + 12;
			hour = Integer.toString(hourInt);
		}
		LocalTime time = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(minute), 0);
		LOGGER.info(time.toString());
		LocalTime nowMinus2Hours = now.plusHours(2);
		
		if(time.isBefore(nowMinus2Hours)||time.equals(nowMinus2Hours)) return true;
		return false;
	}
	
	public void requestPlaceRushOrder(BaseScreenHandler shippingScreen, order order, HashMap<String, String> message) {     //data coupling class ShippingInfoHandler
		if(checkDeliveryToRushOrder(order, message)) {
			throw new MediaOrProvinceNotSupportRushOrderException();
		}
		try {
			Stage newStage = new Stage();
			//control and data coupling class RushOrderScreenHandler
			RushOrderScreenHandler rushOrderScreen = new RushOrderScreenHandler(newStage, configs.RUSHORDER_SCREEN_PATH, shippingScreen.getOrder(), shippingScreen.getMessage());
			rushOrderScreen.setBController(this);    //communication cohesion
			rushOrderScreen.setPreviousScreen(shippingScreen);
			rushOrderScreen.setHomeScreenHandler(shippingScreen.getHomeScreenHandler());
			rushOrderScreen.setScreenTitle("Rush Order Screen");
			rushOrderScreen.showAndWait();
		} catch (IOException e1) {
			e1.printStackTrace();    //coincidental cohesion
		} 
	}
	public int calculateShippingFee(order order){    //functional cohesion
  		int fees = 0;
  		float weight = 0.0f;
  		List<orderMedia> lst = order.getlstOrderMedia();
  		for(orderMedia m: lst) {
  			if(weight < m.getMedia().getWeight()) weight = m.getMedia().getWeight();
  		}
		// tổng tiền ship 10.000 vnd cho mỗi một sản phẩm giao hàng nhanh và tiền ship của sản phẩm không giao hàng nhanh
  		if(order.getAmount() < 100) {
			if(order.getlstOrderMedia().size() > 0) {
				if(weight <= 3) {
					fees = 22* ((int)weight + 1);
				}
				else {
					fees = (int) (66+((int)weight-2)/0.5*2.5);
				}
			}
			fees += order.getLstOrderMediaRushOrder().size()*10;
		}
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " );
        return fees;
    }
}
