package entity.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.db.AIMSDB;
import entity.media.media;

// functional cohesion

public class orderMedia {
	private media media;
    private int price;
    private int quantity;

    public orderMedia(media media, int quantity, int price) {
        this.media = media;
        this.quantity = quantity;
        this.price = price;
    }
    
    public static void saveOrderMedia(orderMedia m, int orderId){
    	try {
    		String sql = "INSERT INTO OrderMedia (orderId, price, quantity, mediaId) VALUES (" + 
    				orderId + ", " + m.price + ", " + m.quantity + ", " + m.media.getId() + ");";
    	    Statement stm = AIMSDB.getConnection().createStatement();
    	    stm.executeUpdate(sql);
      	} catch(SQLException e) {
    		System.out.println("Lỗi them dữ liệu vào bảng OrderMedia");
    		e.printStackTrace();
    	}
    	
    }
    
    @Override
    public String toString() {
        return "{" +
            "  media='" + media + "'" +
            ", quantity='" + quantity + "'" +
            ", price='" + price + "'" +
            "}";
    }
    
    public media getMedia() {
        return this.media;
    }

    public void setMedia(media media) {
        this.media = media;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
