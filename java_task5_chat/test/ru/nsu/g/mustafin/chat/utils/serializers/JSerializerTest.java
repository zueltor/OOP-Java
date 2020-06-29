package ru.nsu.g.mustafin.chat.utils.serializers;

import org.junit.Test;
import ru.nsu.g.mustafin.chat.utils.ClientMessage;

import java.io.IOException;

import static org.junit.Assert.*;

public class JSerializerTest {

    @Test
    public void serialize() throws IOException, ClassNotFoundException {
        ObjectSerializer serializer=new ObjectSerializer();
        ClientMessage.Message msg=new ClientMessage.Message("hello",5);
        var bytes=serializer.serialize(msg);
        var deser_msg=(ClientMessage.Message)serializer.deserialize(bytes,ClientMessage.Message.class);
        assertEquals(msg.session,deser_msg.session);
        assertEquals(msg.message,deser_msg.message);
    }
}