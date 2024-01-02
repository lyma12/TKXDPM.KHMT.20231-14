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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import entity.shipping.Shipment;
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
//			shippingScreenHandler.setPreviousScreen(cartScreen);
//			shippingScreenHandler.setHomeScreenHandler(cartScreen.getHomeScreenHandler());
//			shippingScreenHandler.setScreenTitle("Shipping Screen");
//			shippingScreenHandler.setBController(this);
			shippingScreenHandler.setOrder(order);
//			shippingScreenHandler.show();
			this.display(shippingScreenHandler, cartScreen, "Shipping Screen");

			
		} catch (IOException e) {
			e.printStackTrace();    //coincidental cohesion
		}
        
    }
	
    //bring this method to order.java to avoid SRP
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
	public void processDeliveryInfo(Shipment shippingInfo, order order, BaseScreenHandler shippingScreen) throws InterruptedException, IOException{
        validateDeliveryInfo(shippingInfo);
        order.setShippingFees(calculateShippingFee(order, shippingInfo.getProvince()));
        if(shippingInfo.getRushOrder()) {
        RushOrderController rushOrderController = new RushOrderController();
		rushOrderController.requestPlaceRushOrder(shippingScreen, order, shippingInfo);
        }
        //control coupling class order
        order.setDeliveryInfo(shippingInfo);
        Invoice invoice = createInvoice(order);     //control coupling class Invoice
        //control and data coupling class InvoiceScreenHandler
        InvoiceScreenHandler invoiceScreen = new InvoiceScreenHandler(shippingScreen.getStage(), configs.INVOICE_SCREEN_PATH, invoice, shippingInfo);

        //show invoice
        this.display(invoiceScreen, shippingScreen, "Invoice Screen");
    }

    //unnecessary method in class
	public int getCartSubtotal(){    //functional cohesion
        int subtotal = Cart.getCart().calSubtotal();
        return subtotal;
    }

    //separate validateDeliveryInfo and all its subs method to another class to avoid SRP 
	private void validateDeliveryInfo(Shipment shippingInfo) throws InterruptedException, IOException{
    	if(!validateName(shippingInfo.getName())) throw new InvalidDeliveryInfoException("name", "invalid");
    	if(!validatePhoneNumber(shippingInfo.getPhone())) throw new InvalidDeliveryInfoException("phone", "invalid");
    	if(!validateProvince(shippingInfo.getPhone())) throw new InvalidDeliveryInfoException("province", "invalid");
    	if(validateEmail(shippingInfo.getEmail())) throw new InvalidDeliveryInfoException("email", "invalid");
    	if(!validateAddress(shippingInfo.getAddress())) throw new InvalidDeliveryInfoException("address", "invalid");
    }
   
	private boolean validateEmail(String email) {
		if(email != null) {
			String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$";  // Biểu thức chính quy kiểm tra email
		    return !email.matches(regex);
		}
		return true;
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
    private boolean validateProvince(String address) {
    	if(address.length() > 0) return true;
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
     * trong trường hợp giao hàng nhanh, khách trả thêm 10.000 VND với mỗi sản phẩm giao hàng nhanh.*/
	public int calculateShippingFee(order order, String province ){
  		int fees = 0;
  		float weight = 0.0f;
  		List<orderMedia> lst = order.getlstOrderMedia();
  		for(orderMedia m: lst) {
  			if(weight < m.getMedia().getWeight()) weight = m.getMedia().getWeight();
  		}
		
		if(order.getAmount() < 100) {
			if(configs.SHIPPINGFEES_FIRST_22.contains(province)) {
				if(weight <= 3) {
					fees = 22* ((int)weight + 1);
				}
				else {
					fees = (int) (66+((int)weight-2)/0.5*2.5);
				}
			}
			else {
				if(weight <= 3) {
					fees = 30* ((int)weight + 1);
				}
				else {
					fees = (int) (90+((int)weight-2)/0.5*2.5);
				}
			}
		}
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
	public void confirmInvoice(InvoiceScreenHandler invoiceScreen, Invoice invoice) {    //functional cohesion
		PaymentController paymentController = new PaymentController();
		paymentController.requestToPayOrder(invoiceScreen, invoice);
		
	}
}
