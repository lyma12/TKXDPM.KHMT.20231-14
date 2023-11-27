package utils;

import java.util.Arrays;
import java.util.List;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class configs {
	// api constants
		public static final String GET_BALANCE_URL = "https://ecopark-system-api.herokuapp.com/api/card/balance/118609_group1_2020";
		public static final String GET_VEHICLECODE_URL = "https://ecopark-system-api.herokuapp.com/api/get-vehicle-code/1rjdfasdfas";
		public static final String PROCESS_TRANSACTION_URL = "https://ecopark-system-api.herokuapp.com/api/card/processTransaction";
		public static final String RESET_URL = "https://ecopark-system-api.herokuapp.com/api/card/reset";

		// demo data
		public static final String POST_DATA = "{"
				+ " \"secretKey\": \"BUXj/7/gHHI=\" ,"
				+ " \"transaction\": {"
				+ " \"command\": \"pay\" ,"
				+ " \"cardCode\": \"118609_group1_2020\" ,"
				+ " \"owner\": \"Group 1\" ,"
				+ " \"cvvCode\": \"185\" ,"
				+ " \"dateExpried\": \"1125\" ,"
				+ " \"transactionContent\": \"Pei debt\" ,"
				+ " \"amount\": 50000 "
				+ "}"
			+ "}";
		public static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiIxMTg2MDlfZ3JvdXAxXzIwMjAiLCJpYXQiOjE1OTkxMTk5NDl9.y81pBkM0pVn31YDPFwMGXXkQRKW5RaPIJ5WW5r9OW-Y";

		// database configs
		public static final String DB_NAME = "aims";
		public static final String DB_USERNAME = System.getenv("DB_USERNAME");
		public static final String DB_PASSWORD = System.getenv("DB_PASSWORD");

		public static String CURRENCY = "VND";
		public static float PERCENT_VAT = 10;

		// static resource
		public static final String IMAGE_PATH = "assets/images";
		public static final String INVOICE_SCREEN_PATH = "/view/fxml/invoice.fxml";
		public static final String INVOICE_MEDIA_SCREEN_PATH = "/view/fxml/invoice_item.fxml";
		public static final String PAYMENT_SCREEN_PATH = "/view/fxml/payment.fxml";
		public static final String RESULT_SCREEN_PATH = "/view/fxml/result.fxml";
		public static final String SPLASH_SCREEN_PATH = "/view/fxml/splash.fxml";
		public static final String CART_SCREEN_PATH = "/view/fxml/cart.fxml";
		public static final String SHIPPING_SCREEN_PATH = "/view/fxml/shippingInfor.fxml";
		public static final String CART_MEDIA_PATH = "/view/fxml/cart_item.fxml";
		public static final String HOME_PATH  = "/view/fxml/home.fxml";
		public static final String HOME_MEDIA_PATH = "/view/fxml/media_group_media.fxml";
		public static final String POPUP_PATH = "/view/fxml/popup.fxml";
		public static final String HOME_MEDIA_ITEM = "/view/fxml/item_media.fxml";
		public static final String RUSHORDER_SCREEN_PATH = "/view/fxml/rush_order_form.fxml";
		public static final String RUSHORDER_ITEM_SCREEN_PATH = "/view/fxml/rush_order_item.fxml";

		public static Font REGULAR_FONT = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 24);
		
		public static List<String> SHIPPINGFEES_FIRST_22 =  Arrays.asList("Hà Nội", "Hồ Chí Minh");
		public static List<String> PROVINCE_SUPPORT_RUSH_ORDER = Arrays.asList("Hà Nội");
		public static String[] DISTRICT_HA_NOI = { "Quận Ba Đình", "Quận Bắc Từ Liêm", "Quận Cầu Giấy", "Quận Đống Đa",
				"Quận Hà Đông", "Quận Hai Bà Trưng", "Quận Hoàn Kiếm", "Quận Hoàng Mai", "Quận Long Biên", "Quận Nam Từ Liêm",
				"Quận Tây Hồ", "Quận Thanh Xuân", "Thị xã Sơn Tây", "Huyện Chương Mỹ", "Huyện Đan Phượng", "Huyện Đông Anh",
				"Huyện Gia Lâm", "Huyện Hoài Đức", "Huyện Mê Linh", "Huyện Mỹ Đức", "Huyện Phú Xuyên", "Huyện Phúc Thọ",
				"Huyện Quốc Oai", "Huyện Sóc Sơn", "Huyện Thạch Thất", "Huyện Thanh Oai", "Huyện Thanh Trì", "Huyện Thường Tín", "Huyện Ứng Hòa"
		};
		public static String[] HOURS = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11"};
		public static String[] MINUTES = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
				 "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
				 "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
				 "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
				 "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
				 "51", "52", "53", "54", "55", "56", "57", "58", "59"
		};
		public static String[] AMP = {"AM", "PM"};

		public static String[] PROVINCES = { "Bắc Giang", "Bắc Kạn", "Cao Bằng", "Hà Giang", "Lạng Sơn", "Phú Thọ",
				"Quảng Ninh", "Thái Nguyên", "Tuyên Quang", "Yên Bái", "Điện Biên", "Hòa Bình", "Lai Châu", "Sơn La",
				"Bắc Ninh", "Hà Nam", "Hải Dương", "Hưng Yên", "Nam Định", "Ninh Bình", "Thái Bình", "Vĩnh Phúc", "Hà Nội",
				"Hải Phòng", "Hà Tĩnh", "Nghệ An", "Quảng Bình", "Quảng Trị", "Thanh Hóa", "Thừa Thiên-Huế", "Đắk Lắk",
				"Đắk Nông", "Gia Lai", "Kon Tum", "Lâm Đồng", "Bình Định", "Bình Thuận", "Khánh Hòa", "Ninh Thuận",
				"Phú Yên", "Quảng Nam", "Quảng Ngãi", "Đà Nẵng", "Bà Rịa-Vũng Tàu", "Bình Dương", "Bình Phước", "Đồng Nai",
				"Tây Ninh", "Hồ Chí Minh", "An Giang", "Bạc Liêu", "Bến Tre", "Cà Mau", "Đồng Tháp", "Hậu Giang",
				"Kiên Giang", "Long An", "Sóc Trăng", "Tiền Giang", "Trà Vinh", "Vĩnh Long", "Cần Thơ" };

}
