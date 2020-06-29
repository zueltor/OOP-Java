package ru.nsu.g.mustafin.chat.utils.serializers;

import java.io.IOException;
import java.io.Serializable;

public interface Serializer {
    byte[] serialize(Serializable obj) throws IOException;

    Object deserialize(byte[] bytes, Class<?> objectClass) throws IOException, ClassNotFoundException;
}
