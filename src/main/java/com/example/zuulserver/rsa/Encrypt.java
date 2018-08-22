package com.example.zuulserver.rsa;

import com.example.zuulserver.commons.Config;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Encrypt {
    public static Map<String,String> encrypt(String body, String public_key) throws Exception{
        //随机生成AES密钥
        KeyGenerator keyGenerator = KeyGenerator.getInstance(Config.AES);
        keyGenerator.init(256,new SecureRandom());//指定随机密钥的长度
        SecretKey secretKey = keyGenerator.generateKey();
        //AES密钥，用于加密数据
        byte[] aesKey = secretKey.getEncoded();
        //用RSA公钥加密AES的密钥
        byte[] public_key_bytes = Base64.getDecoder().decode(public_key);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(public_key_bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Config.RSA);
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance(Config.RSA);
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        byte[] aseKeyEncrypt = cipher.doFinal(aesKey);
        String keyString = Base64.getEncoder().encodeToString(aseKeyEncrypt);


        //生成随机iv
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<16;i++){
            stringBuffer.append(Integer.toHexString(new Random().nextInt(16)));
        }
        byte[] iv = stringBuffer.toString().getBytes();
        String ivString = Base64.getEncoder().encodeToString(iv);

        //使用AES密钥和随机iv对数据进行加密

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        Cipher cipher1 = Cipher.getInstance(Config.AES_CBC_PKCS5Padding);
        cipher1.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(aesKey,Config.AES),ivParameterSpec);
        byte[] content = cipher1.doFinal(body.getBytes("UTF-8"));
        String cipherString = Base64.getEncoder().encodeToString(content);

        Map<String,String> map = new HashMap<>();
        map.put("cipher",cipherString);
        map.put("iv",ivString);
        map.put("key",keyString);
        return map;
    }
}
