package com.miracle.cloud.serializer;

import java.io.*;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月07日 10:16:00
 */
public class JDKSerializer {


    public static <T> T deserializer(byte[] bytes) {
        if (null == bytes || bytes.length <= 0) {
            return null;
        }
        ByteArrayInputStream bios = null;
        ObjectInputStream ois = null;
        try {
            bios = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bios);
            return (T)ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != ois) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != bios) {
                try {
                    bios.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


    public static byte[] serializer(Object object) {
        if (null == object) {
            return null;
        }
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != baos) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != oos) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
