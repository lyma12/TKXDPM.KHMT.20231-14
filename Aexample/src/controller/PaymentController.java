package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Logger;

import common.exception.InvalidCardException;
import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import enity.cart.Cart;
import entity.invoice.Invoice;
import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;
import javafx.stage.Stage;
import subsystem.InterbankInterface;
import subsystem.InterbankSubsystem;
import utils.configs;
import utils.utils;
import view.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.payment.PaymentScreenHandler;
import views.screen.payment.ResultScreenHandler;

public class PaymentController extends BaseController {
	private CreditCard card;
	private InterbankInterface interbank;
	private static Logger LOGGER = utils.getLogger(PaymentController.class.getName());
	public void requestToPayOrder(InvoiceScreenHandler invoiceScreen) {
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
	public void processOrderPayMent(Map<String, String> response, PaymentScreenHandler paymentScreen) {
		try {
			BaseScreenHandler resultScreen = new ResultScreenHandler(paymentScreen.getStage(), configs.RESULT_SCREEN_PATH, response.get("RESULT"), response.get("MESSAGE") );
			resultScreen.setPreviousScreen(paymentScreen);
			resultScreen.setHomeScreenHandler(paymentScreen.getHomeScreenHandler());
			resultScreen.setScreenTitle("Result Screen");
			resultScreen.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
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
	public Map<String, String> payOrder(int amount, String contents, String cardNumber, String cardHolderName,
			String expirationDate, String securityCode) {
		Map<String, String> result = new Hashtable<String, String>();
		result.put("RESULT", "PAYMENT FAILED!");
		try {
			this.card = new CreditCard(cardNumber, cardHolderName, Integer.parseInt(securityCode),
					getExpirationDate(expirationDate));

			this.interbank = new InterbankSubsystem();
			PaymentTransaction transaction = interbank.payOrder(card, amount, contents);

			result.put("RESULT", "PAYMENT SUCCESSFUL!");
			result.put("MESSAGE", "You have succesffully paid the order!");
		} catch (PaymentException | UnrecognizedException ex) {
			result.put("MESSAGE", ex.getMessage());
		}
		return result;
	}

	public void emptyCart(){
        Cart.getCart().emptyCart();
    }
}
