package controller;


import java.util.List;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.media;

// functional cohesion


public class BaseController {
	public CartMedia checkMediaInCart(media media){
        return Cart.getCart().checkMediaInCart(media); // data coupling
    }
	public List<CartMedia> getListCartMedia(){
        return Cart.getCart().getListMedia();            
    }
}
