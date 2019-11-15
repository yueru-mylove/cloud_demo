package com.miracle.cloud.encrypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月09日 15:58:00
 */
public class MD5EncryptUtil {


    private static final String MD5_ALGORITHM = "MD5";

    /**
     * 加密
     *
     * @param content
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encrypt(String content) throws NoSuchAlgorithmException {
        MessageDigest instance = MessageDigest.getInstance(MD5_ALGORITHM);
        byte[] digest = instance.digest(content.getBytes());
        return ByteUtils.toHexString(digest);
    }





    public static String encrypt(String text, String salt) throws Exception {
        return DigestUtils.md5Hex(text + salt);
    }



    /**
     *  
     * MD5加码 生成32位md5码 
     *      
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     *  
     * 加密解密算法 执行一次加密，两次解密 
     *      
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        return new String(a);

    }

    // 测试主函数  
    public static void main(String[] args) throws Exception {
        String s = new String("1111");
        System.out.println("原始：" + s);
        System.out.println("MD5后：" + string2MD5(s));
        System.out.println("加密的：" + convertMD5(s));
        System.out.println("解密的：" + convertMD5(convertMD5(s)));
    }

}
