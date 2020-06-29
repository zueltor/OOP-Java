package ru.nsu.g.mustafin.chat.utils.serializers;


import java.io.*;

public class ObjectSerializer implements Serializer {
    @Override
    public byte[] serialize(Serializable obj) throws IOException {
        var byteStream = new ByteArrayOutputStream();
        var os = new ObjectOutputStream(byteStream);

        os.writeObject(obj);
        os.flush();
        return byteStream.toByteArray();
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> objectClass) throws IOException, ClassNotFoundException {
        var byteStream = new ByteArrayInputStream(bytes);
        var is = new ObjectInputStream(byteStream);

        return is.readObject();
    }
}
