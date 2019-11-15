package com.miracle.cloud.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * @author miracle~
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月29日 15:45:00
 */
public class KryoSerializer {


    public static byte[] serialize(Object data, Class clazz) {
        Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        kryo.setRegistrationRequired(false);
        kryo.setReferences(false);
        kryo.register(clazz);
        byte[] bytes = new byte[8192];
        Output output = new Output(bytes, Integer.MAX_VALUE);
        kryo.writeObject(output, data);
        output.flush();
        output.close();
        return bytes;
    }



    public static <T> T deSerialize(byte[] bytes, Class<T> clazz) {
        Kryo kryo = new Kryo();
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
        kryo.setRegistrationRequired(false);
        kryo.setReferences(false);
        kryo.register(clazz);

        Input input = new Input(bytes);
        T t = kryo.readObject(input, clazz);
        input.close();
        return t;
    }

}
