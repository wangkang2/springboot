package com.wk.test.nettyTest.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class KryoSerializer implements Serializer {

    private final Class<?> clazz;

    public KryoSerializer(Class<?> clazz){
        this.clazz = clazz;
    }


    final ThreadLocal<Kryo> kryoThreadLocal = new ThreadLocal<Kryo>(){
        @Override
        protected Kryo initialValue(){
            Kryo kryo = new Kryo();
            kryo.register(clazz, new BeanSerializer(kryo,clazz));
            return kryo;
        }
    };

    private Kryo getKryo(){
        return kryoThreadLocal.get();
    }

    @Override
    public byte[] serialize(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Output output = new Output(byteArrayOutputStream);
        try {
            Kryo kryo = getKryo();
            kryo.writeObjectOrNull(output,object,object.getClass());
            output.flush();
            return byteArrayOutputStream.toByteArray();
        }finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(byteArrayOutputStream);
        }

    }

    @Override
    public <T> T deserialize(byte[] bytes) {
        if(bytes ==null){
            return null;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Input input = new Input(byteArrayInputStream);
        try {
            Kryo kryo = getKryo();
            return (T) kryo.readObjectOrNull(input,clazz);
        }finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(byteArrayInputStream);
        }
    }
}
