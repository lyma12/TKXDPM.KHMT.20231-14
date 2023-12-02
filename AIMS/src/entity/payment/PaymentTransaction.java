package entity.payment;

public class PaymentTransaction {
	private int ID;
	private int transactionCode;
	private String bankCode;
	private String bankTranNo;
	private int cardType;
	private String payDate;
	private String orderInfo;
	private int responseCode;
	private int transactionStatus;
	private String txnRef;
	private String SecureHashType;
	private String SecureHash;
	public PaymentTransaction(int transactionCode, String bankCode, String bankTranNo, int cardType,
			String payDate, String orderInfo, int responseCode, int transactionStatus, String txnRef,
			String secureHashType, String secureHash) {
		super();
		this.transactionCode = transactionCode;
		this.bankCode = bankCode;
		this.bankTranNo = bankTranNo;
		this.cardType = cardType;
		this.payDate = payDate;
		this.orderInfo = orderInfo;
		this.responseCode = responseCode;
		this.transactionStatus = transactionStatus;
		this.txnRef = txnRef;
		SecureHashType = secureHashType;
		SecureHash = secureHash;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
}
