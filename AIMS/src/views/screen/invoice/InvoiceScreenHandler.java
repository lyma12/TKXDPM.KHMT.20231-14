package views.screen.invoice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import controller.PlaceOrderController;
import entity.invoice.Invoice;
import entity.order.order;
import entity.order.orderMedia;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.configs;
import utils.utils;
import view.screen.BaseScreenHandler;
import views.screen.invoice.MediaHandler;

public class InvoiceScreenHandler extends BaseScreenHandler {
	private static Logger LOGGER = utils.getLogger(InvoiceScreenHandler.class.getName());
	protected Text invoice_name;
	protected Text invoice_phone;
	protected Text invoice_province;
	protected Text invoice_intructions;
	protected Text invoice_subtotal;
	protected Text invoice_shipping_fees;
	protected Text invoice_total;
	protected Button btn_return;
	protected Text shipping_note;
	protected Button btn_confirm;
	protected ListView<MediaHandler> list_view;
	private HashMap<String, String> messages;
	private order order;
	private Invoice invoice;
	
	public InvoiceScreenHandler(Stage stage, String screenPath, order order, Invoice invoice) throws IOException {
		super(stage, screenPath);
		this.invoice_name = (Text) this.content.lookup("#invoice_name");
		this.invoice_phone = (Text) this.content.lookup("#invoice_phone");
		this.invoice_intructions = (Text) this.content.lookup("#invoice_intructions");
		this.invoice_province = (Text) this.content.lookup("#invoice_province");
		this.invoice_subtotal = (Text) this.content.lookup("#invoice_subtotal");
		this.invoice_shipping_fees = (Text) this.content.lookup("#invoice_shipping_fees");
		this.invoice_total = (Text) this.content.lookup("#invoice_total");
		this.btn_return = (Button) this.content.lookup("#btn_return");
		this.list_view = (ListView<MediaHandler>) this.content.lookup("#list_view");
		this.shipping_note = (Text) this.content.lookup("#shipping_note");
		this.btn_confirm = (Button) this.content.lookup("#btn_confirm");
		this.messages = order.getDeliveryInfo();
		this.order = order;
		this.invoice = invoice;
		setInvoice();
	}
	
	private void setInvoice() {
		this.invoice_name.setText(this.messages.get("name"));
		this.invoice_phone.setText(this.messages.get("phone"));
		this.invoice_province.setText(this.messages.get("province"));
		String tmp = "";
		if(this.messages.get("instructions") != null && this.messages.get("instructions") != "") tmp += this.messages.get("instructions") + ".";
		if(this.messages.get("rush_order") == "true") tmp += this.messages.get("district");
		this.invoice_intructions.setText(tmp);
		int subtotal = this.order.getAmount();
		int shipping_fees = this.order.getShippingFees();
		int total = subtotal + shipping_fees;
		this.invoice.setAmount(total);
		this.invoice_subtotal.setText(utils.getCurrencyFormat(subtotal));
		this.invoice_shipping_fees.setText(utils.getCurrencyFormat(shipping_fees));
		this.invoice_total.setText(utils.getCurrencyFormat(total));
		this.btn_confirm.setOnMouseClicked(e -> this.confirmInvoice());
		this.btn_return.setOnMouseClicked(e ->{
			this.getPreviousScreen().show();
		});
		try {
			List<orderMedia> lstMedia = this.order.getlstOrderMedia();
			for(orderMedia item: lstMedia) {
				boolean rush_order = false;
				if(this.order.getLstOrderMediaRushOrder().contains(item)) rush_order = true;
				MediaHandler invoiceItem = new MediaHandler(configs.INVOICE_MEDIA_SCREEN_PATH, item, rush_order);
				this.list_view.getItems().add(invoiceItem);
			}
			this.list_view.setCellFactory(
					listView -> new ListCell<MediaHandler>() {
						public void updateItem(MediaHandler item, boolean empty) {
							super.updateItem(item, empty);
							if(item != null || !empty) {
								setGraphic(item.getContent());
							}
							else {
								setGraphic(null);
							}
						}
					}
					);
		}catch(IOException e) {
			e.printStackTrace();
		}
		if(this.messages.get("rush_order") == "true") {
			String note = "Giao Nhanh: " + this.messages.get("hour") + ":" + this.messages.get("minute") + " " + this.messages.get("AMP");
			this.shipping_note.setVisible(true);
			this.shipping_note.setText(note);
		}
		else this.shipping_note.setVisible(false);

		
	}
	@Override
	public PlaceOrderController getBController() {
		return (PlaceOrderController) super.getBController();
	}
	public void confirmInvoice() {
		this.getBController().confirmInvoice(this);
	}
	public Invoice getInvoice() {
		return this.invoice;
	}
	public order getOrder() {
		return this.order;
	}

}
