package entity.invoice;

import entity.order.order;

// functional cohesion

public class Invoice {
	private order order;
    private int amount;
    
    public Invoice(order order){
        this.order = order;
        this.amount = this.order.getAmount() + this.order.getShippingFees();
    }
    public order getOrder() {
        return order;
    }

    public int getAmount() {
        return this.amount;
    }

    public void saveInvoice(){
        
    }
    
}
