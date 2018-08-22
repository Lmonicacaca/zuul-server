package com.example.zuulserver.rsa;

import com.example.zuulserver.commons.Config;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Decrypt {
    public static String decrypt(String key,String iv,String cipher,String private_key)throws Exception{
        //解码key
        byte[] keyBytes = Base64.getDecoder().decode(key);
        //用RSA私钥解密AES的密钥
        byte[] privateBytes = Base64.getDecoder().decode(private_key);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Config.RSA);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher2 = Cipher.getInstance(Config.RSA);
        cipher2.init(Cipher.DECRYPT_MODE,privateKey);
        byte[] aesKey = cipher2.doFinal(keyBytes);

        //解码iv
        byte[] ivBytes = Base64.getDecoder().decode(iv);
        //解码加密数据
        byte[] cipherBytes = Base64.getDecoder().decode(cipher);

        //使用AES密钥和iv解密数据
        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
        Cipher cipher3 = Cipher.getInstance(Config.AES_CBC_PKCS5Padding);
        cipher3.init(Cipher.DECRYPT_MODE,new SecretKeySpec(aesKey,Config.AES),ivParameterSpec);
        byte[] result = cipher3.doFinal(cipherBytes);
        String resultString = new String(result, "UTF-8");
        return resultString;
    }
}
