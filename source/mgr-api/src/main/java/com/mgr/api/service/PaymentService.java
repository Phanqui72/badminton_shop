package com.mgr.api.service;

import com.mgr.api.constant.MgrConstant;
import com.mgr.api.config.VNPayConfig; // Hoặc VNPayUtils nếu bạn đã đổi tên
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentService {
    @Value("${vnp_TmnCode}")
    private String vnp_TmnCode;

    @Value("${vnp_HashSecret}")
    private String vnp_HashSecret;

    @Value("${vnp_PayUrl}")
    private String vnp_Url;

    @Value("${vnp_ReturnUrl}")
    private String vnp_ReturnUrl;

    public String createPaymentUrl(long amount, String txnRef) throws UnsupportedEncodingException {
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", MgrConstant.VNP_VERSION);
        vnp_Params.put("vnp_Command", MgrConstant.VNP_COMMAND_PAY);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", MgrConstant.VNP_CURRENCY_VND);
        vnp_Params.put("vnp_TxnRef", txnRef);
        vnp_Params.put("vnp_OrderInfo", "ThanhToanDonHang" + txnRef);
        vnp_Params.put("vnp_OrderType", MgrConstant.VNP_ORDER_TYPE_OTHER);
        vnp_Params.put("vnp_Locale", MgrConstant.VNP_LOCALE_VN);
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", MgrConstant.VNP_DEFAULT_IP);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        vnp_Params.put("vnp_CreateDate", new SimpleDateFormat("yyyyMMddHHmmss").format(cld.getTime()));

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                String encodedValue = URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString());
                hashData.append(fieldName).append('=').append(encodedValue);
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()))
                        .append('=').append(encodedValue);
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String vnp_SecureHash = VNPayConfig.hmacSHA512(vnp_HashSecret, hashData.toString());
        return vnp_Url + "?" + query.toString() + "&vnp_SecureHash=" + vnp_SecureHash;
    }
}