package VNPaySubsystem;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.logging.Logger;

import controller.PaymentController;
import entity.payment.Card;
import utils.configs;
import utils.utils;

public class Request {
	private static Logger LOGGER = utils.getLogger(Request.class.getName());
	private Card card;
	private int mount;
	private String contents;
	public Request(Card card, int mount, String contents) {
		this.card = card;
		this.mount = mount;
		this.contents = contents;
	}
	
	protected String generalURL() {
		String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "Thanh toan hang hoa";
        String orderType = "140000";
        String vnp_TxnRef = configs.getRandomNumber(8);
        String vnp_IpAddr = null;
        
		try {
			vnp_IpAddr = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        String vnp_TmnCode = configs.vnp_TmnCode;
        String locate = "vn";
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(this.mount*100*1000));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        
        vnp_Params.put("vnp_Locale", locate);
        vnp_Params.put("vnp_ReturnUrl", "ketquatrave");
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        //Add Params of 2.1.0 Version
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        LOGGER.info(fieldNames.toString());
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
					hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
					query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
	                query.append('=');
	                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				} catch (UnsupportedEncodingException e) {
					LOGGER.info(e.getMessage());
					e.printStackTrace();
				}
                //Build query
                 
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
            else {
            	LOGGER.info("thieu: " + fieldName);
            }
        }
        
        String queryUrl = query.toString();
        String vnp_SecureHash = configs.hmacSHA512(configs.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = configs.vnp_PayUrl + "?" + queryUrl;
        LOGGER.info(paymentUrl);
        return paymentUrl;
	}
}
