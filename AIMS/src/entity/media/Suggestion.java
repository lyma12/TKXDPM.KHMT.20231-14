package entity.media;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entity.db.AIMSDB;

public class Suggestion {
	private Boolean rushOrder;
	private int maxPrice;
	private int minPrice;
	private String type;
	private List<String> suggest;
	private String query;
	public Boolean getRushOrder() {
		return rushOrder;
	}
	public void setRushOrder(Boolean rushOrder) {
		if(rushOrder) this.query += " and support_rush_order = 1 ";
		this.rushOrder = rushOrder;
	}
	public int getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(int maxPrice) {
		if(maxPrice < Integer.MAX_VALUE) this.query += " and price <= " + maxPrice;
		this.maxPrice = maxPrice;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		if(type != null) this.query += " and type = '" + type + "' ";
		this.type = type;
	}
	public int getMinPrice() {
		return minPrice;
	}
	public String getQuery() {
		return this.query;
	}
	public void setMinPrice(int minPrice) {
		if(minPrice > Integer.MIN_VALUE) this.query += " and price >= " + minPrice;
		this.minPrice = minPrice;
	}
	
	public Suggestion() {
		this.query = "";
		this.rushOrder = false;
		this.maxPrice = Integer.MAX_VALUE;
		this.minPrice = Integer.MIN_VALUE;
		this.suggest = new ArrayList<String>();
	}
	public List<String> getSuggestion() throws SQLException {
		String sql = "SELECT title from Media ";
		if(type != null) {
			type = type.toLowerCase();
			sql += "where type = '" + type + "' ";
		}
		sql += "limit 20;";
		Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        while(res.next()) {
        	suggest.add(res.getString("title"));
        }
        return suggest;
	}
}
