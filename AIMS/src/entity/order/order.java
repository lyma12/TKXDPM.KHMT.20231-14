package entity.order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.db.AIMSDB;
import entity.shipping.Shipment;
import entity.user.User;
import utils.configs;

// functional cohesion

public class order {
	private int id;
	private User user;
	private int shippingFees;
    private List<orderMedia> lstOrderMedia;
    private Shipment deliveryInfo;
    private List<orderMedia> lstOrderMediaRushOrder;

    public order(){
        this.lstOrderMedia = new ArrayList<>();
        this.lstOrderMediaRushOrder = new ArrayList<>();
    }

    public order(List<orderMedia> lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }
    
    public void resetLstMediaRushOrder() {
    	for(orderMedia item: this.lstOrderMediaRushOrder) {
    		this.lstOrderMedia.add(item);
    	}
    	this.lstOrderMediaRushOrder = new ArrayList<>();
    }
    
    public void addOrderMediaRushOrder(orderMedia media) {
    	if(this.lstOrderMedia.contains(media)) {
    		this.lstOrderMediaRushOrder.add(media);
    	}
    }
    public void removeOrderMediaRushOrder(orderMedia media) {
    	if(this.lstOrderMediaRushOrder.size() == 0) return;
    	if(this.lstOrderMediaRushOrder.contains(media)) {
    		this.lstOrderMediaRushOrder.remove(media);
    	}
    }
    public List<orderMedia> getLstOrderMediaRushOrder(){
    	return this.lstOrderMediaRushOrder;
    }
    public void addOrderMedia(orderMedia om){
        this.lstOrderMedia.add(om);
    }

    public void removeOrderMedia(orderMedia om){
        this.lstOrderMedia.remove(om);
    }

    public List getlstOrderMedia() {
        return this.lstOrderMedia;
    }

    public void setShippingFees(int shippingFees) {
        this.shippingFees = shippingFees;
    }
    public int getId() {
    	return this.id;
    }

    public int getShippingFees() {
        return shippingFees;
    }
    public String getDeliveryInfo() {
        return this.deliveryInfo.toString();
    }
    public String getEmail() {
    	return this.deliveryInfo.getEmail();
    }

    public void setDeliveryInfo(Shipment deliveryInfo) {
        this.deliveryInfo = deliveryInfo;
    }

    public int getAmount(){
        double amount = 0;
        for (Object object : lstOrderMedia) {
            orderMedia om = (orderMedia) object;
            amount += om.getPrice();                          // content coupling
        }
        return (int) (amount + (configs.PERCENT_VAT/100)*amount);         // common coupling
    }
    
    public static void saveOrder(order order) throws SQLException {
    	int deleveryId = Shipment.createNewShipment(order.deliveryInfo);
    	// save Order
    	// get Id Order from deleveryId
    	int orderId = 1;
    	String sql = "SELECT COUNT(*) AS total_orders FROM 'Order' WHERE deleveryInfoId = '" + deleveryId + "';";
    	Statement stm = AIMSDB.getConnection().createStatement();
    	ResultSet res = stm.executeQuery(sql);
    	if(res.next()) {
    		orderId = res.getInt("total_orders") + 1;
    		order.id = orderId;
    	}
    	else throw new SQLException("Lỗi lấy id của order");
    	//
    	if(order.user != null) {
    		sql = "INSERT INTO 'Order' (id, deleveryInfoId, shippingFees, user_id, accept) VALUES (" + 
    	          + orderId + " , " + deleveryId + " , '" + order.shippingFees + "', " + order.user.getUserId() + ", 'false');";
    		stm.executeUpdate(sql);
    	}
    	else {
    		sql = "INSERT INTO 'Order' (id, deleveryInfoId, shippingFees, accept) VALUES (" + 
      	          + orderId + " , " + deleveryId + " , '" + order.shippingFees + "', 'false');";
      		stm.executeUpdate(sql);
    	}
    	
    	sql = "SELECT id FROM 'Order' WHERE id = " + orderId + ";";
    	res = stm.executeQuery(sql);
    	if(res.next()) {
    		for(orderMedia ob : order.lstOrderMedia) {
        		orderMedia.saveOrderMedia(ob, orderId);
        	}
        	for(orderMedia ob: order.lstOrderMediaRushOrder) {
        		orderMedia.saveOrderMedia(ob, orderId);
        	}
    	}
    	else throw new SQLException("Lỗi thêm dữ liệu!");
    	
    }
    public void setUser(User user) {
    	this.user = user;
    }
    public User getUser() {
    	return this.user;
    }
    
    @Override
    public String toString() {
    	String message =  "Thông tin đơn hàng: \n" + 
    			"ID: " + this.id + "-" + this.deliveryInfo.getID() + ";\n" + 
    			"Tiền ship: " + this.shippingFees + "VND\n" + 
    			"Danh sách sản phẩm đã đặt hàng: \n"; 
    	for(orderMedia i : this.lstOrderMedia) {
    		message += i.toString();
    	}
    	message += "Các sản phẩm giao hàng nhanh: \n";
    	for(orderMedia i : this.lstOrderMediaRushOrder) {
    		message += i.toString();
    	}
    	message += this.deliveryInfo.toString();
    	return message;
    }
    
    

}
