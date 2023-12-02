package controller;

import java.io.IOException;
import java.sql.SQLException;

import enity.cart.Cart;
import javafx.stage.Stage;
import view.screen.BaseScreenHandler;

public class ViewCartController extends BaseController {

	public void checkAvailabilityOfProduct() throws SQLException{
		
        Cart.getCart().checkAvailabilityOfProduct();
    }
	public int getCartSubtotal(){
        int subtotal = Cart.getCart().calSubtotal();
        return subtotal;
    }
}
