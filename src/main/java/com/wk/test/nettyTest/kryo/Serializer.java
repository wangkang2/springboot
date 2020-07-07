package com.wk.test.nettyTest.kryo;

public interface Serializer {
    //序列化接口
    byte[] serialize(Object object);
    //反序列化接口
    <T> T deserialize(byte[] bytes);
}
