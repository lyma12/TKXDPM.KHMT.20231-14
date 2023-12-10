package controller;

import java.io.IOException;
import java.sql.SQLException;

import entity.cart.Cart;
import javafx.stage.Stage;
import views.screen.BaseScreenHandler;

public class ViewCartController extends BaseController {

    public void checkAvailabilityOfProduct() throws SQLException { // functional cohesion

        Cart.getCart().checkAvailabilityOfProduct();
    }

    public int getCartSubtotal() { // functional cohesion
        int subtotal = Cart.getCart().calSubtotal();
        return subtotal;
    }
}
