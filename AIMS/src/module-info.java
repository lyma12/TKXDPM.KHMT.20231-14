module Aexample {
	requires javafx.controls;
	requires java.logging;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.web;
	requires java.mail;
	requires org.controlsfx.controls;
	requires javafx.base;
	exports views.screen to javafx.fxml;
	exports views.screen.cart to javafx.fxml;
	exports views.screen.search to javafx.fxml;
	
	
	opens application to javafx.graphics, javafx.fxml;
	opens views.screen.home to javafx.fxml;
	opens views.screen.cart to javafx.html;
	opens views.screen.shippingInfo to javafx.html;
	opens views.screen.invoice to javafx.html;
	opens views.screen.payment to javafx.html;
	opens views.screen.search to javafx.fxml;
}
