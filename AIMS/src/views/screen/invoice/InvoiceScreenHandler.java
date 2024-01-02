/*
    Coupling trong class InvoiceScreenHandler:
	- class Invoice: tương tác với đối tượng của lớp invoice thông qua truyền tham số và truy xuất thuộc tính
	- class order: tương tác với đối tượng của lớp order thông qua truyền tham số và truy xuất thuộc tính
	- class PlaceOrderController
	- class orderMedia: tương tác với đối tượng của lớp order thông qua truyền tham số và truy xuất thuộc tính
	- class MediaHandler: tương tác với đối tượng của lớp MediaHandler thông qua truyền tham số 
*/
package views.screen.invoice;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import controller.PlaceOrderController;
import entity.invoice.Invoice;
import entity.order.order;
import entity.order.orderMedia;
import entity.shipping.Shipment;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.configs;
import utils.utils;
import views.screen.BaseScreenHandler;
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
	protected Shipment shippingInfo;
	private Invoice invoice;     //coupling class invoice
	
	public InvoiceScreenHandler(Stage stage, String screenPath, Invoice invoice, Shipment shippingInfo) throws IOException {
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
		this.invoice = invoice;
		this.shippingInfo = shippingInfo;
		setInvoice();
	}
	
	private void setInvoice() {
		this.invoice_name.setText(this.shippingInfo.getName());
		this.invoice_phone.setText(this.shippingInfo.getPhone());
		this.invoice_province.setText(this.shippingInfo.getProvince());
		String tmp = "";
		if(this.shippingInfo.getInstructions() != null || this.shippingInfo.getInstructions() != "") tmp += this.shippingInfo.getInstructions() + ".";
		if(this.shippingInfo.getRushOrder()) tmp += this.shippingInfo.getAddress();
		this.invoice_intructions.setText(tmp);
		this.invoice_subtotal.setText(utils.getCurrencyFormat(this.invoice.getOrder().getAmount()));
		this.invoice_shipping_fees.setText(utils.getCurrencyFormat(this.invoice.getOrder().getShippingFees()));
		this.invoice_total.setText(utils.getCurrencyFormat(this.invoice.getAmount()));
		this.btn_confirm.setOnMouseClicked(e -> this.confirmInvoice());
		this.btn_return.setOnMouseClicked(e ->{
			this.getPreviousScreen().show();
		});
		try {
			List<orderMedia> lstMedia = this.invoice.getOrder().getlstOrderMedia();     //coupling class orderMedia
			for(orderMedia item: lstMedia) {
				boolean rush_order = false;
				if(this.invoice.getOrder().getLstOrderMediaRushOrder().contains(item)) rush_order = true;
				MediaHandler invoiceItem = new MediaHandler(configs.INVOICE_MEDIA_SCREEN_PATH, item, rush_order);
				this.list_view.getItems().add(invoiceItem);
			}
			this.list_view.setCellFactory(
					listView -> new ListCell<MediaHandler>() {
						public void updateItem(MediaHandler item, boolean empty) {     //coupling class MediaHandler
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
		if(this.shippingInfo.getRushOrder()) {
			String note = "Giao Nhanh: " + this.shippingInfo.getHours();
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
		this.getBController().confirmInvoice(this, this.invoice);
	}

}
