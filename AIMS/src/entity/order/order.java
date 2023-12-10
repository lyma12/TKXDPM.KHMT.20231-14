package entity.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import utils.configs;

// functional cohesion

public class order {
	private int shippingFees;
    private List<orderMedia> lstOrderMedia;
    private HashMap<String, String> deliveryInfo;
    private List<orderMedia> lstOrderMediaRushOrder;

    public order(){
        this.lstOrderMedia = new ArrayList<>();
        this.lstOrderMediaRushOrder = new ArrayList<>();
    }

    public order(List<orderMedia> lstOrderMedia) {
        this.lstOrderMedia = lstOrderMedia;
    }
    
    public void resetLstMediaRushOrder() {
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

    public int getShippingFees() {
        return shippingFees;
    }
    public HashMap<String, String> getDeliveryInfo() {
        return deliveryInfo;
    }

    public void setDeliveryInfo(HashMap<String, String> deliveryInfo) {
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
    
    public static void saveOrder(order order) {
    	
    }
}
