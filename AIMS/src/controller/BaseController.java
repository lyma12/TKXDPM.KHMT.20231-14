package controller;


import java.util.List;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.media;
import views.screen.BaseScreenHandler;

// functional cohesion




public class BaseController {
	public CartMedia checkMediaInCart(media media){
        return Cart.getCart().checkMediaInCart(media); // data coupling
    }
	public List<CartMedia> getListCartMedia(){
        return Cart.getCart().getListMedia();            
    }
	
	
	protected void display(BaseScreenHandler screen, BaseScreenHandler previousScreen, String title ) {
		screen.setBController(this);
		screen.setPreviousScreen(previousScreen);
		screen.setHomeScreenHandler(previousScreen.getHomeScreenHandler());
		screen.setScreenTitle(title);
		screen.show();
	}
}
