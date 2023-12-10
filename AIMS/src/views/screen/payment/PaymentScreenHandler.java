package views.screen.payment;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import controller.PaymentController;
import entity.invoice.Invoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.configs;
import views.screen.BaseScreenHandler;

public class PaymentScreenHandler extends BaseScreenHandler {

	private Button btnConfirmPayment;
	private Invoice invoice;
	private Text pageTitle;
	private TextField cardNumber;
	private TextField holderName;
	private TextField expirationDate;
	private TextField securityCode;
	public PaymentScreenHandler(Stage stage, String screenPath) throws IOException {
		super(stage, screenPath);
		this.btnConfirmPayment = (Button) this.content.lookup("#btnConfirmPayment");
		this.pageTitle = (Text) this.content.lookup("#pageTitle");
		this.cardNumber = (TextField) this.content.lookup("#cardNumber");
		this.holderName = (TextField) this.content.lookup("#holderName");
		this.expirationDate = (TextField) this.content.lookup("#expirationDate");
		this.securityCode = (TextField) this.content.lookup("#securityCode");
	}
	public PaymentScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice = invoice;
		this.btnConfirmPayment = (Button) this.content.lookup("#btnConfirmPayment");
		this.pageTitle = (Text) this.content.lookup("#pageTitle");
		this.cardNumber = (TextField) this.content.lookup("#cardNumber");
		this.holderName = (TextField) this.content.lookup("#holderName");
		this.expirationDate = (TextField) this.content.lookup("#expirationDate");
		this.securityCode = (TextField) this.content.lookup("#securityCode");
		btnConfirmPayment.setOnMouseClicked(e -> {
			try {
				confirmToPayOrder();
			} catch (Exception exp) {
				System.out.println(exp.getStackTrace());
			}
		});
	}
	@Override
	public PaymentController getBController() {
		return (PaymentController) super.getBController();
	}
	void confirmToPayOrder() throws IOException{
		String contents = "pay order";
		Map<String, String> response = new Hashtable<String, String>();
		response.put("contents", contents);
		response.put("cardNumber", this.cardNumber.getText());
		response.put("cardHolderName", this.holderName.getText());
		response.put("expirationDate", this.expirationDate.getText());
		response.put("securityCode", this.securityCode.getText());
		response = this.getBController().processOrderPayMent(response);
		
		BaseScreenHandler resultScreen = new ResultScreenHandler(this.stage, configs.RESULT_SCREEN_PATH, response.get("RESULT"), response.get("MESSAGE") );
		resultScreen.setPreviousScreen(this);
		resultScreen.setHomeScreenHandler(homeScreenHandler);
		resultScreen.setScreenTitle("Result Screen");
		resultScreen.show();
	}
}
