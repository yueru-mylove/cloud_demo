package com.miracle.cloud.encrypt;


import com.miracle.cloud.collection.ArrayUtil;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.List;


/**
 * sxyK5Ltm+zRCMrR/IICe17eHAOfeNjR3AWMYHxmWo50OEF3ln6C0PQSco5Y+k0I+lz7HA3YXuPQQcQH/iLevh4PQDk44fIR2JKPvfjGnPEEdhEb/G+XXgo2jy0Hzkth1FdtH2yzM8A86u5HD1aEKy+gasD81x/gFZ25BVqkEpSw=
 * <p>
 * {"deviceid":"4419BEA4-EE87-4F41-8EB7-C02E12D36030","code":"ainbsy"}
 */


/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月07日 18:03:00
 */
public class RsaEncryptUtil {

    private static final String RSA_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    private static final int KEY_SIZE = 1024;
    private static final String SEPARATOR = "[*\\-+;$]";
    private static final Integer DATA_LENGTH = KEY_SIZE / 8 - 11;


    private static byte[] publicKey;
    private static byte[] privateKey;


    public static String encrypt(String content, String filePath) throws Exception {
        byte[] data = content.getBytes();
        List<byte[]> result = ArrayUtil.sliceArray(data, DATA_LENGTH);
        PublicKey key = getPublicKey(filePath);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypt = null;
        int fromIndex = 0;
        for (byte[] bytes : result) {
            cipher.update(bytes);
            try {
                byte[] bytes1 = cipher.doFinal(bytes);
                encrypt = Arrays.copyOfRange(bytes1, fromIndex, fromIndex + DATA_LENGTH);
                fromIndex += DATA_LENGTH;
            } catch (IllegalBlockSizeException | BadPaddingException e) {
                e.printStackTrace();
            }
        }
        return new String(encrypt);
    }


    /**
     * 从pem文件中读取公玥
     *
     * @param filePath 公玥文件路径
     * @return PublicKey
     * @throws Exception Exception
     */
    public static PublicKey getPublicKey(String filePath) throws Exception {
        return (PublicKey) readKeyFromPem(filePath, false);
    }


    /**
     * 从pem文件中读取privatekey
     *
     * @param filePath pem文件路径
     * @return PrivateKey
     * @throws Exception exception
     */
    public static PrivateKey getPrivateKeyFromPem(String filePath) throws Exception {
        return (PrivateKey) readKeyFromPem(filePath, true);
    }


    /**
     * 从pem文件中读取key，注意key必须为pkcs8格式，如果是pkcs1格式，则需要使用openssl转换为pkcs8格式
     *
     * @param filePath 文件路径：注意springboot加载资源文件的方式，使用流文件的方式加载文件。
     * @return PublicKey - PrivateKey
     * @throws Exception exception
     */
    public static Key readKeyFromPem(String filePath, Boolean isPrivate) throws Exception {
        InputStream inputStream = RsaEncryptUtil.class.getClassLoader().getResourceAsStream(filePath);
        if (null == inputStream) {
            return null;
        }
        PemReader pemReader = new PemReader(new InputStreamReader(inputStream));
        try {
            PemObject pemObject = pemReader.readPemObject();
            byte[] key = pemObject.getContent();
            KeyFactory instance = KeyFactory.getInstance(RSA_ALGORITHM);
            if (isPrivate) {
                PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
                return instance.generatePrivate(pkcs8EncodedKeySpec);
            } else {
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(key);
                return instance.generatePublic(x509EncodedKeySpec);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 由给定的私玥对指定数据进行解密
     *
     * @param data     需要解密的数据
     * @param filePath 私玥文件位置
     * @return byte[]
     * @throws Exception Exception
     */
    public static byte[] decryptWithPrivateKey(byte[] data, String filePath) throws Exception {
        PrivateKey key = getPrivateKeyFromPem(filePath);
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }


    /**
     * 私玥解密，指定私玥文件位置
     *
     * @param content  要解密内容
     * @param filePath 私玥文件位置
     * @return String
     * @throws Exception Exception
     */
    public static String decryptWithPrivateKey(String content, String filePath) throws Exception {
        return decryptWithPrivateKey(content, getPrivateKeyFromPem(filePath));
    }


    /**
     * 私玥解密，指定私玥
     *
     * @param content 要解密内容
     * @param key     私玥
     * @return String，明文
     * @throws Exception Exception
     */
    public static String decryptWithPrivateKey(String content, PrivateKey key) throws Exception {
        // KeyFactory factory = KeyFactory.getInstance(RSA_ALGORITHM);
        Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(Base64.encodeBase64(cipher.doFinal(content.getBytes())));
    }


    /**
     * 私玥解密
     *
     * @param content 要解密的数据
     * @return string
     * @throws Exception Exception
     */
    public static String decryptWithPrivateKey(String content) throws Exception {
        return new String(decryptWithPrivateKey(content.getBytes()));
    }


    /**
     * 私玥解密
     *
     * @param data 要解密的数据
     * @return byte[]
     * @throws Exception Exception
     */
    public static byte[] decryptWithPrivateKey(byte[] data) throws Exception {
        PrivateKey key = getPrivateKey();
        KeyFactory factory = KeyFactory.getInstance(RSA_ALGORITHM);

        Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }


    /**
     * 公玥 解密
     *
     * @param content 要解密的数据
     * @return String
     * @throws Exception Exception
     */
    public static String decryptWithPublicKey(String content) throws Exception {
        return new String(decryptWithPublicKey(content.getBytes()));
    }


    /**
     * 公玥解密
     *
     * @param data 要解密的数据
     * @return byte[]
     * @throws Exception Exception
     */
    public static byte[] decryptWithPublicKey(byte[] data) throws Exception {
        PublicKey pubKey = getPublicKey();
        KeyFactory factory = KeyFactory.getInstance(RSA_ALGORITHM);

        Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }

    /**
     * 私玥加密
     *
     * @param content 加密内容
     * @return string
     * @throws Exception exception
     */
    public static String encryptWithPrivateKey(String content) throws Exception {
        return Base64.encodeBase64String(encryptWithPrivateKey(content.getBytes()));
    }


    /**
     * 私玥加密
     *
     * @param data 待加密数据
     * @return byte[]
     * @throws Exception exception
     */
    public static byte[] encryptWithPrivateKey(byte[] data) throws Exception {

        PrivateKey key = getPrivateKey();
        KeyFactory factory = KeyFactory.getInstance(RSA_ALGORITHM);
        Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipher.update(data);
        return cipher.doFinal(data);
    }


    /**
     * 公钥加密
     *
     * @param content 要加密的数据
     * @return string
     * @throws Exception Exception
     */
    public static String encryptWithPublicKey(String content) throws Exception {
        return Base64.encodeBase64String(encryptWithPublicKey(content.getBytes()));
    }


    /**
     * 公钥加密
     *
     * @param data 数据
     * @return byte[]
     * @throws Exception exception
     */
    public static byte[] encryptWithPublicKey(byte[] data) throws Exception {
        PublicKey pubKey = getPublicKey();
        KeyFactory factory = KeyFactory.getInstance(RSA_ALGORITHM);
        Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }


    /**
     * 验签
     *
     * @param data 数据
     * @param sign 数字签名
     * @return boolean
     * @throws Exception exception
     */
    public static boolean verify(byte[] data, byte[] sign) throws Exception {
        if (null == privateKey || null == publicKey) {
            initKey();
        }

        KeyFactory instance = KeyFactory.getInstance(RSA_ALGORITHM);

        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
        PublicKey publicKey = instance.generatePublic(x509EncodedKeySpec);
        // 初始化签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }


    /**
     * 签名
     *
     * @param data 数据
     * @return string
     * @throws Exception exception
     */
    public static String sign(byte[] data) throws Exception {
        PrivateKey priKey = getPrivateKey();
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        // 签名
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 初始化密钥对
     *
     * @throws NoSuchAlgorithmException 算法异常
     */
    private static void initKey() throws NoSuchAlgorithmException {
        KeyPairGenerator instance = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        instance.initialize(KEY_SIZE);
        KeyPair keyPair = instance.generateKeyPair();
        privateKey = keyPair.getPrivate().getEncoded();
        publicKey = keyPair.getPublic().getEncoded();
    }


    private static PublicKey getPublicKey() throws Exception {
        if (null == publicKey || null == privateKey) {
            initKey();
        }
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory factory = KeyFactory.getInstance(RSA_ALGORITHM);
        return factory.generatePublic(x509EncodedKeySpec);
    }


    /**
     * 获取公玥
     *
     * @return PrivateKey
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     */
    private static PrivateKey getPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (null == privateKey || null == publicKey) {
            initKey();
        }
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory factory = KeyFactory.getInstance(RSA_ALGORITHM);
        return factory.generatePrivate(pkcs8EncodedKeySpec);
    }

    public static void main(String[] args) {
        String encrypt = "{\"deviceid\":\"4419BEA4-EE87-4F41-8EB7-C02E12D36030\",\"code\":\"ainbsy\"}";
        try {
            String encrypt1 = encrypt(encrypt, "PublicKey.pem");
            System.out.println(encrypt1);
        } catch (Exception e) {
            e.printStackTrace();
        }



/*        String content = "sxyK5Ltm+zRCMrR/IICe17eHAOfeNjR3AWMYHxmWo50OEF3ln6C0PQSco5Y+k0I+lz7HA3YXuPQQcQH/iLevh4PQDk44fIR2JKPvfjGnPEEdhEb/G+XXgo2jy0Hzkth1FdtH2yzM8A86u5HD1aEKy+gasD81x/gFZ25BVqkEpSw=";
        byte[] result = null;
        int fromIndex = 0;
        String[] split = content.split(SEPARATOR);
        for (String s : split) {
            System.out.println(s);
            byte[] decode = Base64.decodeBase64(s);
            try {
                Cipher instance = Cipher.getInstance("RSA/ECB/NoPadding", new BouncyCastleProvider());
                instance.init(Cipher.DECRYPT_MODE, getPrivateKeyFromPem("pkcs8.pem"));
                byte[] bytes = instance.doFinal(decode);
                result = Arrays.copyOf(bytes, fromIndex);
                fromIndex += bytes.length;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(new String(result));*/


        /*try {
            initKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/
        // PrivateKey privateKey = getPrivateKey();
        // System.out.println(privateKey);

    }


}


