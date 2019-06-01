package com.voiceai.cloud.encrypt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAUtils {

    //    public static String DEFAULT_privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOok3/jTCs/lto+5Q08nsFASeE+8hpXszh/kU6oRjmlmEmYrvrbFXr/OMd/8bZvfPE6sTfPA9y81F3kPcN7Zht5hx/XrUkOJPxKZHNUzzFu+VQzFpZbsGabyQDmUXk3ZIastvtpoC10xkE2tPRlF5UvYM159ylohWlObOsU2tsT7AgMBAAECgYBzpQzeR9YyQqH9sKI3CUJC+ixkLZhHmnNgOxS7jfpJwnlZ70c93JPkpkHqADJe505RCTdjKi6sKUvTpjW4S7YZVFdgHOQkQQzAhEYOpTMA3hBqq+P05FlZV8VB5CLNKbAVfIxECTUpg0WiK05J9LAsA0uWIQQaw1pTBoArTM8KuQJBAPUBxW5qoHlRAPX8u8KWX/uBoJXmNxrXWI94ybyfWArfRBQnwNENZ+k7aET1kSBb/5SQKDdSCLZx4l6DmG+iyJ8CQQD0plRD5WvdfNF7FiSRYPWXdax7jm2t23hxpJHU6LtrR146rLnVjpauk1EaGu6Y8MdupBAUMyY/bLjSNMay7HolAkAcP2wH4vtFgXVe5xiuwq+mS7e8EKLh0VpAkGiR+r0n473xpPOyJp2IYnUPHlDxLio1crlwR2EBx/eVdg6pMFd5AkARHLE4Lovk6h4c5eeY45dKCMkPu16gkoFSMZihs8WoM027/Ojirx6LB3LcyJV3zR+l9YPKZel+9jPXQCnFyhShAkEAvKhh3+TRKg6j6gI2wDzwMLJLcj0YacGP3pMuxJk/h3jFS2oDvA/T46GeapZ0uPCaZdYt7IzCJRutgtbxNn1LLg==";
    public static String DEFAULT_privatekey = "MIICXAIBAAKBgQDqJN/40wrP5baPuUNPJ7BQEnhPvIaV7M4f5FOqEY5pZhJmK762xV6/zjHf/G2b3zxOrE3zwPcvNRd5D3De2YbeYcf161JDiT8SmRzVM8xbvlUMxaWW7Bmm8kA5lF5N2SGrLb7aaAtdMZBNrT0ZReVL2DNefcpaIVpTmzrFNrbE+wIDAQABAoGAc6UM3kfWMkKh/bCiNwlCQvosZC2YR5pzYDsUu436ScJ5We9HPdyT5KZB6gAyXudOUQk3YyourClL06Y1uEu2GVRXYBzkJEEMwIRGDqUzAN4Qaqvj9ORZWVfFQeQizSmwFXyMRAk1KYNFoitOSfSwLANLliEEGsNaUwaAK0zPCrkCQQD1AcVuaqB5UQD1/LvCll/7gaCV5jca11iPeMm8n1gK30QUJ8DRDWfpO2hE9ZEgW/+UkCg3Ugi2ceJeg5hvosifAkEA9KZUQ+Vr3XzRexYkkWD1l3Wse45trdt4caSR1Oi7a0deOqy51Y6WrpNRGhrumPDHbqQQFDMmP2y40jTGsux6JQJAHD9sB+L7RYF1XucYrsKvpku3vBCi4dFaQJBokfq9J+O98aTzsiadiGJ1Dx5Q8S4qNXK5cEdhAcf3lXYOqTBXeQJAERyxOC6L5OoeHOXnmOOXSgjJD7teoJKBUjGYobPFqDNNu/zo4q8eiwdy3MiVd80fpfWDymXpfvYz10ApxcoUoQJBALyoYd/k0SoOo+oCNsA88DCyS3I9GGnBj96TLsSZP4d4xUtqA7wP0+OhnmqWdLjwmmXWLeyMwiUbrYLW8TZ9Sy4=";
    public static String DEFAULT_publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDqJN/40wrP5baPuUNPJ7BQEnhPvIaV7M4f5FOqEY5pZhJmK762xV6/zjHf/G2b3zxOrE3zwPcvNRd5D3De2YbeYcf161JDiT8SmRzVM8xbvlUMxaWW7Bmm8kA5lF5N2SGrLb7aaAtdMZBNrT0ZReVL2DNefcpaIVpTmzrFNrbE+wIDAQAB";


    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    public static final String RSA_ALGORITHM_ANDROID = "RSA/ECB/PKCS1Padding";

    public static String textsep = "*-+;$";
    public static String textsep__ = "\\*-\\+;\\$";

    public static Map<String, String> createKeys(int keySize) {
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM_ANDROID + "]");
        }

        //初始化KeyPairGenerator对象,密钥长度
        kpg.initialize(keySize);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = new BASE64Encoder().encode(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = new BASE64Encoder().encode(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);

        return keyPairMap;
    }

    /**
     * 得到公钥
     *
     * @param publicKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        //通过X509编码的Key指令获得公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
        return key;
    }

    /**
     * 得到私钥
     *
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(new BASE64Decoder().decodeBuffer(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        return key;
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_ANDROID);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return new BASE64Encoder().encode(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     */
    public static String publicEncryptEX(String data, RSAPublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_ANDROID);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return rsaSplitCodecEX(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), publicKey.getModulus().bitLength());
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecrypt(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_ANDROID);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, new BASE64Decoder().decodeBuffer(data), privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     */

    public static String privateDecryptEX(String data, RSAPrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM_ANDROID);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return rsaSplitCodecEX_f(cipher, Cipher.DECRYPT_MODE, data, privateKey.getModulus().bitLength());
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

//    /**
//     * 私钥加密
//     *
//     * @param data
//     * @param privateKey
//     * @return
//     */
//
//    public static String privateEncrypt(String data, RSAPrivateKey privateKey) {
//        try {
//            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
//            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
//            return new BASE64Encoder().encode(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
//        } catch (Exception e) {
//            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
//        }
//    }
//
//    /**
//     * 公钥解密
//     *
//     * @param data
//     * @param publicKey
//     * @return
//     */
//
//    public static String publicDecrypt(String data, RSAPublicKey publicKey) {
//        try {
//            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
//            cipher.init(Cipher.DECRYPT_MODE, publicKey);
//            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, new BASE64Decoder().decodeBuffer(data), publicKey.getModulus().bitLength()), CHARSET);
//        } catch (Exception e) {
//            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
//        }
//    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }

        int offSet = 0;
        byte[] buff;
        int i = 0;
        byte[] resultDatas = null;
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
            resultDatas = out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        return resultDatas;
    }

    private static String rsaSplitCodecEX(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        int offSet = 0;
        byte[] buff;
        int i = 0;
        StringBuilder resultDatas = new StringBuilder();
        int lendata = datas.length / maxBlock;
        if (datas.length % maxBlock > 0) {
            lendata++;
        }
        byte[][] rrr = new byte[lendata][];
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                rrr[i] = buff;
                i++;
                offSet = i * maxBlock;
            }

        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        for (int i1 = 0; i1 < rrr.length; i1++) {
            resultDatas.append(new BASE64Encoder().encode(rrr[i1]));
            resultDatas.append(textsep);
        }
        String retur = resultDatas.toString();
        if (retur.length() > textsep.length()) {
            retur = retur.substring(0, retur.length() - textsep.length());
        }
        return retur;
    }

    private static String rsaSplitCodecEX_f(Cipher cipher, int opmode, String dat, int keySize) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        int offSet = 0;
        byte[] buff;
        int i = 0;
        StringBuilder resultDatas = new StringBuilder();
        String[] dats = dat.split(textsep__);
        int lendata = dats.length;
        byte[][] rrr = new byte[lendata][];
        try {
            while (i < lendata) {
                byte[] datas = new BASE64Decoder().decodeBuffer(dats[i]);
                buff = cipher.doFinal(datas, 0, maxBlock);
                rrr[i] = buff;
                i++;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        for (byte[] bytes : rrr) {
            try {
                resultDatas.append(new String(bytes, CHARSET));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return resultDatas.toString();
    }


    public static String RsaEncrypt(String str, String publicKey) {
        try {
            return publicEncrypt(str, RSAUtils.getPublicKey(publicKey));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String RsaEncryptEX(String str, String publickey) {
        try {
            return publicEncryptEX(str, RSAUtils.getPublicKey(publickey));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
