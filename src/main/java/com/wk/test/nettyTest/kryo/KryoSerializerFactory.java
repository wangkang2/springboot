package com.wk.test.nettyTest.kryo;

public class KryoSerializerFactory {
    public static Serializer getSerializer(Class<?> clazz){
        return new KryoSerializer(clazz);
    }
}
