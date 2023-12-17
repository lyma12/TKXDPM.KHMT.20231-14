package VNPaySubsystem;

import java.util.logging.Logger;

import entity.payment.Card;
import entity.payment.PaymentTransaction;
import utils.utils;

public class VNPaySubsystemController {
	private static Logger LOGGER = utils.getLogger(VNPaySubsystemController.class.getName());

	private Request request;
	private String responseString;
	private Response response;
	private String url;
	private VNPayBoundary vnpBoudary;
	private PaymentTransaction paymentTransaction;
	
	// communicational cohesion
	public VNPaySubsystemController(VNPayBoundary vnp) {
		super();
		this.vnpBoudary = vnp;
	}
	
	protected PaymentTransaction payOrder(Card card, int mount, String contents) {
		this.request = new Request(card, mount, contents);
		this.url = request.generalURL();
		this.responseString = this.vnpBoudary.query(url);
		this.response = new Response(this.responseString);
		this.paymentTransaction = this.response.handleResponse();
		return this.paymentTransaction;
	}
	
}
