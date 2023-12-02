package controller;


import java.util.List;

import enity.cart.Cart;
import enity.cart.CartMedia;
import entity.media.media;

public class BaseController {
	public CartMedia checkMediaInCart(media media){
        return Cart.getCart().checkMediaInCart(media);
    }
	public List<CartMedia> getListCartMedia(){
        return Cart.getCart().getListMedia();
    }
}
