package entity.media;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import entity.db.AIMSDB;
import utils.utils;

public class media {
	private static Logger LOGGER = utils.getLogger(media.class.getName());
	
	protected Statement stm;
    protected int id;
    protected String title;
    protected String category;
    protected int value; // the real price of product (eg: 450)
    protected int price; // the price which will be displayed on browser (eg: 500)
    protected int quantity;
    protected String type;
    protected String barcode;
    protected Date ngay_nhap_kho;
    protected int size;
    protected String description;
    protected float weight;
    protected boolean support_rush_order;
    protected String imageURL;
    public media() throws SQLException{
        stm = AIMSDB.getConnection().createStatement();
    }
    
    public static List<media> searchMedia (String sql) throws SQLException{
    	Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
        List<media> r = new ArrayList<media>();
		while(res.next()) {
			media m  = new media()
                .setId(res.getInt("id"))
                .setTitle(res.getString("title"))
                .setQuantity(res.getInt("quantity"))
                .setCategory(res.getString("category"))
                .setMediaURL(res.getString("imageUrl"))
                .setWeight(res.getFloat("weight"))
                .setSupportRushOrder(res.getBoolean("support_rush_order"))
                .setPrice(res.getInt("price"))
                .setType(res.getString("type"));
			r.add(m);
        }
        return r;
    }
    
    public media (int id, String title, String category, int price, int quantity, String type ) throws SQLException{
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        //stm = AIMSDB.getConnection().createStatement();
    }

    public int getQuantity() throws SQLException{
        int updated_quantity = getMediaById(id).quantity;
        this.quantity = updated_quantity;
        return updated_quantity;
    }

    public media getMediaById(int id) throws SQLException{
        String sql = "SELECT * FROM Media WHERE id = " + id + ";";
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery(sql);
		if(res.next()) {

            return new media()
                .setId(res.getInt("id"))
                .setTitle(res.getString("title"))
                .setQuantity(res.getInt("quantity"))
                .setCategory(res.getString("category"))
                .setMediaURL(res.getString("imageUrl"))
                .setWeight(res.getFloat("weight"))
                .setSupportRushOrder(res.getBoolean("support_rush_order"))
                .setPrice(res.getInt("price"))
                .setType(res.getString("type"));
        }
        return null;
    }
    
    public List<media> getMediaByType(String type) throws SQLException{
    	try {
    	Statement stm = AIMSDB.getConnection().createStatement();
    	String sql = "SELECT * FROM Media WHERE Media.type = '" + type +"' ";
    	ResultSet res = stm.executeQuery(sql);
    	Set<media> medium = new HashSet<>();
    	while(res.next()) {
    		media media = new media()
                    .setId(res.getInt("id"))
                    .setTitle(res.getString("title"))
                    .setQuantity(res.getInt("quantity"))
                    .setCategory(res.getString("category"))
                    .setMediaURL(res.getString("imageUrl"))
                    .setPrice(res.getInt("price"))
                    .setWeight(res.getFloat("weight"))
                    .setSupportRushOrder(res.getBoolean("support_rush_order"))
                    .setType(res.getString("type"));
                medium.add(media);
    	}
    	List<media> result = new ArrayList<>();
    	for(media item: medium) {
    		result.add(item);
    	}
    	return result;}
    	catch(SQLException e) {
    		LOGGER.info("Errors occured: " + e.getMessage());
            e.printStackTrace();
    	}
    	return null;
    }
    
    public static List<String> getTypeMedia() throws SQLException{
    	Statement stm = AIMSDB.getConnection().createStatement();
    	ResultSet res = stm.executeQuery("select distinct type from media");
    	ArrayList<String> type = new ArrayList<>();
    	while(res.next()) {
    		String str = res.getString("type");
    		type.add(str);
    	}
    	return type;
    }

    public List<media> getAllMedia() throws SQLException{
        Statement stm = AIMSDB.getConnection().createStatement();
        ResultSet res = stm.executeQuery("select * from Media");
        ArrayList<media> medium = new ArrayList<>();
        while (res.next()) {
            media media = new media()
                .setId(res.getInt("id"))
                .setTitle(res.getString("title"))
                .setQuantity(res.getInt("quantity"))
                .setCategory(res.getString("category"))
                .setMediaURL(res.getString("imageUrl"))
                .setPrice(res.getInt("price"))
                .setWeight(res.getFloat("weight"))
                .setSupportRushOrder(res.getBoolean("support_rush_order"))
                .setType(res.getString("type"));
            medium.add(media);
        }
        return medium;
    }

    public void updateMediaFieldById(String tbname, int id, String field, Object value) throws SQLException {
        Statement stm = AIMSDB.getConnection().createStatement();
        if (value instanceof String){
            value = "\"" + value + "\"";
        }
        stm.executeUpdate(" update " + tbname + " set" + " " 
                          + field + "=" + value + " " 
                          + "where id=" + id + ";");
    }

    // getter and setter 
    public int getId() {
        return this.id;
    }

    private media setId(int id){
        this.id = id;
        return this;
    }
    
    public String getTitle() {
        return this.title;
    }

    public media setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCategory() {
        return this.category;
    }

    public media setCategory(String category) {
        this.category = category;
        return this;
    }

    public int getPrice() {
        return this.price;
    }

    public media setPrice(int price) {
        this.price = price;
        return this;
    }

    public String getImageURL(){
        return this.imageURL;
    }

    public media setMediaURL(String url){
        this.imageURL = url;
        return this;
    }

    public media setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getType() {
        return this.type;
    }

    public media setType(String type) {
        this.type = type;
        return this;
    }
    
    protected media setWeight(float w) {
    	this.weight = w;
    	return this;
    }
    public float getWeight() {
    	return this.weight;
    }
    protected media setSupportRushOrder(boolean support) {
    	this.support_rush_order = support;
    	return this;
    }
    public boolean getSupportRushOrder() {
    	return this.support_rush_order;
    }

    @Override
    public String toString() {
        return "Sản phẩm: " + 
            "\t\tID = " + id + "\n" + 
        	"\t\tTitle = " + title + "\n"+
        	"\t\tType = " + type +"\n";
    }    
}
