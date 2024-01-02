package entity.invoice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.db.AIMSDB;
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
    @Override
    public String toString() {
    	return "Hóa đơn mua hàng: \n" + 
    			"Tổng tiền: " + this.amount + "VND\n" + 
    			this.order.toString();
    }
    public void saveInvoice(){
        try {
        	String sql = "INSERT INTO Invoice (totalAmount, orderId )VALUES (" + 
        			this.amount + ", " + this.order.getId() + ");";
        	Statement stm = AIMSDB.getConnection().createStatement();
        	stm.executeUpdate(sql);
        } catch(SQLException e) {
        	e.printStackTrace();
        }
    }
    
}
