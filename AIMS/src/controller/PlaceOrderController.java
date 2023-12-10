/*
    Coupling trong class PlaceOrderController:
    - class CartScreenHanlder: tương tác với đối tượng của lớp CartScreenHandler thông qua truyền tham số, các phương thức 
                               getState, getHomeScreenHandler
    - class InvoiceScreenHandler: tương tác với đối tượng của lớp InvoiceScreenHandler thông qua phương thức khởi tạo, truyền
                                  tham số, các phương thức setPreviousScreen, setHomeScreenHandler, ...
    - class ShippingInfoHandler: tương tác với đối tượng của lớp ShippingInfoHandler thông qua phương thức khởi tạo, setScreenTitle,
                                 setBController, ...
    - class Cart: tương tác với các đối tượng của class Cart thông qua phương thức getCart, getListMedia, getAvailabilityOfProduct
    - class CartMedia: tương tác với các đối tượng của class CartMedia thông qua phương thức getMedia, getQuantity, getPrice
    - class Invoice: tương tác với các đối tượng của class Invoice thông qua phương thức createInvoice
    - class order: tương tác với các đối tượng của class order thông qua phương thức setDeliveryInfo, createOrder, setOrder, ...
    - class orderMedia: tương tác với các đối tượng của class orderMedia
*/
package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

import common.exception.InvalidDeliveryInfoException;
import common.exception.MediaOrProvinceNotSupportRushOrderException;
import common.exception.ViewCartException;
import views.screen.cart.CartScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.payment.PaymentScreenHandler;
import views.screen.shippingInfo.*;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.order;
import entity.order.orderMedia;
import javafx.stage.Stage;
import utils.configs;
import views.screen.BaseScreenHandler;

public class PlaceOrderController extends BaseController {
	
	private static Logger LOGGER = utils.utils.getLogger(PlaceOrderController.class.getName());
	public void placeOrder(BaseScreenHandler cartScreen) throws SQLException{     //data coupling class CartScreenHandler
        Cart.getCart().checkAvailabilityOfProduct();     //control coupling class Cart
        if(Cart.getCart().getListMedia().size() <= 0) throw new ViewCartException();
        try {
        	order order = createOrder();
            //coupling class ShippingInfoHandler
			ShippingInfoHandler shippingScreenHandler = new ShippingInfoHandler(cartScreen.getStage() , configs.SHIPPING_SCREEN_PATH);
			shippingScreenHandler.setPreviousScreen(cartScreen);    //commuication cohesion
			shippingScreenHandler.setHomeScreenHandler(cartScreen.getHomeScreenHandler());
			shippingScreenHandler.setScreenTitle("Shipping Screen");
			shippingScreenHandler.setBController(this);
			order order = createOrder();
			shippingScreenHandler.setOrder(order);    //sequential cohesion
			shippingScreenHandler.show();
			
		} catch (IOException e) {
			e.printStackTrace();    //coincidental cohesion
		}
        
    }
	
	public order createOrder() throws SQLException{
        order order = new order();
        for (Object object : Cart.getCart().getListMedia()) {
            //data coupling class CartMedia
            CartMedia cartMedia = (CartMedia) object;
            //data coupling class orderMedia
            orderMedia orderMedia = new orderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);    //sequential cohesion
        }
        return order;
    }
	public Invoice createInvoice(order order) {    //functional cohesion
        return new Invoice(order);
    }
	public void processDeliveryInfo(HashMap<String, String> info, order order, BaseScreenHandler shippingScreen) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
        order.setShippingFees(calculateShippingFee(order, info));
        if(info.get("rush_order") == "true") {
        RushOrderController rushOrderController = new RushOrderController();
		rushOrderController.requestPlaceRushOrder(shippingScreen, order, info);
        }
        //control coupling class order
        order.setDeliveryInfo(info);
        Invoice invoice = createInvoice(order);     //control coupling class Invoice
        //control and data coupling class InvoiceScreenHandler
        InvoiceScreenHandler invoiceScreen = new InvoiceScreenHandler(shippingScreen.getStage(), configs.INVOICE_SCREEN_PATH, order, invoice);
        invoiceScreen.setPreviousScreen(shippingScreen);    //comunication cohesion
		invoiceScreen.setHomeScreenHandler(shippingScreen.getHomeScreenHandler());
		invoiceScreen.setScreenTitle("Invoice Screen");
		invoiceScreen.setBController(this);
		invoiceScreen.show();
        //show invoice
    }
	public int getCartSubtotal(){    //functional cohesion
        int subtotal = Cart.getCart().calSubtotal();
        return subtotal;
    }
	private void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	if(!validateName(info.get("name"))) throw new InvalidDeliveryInfoException("name", "invalid");
    	if(!validatePhoneNumber(info.get("phone"))) throw new InvalidDeliveryInfoException("phone", "invalid");
    	if(!validateAddress(info.get("province"))) throw new InvalidDeliveryInfoException("province", "invalid");
    }
    
    private boolean validatePhoneNumber(String phoneNumber) {
    	
    	if(phoneNumber.length() == 10) return true;
    	for(char i : phoneNumber.toCharArray()) {
    		if(i >= 'a' && i <= 'z') return true;
    		if(i >= 'A' && i <= 'Z') return true;
    	}
    	return false;
    }
    
    private boolean validateName(String name) {
    	if(name.length() > 0) return true;
    	return false;
    }
    private boolean validateAddress(String address) {
    	if(address.length() > 0) return true;
    	return false;
    }
    /*
     * Không tính thuế phí giao hàng
     * đơn hàng 100.000 VND miễn phí
     * chỉ tính với sản phẩm có khối lượng lớn nhất
     * giao hàng trong nội thành Hà Nội hoặc tp HCM giá khởi điểm 3kg đầu là 22.000 VND
     * giao hàng ở các vị trí khác là 0,5 kg đầu là 30.000 VND.
     * cứ 0.5kg tiếp theo, khách hàng sẽ phải trả thêm 2.500 VND
     * trong trường hợp giao hàng nhanh, khách trả thêm 10.000 VND với mỗi sản phẩm giao hàng nhanh.
     * ( vì các sản phẩm trong aims là sách, cd, dvd nên khối lượng sản phẩm lớn nhất coi như không quá 0.5 kg.)
     * */
	public int calculateShippingFee(order order, HashMap<String, String> deliveryForm ){    //functional cohesion
  		int fees = 0;
		
		// đơn hàng có tổng tiền sản phẩm trên 100 NVND sẽ được miễn phí.
		
		if(order.getAmount() < 100) {
			String province = deliveryForm.get("province");
			if(configs.SHIPPINGFEES_FIRST_22.contains(province)) {
				fees = 22;
			}
			else fees = 30;
		}
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
	public void confirmInvoice(InvoiceScreenHandler invoiceScreen) {    //functional cohesion
		PaymentController paymentController = new PaymentController();
		paymentController.requestToPayOrder(invoiceScreen, invoice);
		
	}
}
