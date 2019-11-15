package com.miracle.cloud.encrypt;

import com.google.common.base.Strings;
import com.miracle.cloud.collection.BeanUtil;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月09日 21:07:00
 */
public class AESEncryptUtils {


    private static final String KEY = "alhd192U1U41UWHQTNH#u4u5#Y*KHSDFHKA(UFDU(AJF";
    private static final String AES_ALGORITHM = "AES";
    // 128 192 256
    private static final Integer DEFAULT_DATA_BITS = 128;


    /**
     * 加密
     *
     * @param content   要加密的内容
     * @param key   密钥
     * @return  byte[]
     * @throws Exception    exception
     */
    public static String encrypt(String content, String key) throws Exception {
        if (Strings.isNullOrEmpty(content)) {
            return null;
        }
        SecretKeySpec spec = getSecretKeySpec(key);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, spec);
        return ByteUtils.toHexString(cipher.doFinal(content.getBytes()));
    }


    /**
     * 解密
     *
     * @param data  密文
     * @param key   密钥
     * @return  明文
     * @throws Exception    exception
     */
    public static String decrypt(String data, String key) throws Exception {
        if (BeanUtil.isEmpty(data)) {
            return null;
        }

        SecretKeySpec secretKeySpec = getSecretKeySpec(key);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(ByteUtils.fromHexString(data)));
    }

    /**
     * 利用私玥生成key
     *
     * @param key   私玥
     * @return  key
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    private static SecretKeySpec getSecretKeySpec(String key) throws NoSuchAlgorithmException {
        if (Strings.isNullOrEmpty(key)) {
            key = KEY;
        }
        KeyGenerator generator = KeyGenerator.getInstance(AES_ALGORITHM);
        SecureRandom random = new SecureRandom(key.getBytes());
        generator.init(DEFAULT_DATA_BITS, random);
        SecretKey secretKey = generator.generateKey();
        return new SecretKeySpec(secretKey.getEncoded(), AES_ALGORITHM);
    }

    public static void main(String[] args) {

        try {
            String encrypt = encrypt("123456", KEY);
            String decrypt = decrypt(encrypt, KEY);
            System.out.println(decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
