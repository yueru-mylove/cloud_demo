package com.voiceai.cloud.encrypt;

import com.google.common.base.Strings;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月09日 21:27:00
 */
public class DESEncryptUtil {


    private static final String KEY = "alhd192U1U41UWHQTNH#u4u5#Y*KHSDFHKA(UFDU(AJF";
    private static final String DES_ALGORITHM = "DES";
    // 128 192 256
    private static final Integer DEFAULT_DATA_BITS = 128;

    /**
     * 解密
     *
     * @param content   密文
     * @param key   私玥
     * @return  明文
     * @throws Exception    exception
     */
    public static String decrypt(String content, String key) throws Exception {
        byte[] bytes = Base64.decode(ByteUtils.fromHexString(content));
        SecretKey secretKey = getSecretKey(key);
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(bytes));
    }



    /**
     * 加密
     *
     * @param content   明文
     * @param key   私玥
     * @return  string
     * @throws Exception    exception
     */
    public static String encrypt(String content, String key) throws Exception {
        SecretKey secretKey = getSecretKey(key);
        Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return ByteUtils.toHexString(Base64.encode(cipher.doFinal(content.getBytes())));
    }


    private static SecretKey getSecretKey(String key) throws Exception {
        if (Strings.isNullOrEmpty(key)) {
            key = KEY;
        }
        DESKeySpec spec = new DESKeySpec(key.getBytes());
        SecretKeyFactory factory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        return factory.generateSecret(spec);
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
