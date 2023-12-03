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

public class PaymentController extends BaseController {
	private Card card;
	private InterBankingInterface interbank;
	private order order;
	private static Logger LOGGER = utils.getLogger(PaymentController.class.getName());
	public void requestToPayOrder(InvoiceScreenHandler invoiceScreen) {
		this.order = invoiceScreen.getOrder();
		try {
			BaseScreenHandler paymentScreen = new PaymentScreenHandler(invoiceScreen.getStage(), configs.PAYMENT_SCREEN_PATH, invoiceScreen.getInvoice() );
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
	public Map<String, String> processOrderPayMent(Map<String, String> response, PaymentScreenHandler paymentScreen) {
		String contents = response.get("contents");
		this.card = new Card(response.get("cardNumber"), response.get("cardHolderName"), response.get("expirationDate"), response.get("securityCode"));
		// interBondary payOrder
		try {
			this.interbank = new VNPaySubsystem();
			PaymentTransaction payment = this.interbank.payOrder(this.card, this.order.getAmount(), contents);
			response.put("RESULT", "Giao dịch thành công!");
			response.put("MESSAGE", "Đơn hàng sẽ phê duyệt để chuyển đi!");
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
		//save transaction
		
	}
	private String getExpirationDate(String date) throws InvalidCardException {
		String[] strs = date.split("/");
		if (strs.length != 2) {
			throw new InvalidCardException();
		}

		String expirationDate = null;
		int month = -1;
		int year = -1;

		try {
			month = Integer.parseInt(strs[0]);
			year = Integer.parseInt(strs[1]);
			if (month < 1 || month > 12 || year < Calendar.getInstance().get(Calendar.YEAR) % 100 || year > 100) {
				throw new InvalidCardException();
			}
			expirationDate = strs[0] + strs[1];

		} catch (Exception ex) {
			throw new InvalidCardException();
		}

		return expirationDate;
	}
	/*public Map<String, String> payOrder(int amount, String contents, String cardNumber, String cardHolderName,
			String expirationDate, String securityCode) {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("RESULT", "PAYMENT FAILED!");
		try {
			this.card = new Card(cardNumber, cardHolderName, Integer.parseInt(securityCode),
					getExpirationDate(expirationDate));

			this.interbank = new InterbankSubsystem();
			PaymentTransaction transaction = interbank.payOrder(card, amount, contents);

			result.put("RESULT", "PAYMENT SUCCESSFUL!");
			result.put("MESSAGE", "You have succesffully paid the order!");
		} catch (PaymentException | UnrecognizedException ex) {
			result.put("MESSAGE", ex.getMessage());
		}
		return result;
	}*/

	public void emptyCart(){
        Cart.getCart().emptyCart();
    }
}
