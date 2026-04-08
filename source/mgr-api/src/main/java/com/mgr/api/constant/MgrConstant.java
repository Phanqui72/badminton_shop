package com.mgr.api.constant;

public class MgrConstant {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static final Integer USER_KIND_ADMIN = 1;
    public static final Integer USER_KIND_USER = 2;
    public static final Integer USER_KIND_SELLER = 3;

    public static final Integer STATUS_ACTIVE = 1;
    public static final Integer STATUS_PENDING = 0;
    public static final Integer STATUS_LOCK = -1;
    public static final Integer STATUS_DELETE = -2;

    //Gender type
    public static final Integer GENDER_TYPE_MALE = 0;
    public static final Integer GENDER_TYPE_FEMALE = 1;
    public static final Integer GENDER_TYPE_OTHER = -1;

    // Trạng thái đơn hàng (Order Status)
    public static final Integer ORDER_STATUS_PENDING = 0;
    public static final Integer ORDER_STATUS_PAID = 1;
    public static final Integer ORDER_STATUS_FAILED = -1;

    // Phương thức thanh toán (Payment Method)
    public static final Integer PAYMENT_METHOD_COD = 1;
    public static final Integer PAYMENT_METHOD_VNPAY = 2;

    // Cấu hình VNPAY (VNPAY Internal Constants)
    public static final String VNP_VERSION = "2.1.0";
    public static final String VNP_COMMAND_PAY = "pay";
    public static final String VNP_CURRENCY_VND = "VND";
    public static final String VNP_ORDER_TYPE_OTHER = "other";
    public static final String VNP_LOCALE_VN = "vn";
    public static final String VNP_DEFAULT_IP = "127.0.0.1";

    // Response Code từ VNPAY
    public static final String VNP_RESPONSE_FINISHED = "00";

    //TOTAL PRICE ORDER DEFAULT
    public static final Double TOTAL_PRICE_DEFAULT = 0.0;


    private MgrConstant() {
        throw new IllegalStateException("Utility class");
    }
}
