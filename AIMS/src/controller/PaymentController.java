package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;
import entity.order.*;
import InterBankingInterface.InterBankingInterface;
import VNPaySubsystem.VNPaySubsystem;
import common.exception.GatewayTimeOutException;
import common.exception.InternalServerErrorException;
import common.exception.InvalidCardException;
import common.exception.InvalidDataErrorException;
import common.exception.NotEnoughBalanceException;
import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.cart.Cart;
import entity.invoice.Invoice;
import entity.payment.Card;
import entity.payment.PaymentTransaction;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.configs;
import utils.utils;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.payment.PaymentScreenHandler;
import views.screen.payment.ResultScreenHandler;

// functional cohesion
public class PaymentController extends BaseController {
	private Card card;
	private InterBankingInterface interbank;
	private Invoice invoice;
	private static Logger LOGGER = utils.getLogger(PaymentController.class.getName());
	public void requestToPayOrder(BaseScreenHandler invoiceScreen, Invoice invoice) {
		this.invoice = invoice;
		try {
			BaseScreenHandler paymentScreen = new PaymentScreenHandler(invoiceScreen.getStage(), configs.PAYMENT_SCREEN_PATH, invoice );
			paymentScreen.setBController(this);
			paymentScreen.setPreviousScreen(invoiceScreen);
			paymentScreen.setHomeScreenHandler(invoiceScreen.getHomeScreenHandler());
			paymentScreen.setScreenTitle("Payment Screen");
			paymentScreen.show();
			LOGGER.info("Confirmed invoice");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public Map<String, String> processOrderPayMent(Map<String, String> response) {
		String contents = response.get("contents");
		this.card = new Card(response.get("cardNumber"), response.get("cardHolderName"), response.get("expirationDate"), response.get("securityCode"));
		// interBondary payOrder
		try {
			this.interbank = new VNPaySubsystem();
			PaymentTransaction payment = this.interbank.payOrder(this.card, this.invoice.getAmount(), contents);
			response.put("RESULT", "Giao dịch thành công!");
			response.put("MESSAGE", "Đơn hàng sẽ phê duyệt để chuyển đi!");
			order.saveOrder(invoice.getOrder());
			Cart.getCart().emptyCart();
			PaymentTransaction.savePaymentTransaction(payment);
		}catch(InternalServerErrorException e) {
			response.put("RESULT", "Giao dịch thất bại!");
			response.put("MESSAGE", e.getMessage());
		}
		catch(InvalidDataErrorException e) {
			response.put("RESULT", "Giao dịch thất bại!");
			response.put("MESSAGE", e.getMessage());
		}catch(NotEnoughBalanceException e) {
			response.put("RESULT", "Giao dịch thất bại!");
			response.put("MESSAGE", e.getMessage());
		}catch(GatewayTimeOutException e) {
			response.put("RESULT", "Giao dịch thất bại!");
			response.put("MESSAGE", e.getMessage());
		}
		
		return response;
		
	}
}
