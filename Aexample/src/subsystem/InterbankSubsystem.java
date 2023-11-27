package subsystem;

import common.exception.InternalServerErrorException;
import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;
import subsystem.interbank.InterbankSubsystemController;


public class InterbankSubsystem implements InterbankInterface {


	private InterbankSubsystemController ctrl;


	public InterbankSubsystem() {
		String str = new String();
		this.ctrl = new InterbankSubsystemController();
	}


	public PaymentTransaction payOrder(CreditCard card, int amount, String contents) {
		PaymentTransaction transaction = ctrl.payOrder(card, amount, contents);
		return transaction;
	}


	public PaymentTransaction refund(CreditCard card, int amount, String contents) {
		PaymentTransaction transaction = ctrl.refund(card, amount, contents);
		return transaction;
	}
}
