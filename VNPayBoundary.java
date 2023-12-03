package VNPaySubsystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import utils.utils;

class VNPayBoundary  {
	private static Logger LOGGER = utils.getLogger(VNPayBoundary.class.getName());
	private WebEngine engine;
	private WebView vnpay;
	private String response;
	
	protected String query(String url) {
		//url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html?vnp_Amount=1806000&vnp_Command=pay&vnp_CreateDate=20210801153333&vnp_CurrCode=VND&vnp_IpAddr=127.0.0.1&vnp_Locale=vn&vnp_OrderInfo=Thanh+toan+don+hang+%3A5&vnp_OrderType=other&vnp_ReturnUrl=https%3A%2F%2Fdomainmerchant.vn%2FReturnUrl&vnp_TmnCode=DEMOV210&vnp_TxnRef=5&vnp_Version=2.1.0&vnp_SecureHash=3e0d61a0c0534b2e36680b3f7277743e8784cc4e1d68fa7d276e79c23be7d6318d338b477910a27992f5057bb1582bd44bd82ae8009ffaf6d141219218625c42";
		Stage stage = new Stage();
		vnpay = new WebView();
		engine = vnpay.getEngine();
		engine.load(url);
		engine.getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
			if(newState == Worker.State.SUCCEEDED) {
				if (engine.getLocation().contains("ketquatrave")) {
					response = engine.getLocation();
					LOGGER.info("return: " + engine.getLocation());
					//Response t = new Response(response);
					//t.handleResponse();
					stage.close();
				}
			}
		});
		stage.setScene(new Scene(vnpay));
		stage.showAndWait();
		
        return response;
	}

}
