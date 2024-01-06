package entity.shipping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import entity.db.AIMSDB;

public class Shipment {
	private String name;
	private String province;
	private String instruction;
	private String address;
	private String phone;
	private String email;
	private Boolean rush_order;
	private String hours;
	private int ID;
	public Shipment() {
		super();
	}
	
	public Shipment(String name, String province, String instruction, String address, String phone, String email) {
		this.name = name;
		this.province = province;
		this.instruction = instruction;
		this.address = address;
		this.phone = phone;
		this.email = email;
	}
	public String getHours() {
		return this.hours;
	}
	public int getID() {
		return this.ID;
	}
	public String getEmail() {
		return this.email;
	}
	public String getName() {
		return this.name;
	}
	public String getAddress() {
		return this.address;
	}
	public String getInstructions() {
		return this.instruction;
	}
	public String getPhone() {
		return this.phone;
	}
	public String getProvince() {
		return this.province;
	}
	public Boolean getRushOrder() {
		return this.rush_order;
	}
	
	public void setRushOrder(Boolean rushOrder) {
		this.rush_order = rushOrder;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setInstructions(String instructions) {
		this.instruction = instructions;
	}
	public void setHours(String hours) {
		this.hours = hours;
	}
	@Override
	public String toString() {
		return "Thông tin địa chỉ người mua:\n" + 
				"Name: " + name +
				"\nAddress: " + address + 
				"\nProvince: " + province +
				"\nPhone: " + phone +
				"\nEmail: " + email;
	}
    public static int createNewShipment(Shipment shippingInfo) throws SQLException{
    	String sql = "SELECT * FROM deleveryinfo WHERE " + 
    				" name = '" + shippingInfo.name + "' AND" + 
    				" phone = '" + shippingInfo.phone + "' AND" +  
    				" email = '" + shippingInfo.email + "' AND" + 
    				" address = '" + shippingInfo.address + "' AND" + 
    				" province = '" + shippingInfo.province + "';";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        if(res.next()) {
        	return res.getInt("id");
        }
        else {
        	sql = "INSERT INTO deleveryinfo (name, phone, email, address, province, instructions) VALUES ('" + 
        				 shippingInfo.name + "', '" + shippingInfo.phone + "', '" + shippingInfo.email + "', '"  +
        				 shippingInfo.address + "', '" + shippingInfo.province + "', '" + shippingInfo.instruction + "')";
        	stm = AIMSDB.getConnection().createStatement();
        	stm.executeUpdate(sql);
        	try (ResultSet generatedKeys = stm.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                	shippingInfo.ID = generatedKeys.getInt(1);
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Không thể lấy ID vừa được tạo");
                }
            }
        }
        
    }

}
