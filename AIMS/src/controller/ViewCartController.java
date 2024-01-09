package controller;

import java.sql.SQLException;

import entity.cart.Cart;

public class ViewCartController extends BaseController {

    public void checkAvailabilityOfProduct() throws SQLException { // functional cohesion

        Cart.getCart().checkAvailabilityOfProduct();
    }

    public int getCartSubtotal() { // functional cohesion
        int subtotal = Cart.getCart().calSubtotal();
        return subtotal;
    }
}
