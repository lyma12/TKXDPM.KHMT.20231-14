package entity.invoice;

import entity.order.order;


public class Invoice {
	private order order;
    private int amount;
    
    public Invoice(){

    }

    public Invoice(order order){
        this.order = order;
    }

    public order getOrder() {
        return order;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void saveInvoice(){
        
    }
}
