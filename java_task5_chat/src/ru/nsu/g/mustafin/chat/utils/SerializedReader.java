package ru.nsu.g.mustafin.chat.utils;

import ru.nsu.g.mustafin.chat.utils.serializers.Serializer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SerializedReader {
    private DataInputStream dis;
    private Serializer serializer;

    public SerializedReader(InputStream in, Serializer serializer) {
        this.serializer=serializer;
        dis = new DataInputStream(in);
    }

    public Object receive(Class<?> objectClass) throws IOException, ClassNotFoundException {
        int length = dis.readInt();
        return serializer.deserialize(dis.readNBytes(length), objectClass);
    }
}
