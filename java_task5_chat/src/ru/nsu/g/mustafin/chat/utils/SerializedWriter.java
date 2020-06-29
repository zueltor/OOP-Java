package ru.nsu.g.mustafin.chat.utils;

import ru.nsu.g.mustafin.chat.utils.serializers.Serializer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

public class SerializedWriter {
    private DataOutputStream dos;
    private Serializer serializer;

    public SerializedWriter(OutputStream out, Serializer serializer) {
        this.serializer = serializer;
        dos = new DataOutputStream(out);
    }

    public void send(Serializable obj) throws IOException {
        byte[] bytes = serializer.serialize(obj);
        dos.writeInt(bytes.length);
        dos.write(bytes);
        dos.flush();
    }
}
