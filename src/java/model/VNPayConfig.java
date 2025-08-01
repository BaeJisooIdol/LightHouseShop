/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author admin
 */

//Ngân hàng	NCB
//Số thẻ	9704198526191432198
//Tên chủ thẻ	NGUYEN VAN A
//Ngày phát hành	07/15
//Mật khẩu OTP	123456

public class VNPayConfig {
    public static final String VNP_URL = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html"; // URL sandbox
    public static final String VNP_RETURN_URL = "https://ad56-113-178-138-213.ngrok-free.app/lighthouse/transaction?action=payment"; // URL return
    public static final String VNP_TMN_CODE = ""; // Mã terminal của bạn SX40YEW5
    public static final String VNP_HASH_SECRET = ""; // Chuỗi bí mật 9H6NT2HF13DKVVGSGD5XKKLCQ4UL3732
    public static final String VNP_API_URL = "http://sandbox.vnpayment.vn/merchant_webapi/merchant.html"; // API
    
    public static String getIpAddress(HttpServletRequest request) {
        String ipAdress;
        try {
            ipAdress = request.getHeader("X-FORWARDED-FOR");
            if (ipAdress == null) {
                ipAdress = request.getRemoteAddr();
            }
        } catch (Exception e) {
            ipAdress = "Invalid IP:" + e.getMessage();
        }
        return ipAdress;
    }

    public static String getRandomNumber(int len) {
        Random rnd = new Random();
        String chars = "0123456789";
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }
}
