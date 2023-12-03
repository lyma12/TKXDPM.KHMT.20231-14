package VNPaySubsystem;

import InterBankingInterface.InterBankingInterface;
import entity.payment.Card;
import entity.payment.PaymentTransaction;

public class VNPaySubsystem implements InterBankingInterface {
	
	private VNPayBoundary vnpBoundary;
	private VNPaySubsystemController vnpController;
	
	public VNPaySubsystem() {
		super();
		this.vnpBoundary = new VNPayBoundary();
		this.vnpController = new VNPaySubsystemController(this.vnpBoundary);
		
	}

	@Override
	public PaymentTransaction payOrder(Card card, int mount, String contents) {
		PaymentTransaction paymentTransaction = this.vnpController.payOrder(card, mount, contents);
		return paymentTransaction;
	}
	
	
}
