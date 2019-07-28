package com.example.zuulserver.commons;


import java.security.MessageDigest;

public class MD5Utils {
    private static final String[] hexDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String byteToHexString(byte b) {
        int n = b;
        if (b < 0) {
            n = b + 256;
        }

        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();

        for(int i = 0; i < b.length; ++i) {
            resultSb.append(byteToHexString(b[i]));
        }

        return resultSb.toString();
    }
    public static String MD5Encode(String origin,String salt) {
        if(StringUtils.isNotBlank(salt)){
            origin = origin+salt;
        }
        origin = origin.trim();
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
        } catch (Exception var3) {
            ;
        }

        return resultString;
    }

    public static void main(String[] args) {
        String str = "{\"name\":\"cc\"}";
        System.out.println(MD5Encode(str,SaltEnum.MD5_SALT.getCode()));
    }
}
