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
	public void placeOrder(CartScreenHandler cartScreen) throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
        if(Cart.getCart().getListMedia().size() <= 0) throw new ViewCartException();
        try {
			ShippingInfoHandler shippingScreenHandler = new ShippingInfoHandler(cartScreen.getStage() , configs.SHIPPING_SCREEN_PATH);
			shippingScreenHandler.setPreviousScreen(cartScreen);
			shippingScreenHandler.setHomeScreenHandler(cartScreen.getHomeScreenHandler());
			shippingScreenHandler.setScreenTitle("Shipping Screen");
			shippingScreenHandler.setBController(this);
			order order = createOrder();
			shippingScreenHandler.setOrder(order);
			shippingScreenHandler.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
	
	public order createOrder() throws SQLException{
        order order = new order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            orderMedia orderMedia = new orderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }
	public Invoice createInvoice(order order) {
        return new Invoice(order);
    }
	public void processDeliveryInfo(HashMap<String, String> info, order order, ShippingInfoHandler shippingScreen) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
        order.setShippingFees(calculateShippingFee(order, shippingScreen.getMessage()));
        if(info.get("rush_order") == "true") {
        RushOrderController rushOrderController = new RushOrderController();
		rushOrderController.requestPlaceRushOrder(shippingScreen);
        }
        order.setDeliveryInfo(info);
        Invoice invoice = createInvoice(order);
        InvoiceScreenHandler invoiceScreen = new InvoiceScreenHandler(shippingScreen.getStage(), configs.INVOICE_SCREEN_PATH, order, invoice);
        invoiceScreen.setPreviousScreen(shippingScreen);
		invoiceScreen.setHomeScreenHandler(shippingScreen.getHomeScreenHandler());
		invoiceScreen.setScreenTitle("Invoice Screen");
		invoiceScreen.setBController(this);
		invoiceScreen.show();
        //show invoice
    }
	public int getCartSubtotal(){
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
	public int calculateShippingFee(order order, HashMap<String, String> deliveryForm ){
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
	public void confirmInvoice(InvoiceScreenHandler invoiceScreen) {
		PaymentController paymentController = new PaymentController();
		paymentController.requestToPayOrder(invoiceScreen);
	}
}
