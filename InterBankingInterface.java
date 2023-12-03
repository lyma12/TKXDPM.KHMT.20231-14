package InterBankingInterface;

import entity.payment.Card;
import entity.payment.PaymentTransaction;

public interface InterBankingInterface {
	public PaymentTransaction payOrder(Card card, int mount, String contents);

}
