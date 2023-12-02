package entity.payment;

public class Card {
	private String cardCode;
	private String owner;
	private String cvvCode;
	private String dateExpired;

	public Card(String cardCode, String owner, String cvvCode, String dateExpired) {
		super();
		this.cardCode = cardCode;
		this.owner = owner;
		this.cvvCode = cvvCode;
		this.dateExpired = dateExpired;
	}

}
