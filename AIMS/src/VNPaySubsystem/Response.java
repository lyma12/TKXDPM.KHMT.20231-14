package VNPaySubsystem;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import common.exception.GatewayTimeOutException;
import common.exception.InternalServerErrorException;
import common.exception.InvalidDataErrorException;
import common.exception.NotEnoughBalanceException;
import entity.payment.PaymentTransaction;
import utils.utils;

public class Response {
	private static Logger LOGGER = utils.getLogger(Response.class.getName());
	private String contents;
	Map<String, String> responseValue;
	protected Response(String contents) {
		this.contents = contents;
		this.responseValue = new LinkedHashMap<String, String>();
		//handleResponse();
	}
	protected PaymentTransaction handleResponse() throws InvalidDataErrorException, GatewayTimeOutException, NotEnoughBalanceException, InternalServerErrorException  {
		// handlerErrorCode
		String tmp = this.contents.substring(this.contents.indexOf("ketquatrave?")+12);
		
		for(String keyValue : tmp.split("&")) {
		   String[] pairs = keyValue.split("=",2);
		   responseValue.put(pairs[0], pairs[1]);
		}
		LOGGER.info(responseValue.toString());
		System.out.print(responseValue.get("vnp_ResponseCode"));
		//return 
		if(responseValue.get("vnp_ResponseCode") == null) {
			LOGGER.info("return null");
			throw new GatewayTimeOutException();
		}
		if (responseValue.get("vnp_ResponseCode").compareTo("24") == 0) {
			LOGGER.info("InvalidDataErrorException");
			throw new InvalidDataErrorException("Khách hàng hủy giao dịch!");
		}
		else
		if (responseValue.get("vnp_ResponseCode").compareTo("51") == 0) {
			LOGGER.info("NotEnoughBalanceException");
			throw new NotEnoughBalanceException("Tài khoản của quý khách không đủ số dư để thực hiện giao dịch!");
		}
		else
		if (responseValue.get("vnp_ResponseCode").compareTo("12") == 0) {
			LOGGER.info("NotEnoughBalanceException");
			throw new InvalidDataErrorException("Thẻ/tài khoản của khách hàng bị khóa");
		}
		else
		if (responseValue.get("vnp_ResponseCode").compareTo("13") == 0) {
			LOGGER.info("NotEnoughBalanceException");
			throw new InvalidDataErrorException("Khách hàng nhập sai mật khẩu xác thực giao dịch!");
		}
		else
		if (responseValue.get("vnp_ResponseCode").compareTo("65") == 0) {
			LOGGER.info("NotEnoughBalanceException");
			throw new InternalServerErrorException("Tài khoản khách hàng đã vượt quá hạn mức giao dịch trong ngày!");
		}
		else
		if (responseValue.get("vnp_ResponseCode").compareTo("75") == 0) {
			LOGGER.info("NotEnoughBalanceException");
			throw new InternalServerErrorException("Ngân hàng thanh toán đang bảo trì!");
		}else
		if (responseValue.get("vnp_ResponseCode").compareTo("99") == 0) {
			LOGGER.info("NotEnoughBalanceException");
			throw new InternalServerErrorException("Giao dịch không thanh công");
		}else
		if (responseValue.get("vnp_ResponseCode").compareTo("79") == 0) {
			LOGGER.info("NotEnoughBalanceException");
			throw new InternalServerErrorException("Khách hàng nhập sai mật khẩu thanh toán quá số lần quy định!");
		}
		
		return new PaymentTransaction(Integer.parseInt(responseValue.get("vnp_TransactionNo")), responseValue.get("vnp_BankCode"),responseValue.get("vnp_TransactionNo"),1,
				responseValue.get("vnp_PayDate"),responseValue.get("vnp_OrderInfo"),Integer.parseInt(responseValue.get("vnp_ResponseCode")),
				Integer.parseInt(responseValue.get("vnp_TransactionStatus")),responseValue.get("vnp_TxnRef"),responseValue.get("vnp_SecureHash"),
				responseValue.get("vnp_SecureHash"));
	}
	
}
