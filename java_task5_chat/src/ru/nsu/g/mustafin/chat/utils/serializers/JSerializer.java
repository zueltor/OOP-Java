package ru.nsu.g.mustafin.chat.utils.serializers;

import com.google.gson.*;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import ru.nsu.g.mustafin.chat.utils.ClientMessage;
import ru.nsu.g.mustafin.chat.utils.ServerMessage;

import java.io.Serializable;

public class JSerializer implements Serializer {
    private Gson gson;//=new Gson();

    public JSerializer() {
           var gsb = new GsonBuilder();
        RuntimeTypeAdapterFactory<ClientMessage> client_msg_factory
                = RuntimeTypeAdapterFactory.of(ClientMessage.class, "ctype");
        client_msg_factory.registerSubtype(ClientMessage.Login.class,"login");
        client_msg_factory.registerSubtype(ClientMessage.List.class,"list");
        client_msg_factory.registerSubtype(ClientMessage.Message.class,"message");
        client_msg_factory.registerSubtype(ClientMessage.Logout.class,"logout");

        RuntimeTypeAdapterFactory<ServerMessage> server_msg_factory =
                RuntimeTypeAdapterFactory.of(ServerMessage.class, "ctype");
        server_msg_factory.registerSubtype(ServerMessage.Error.class,"error");
        server_msg_factory.registerSubtype(ServerMessage.Success.class,"success");
        server_msg_factory.registerSubtype(ServerMessage.Success.Logout.class,"logout");
        server_msg_factory.registerSubtype(ServerMessage.Success.Login.class,"login");
        server_msg_factory.registerSubtype(ServerMessage.Success.List.class,"list");
        server_msg_factory.registerSubtype(ServerMessage.Event.Message.class,"message");
        server_msg_factory.registerSubtype(ServerMessage.Event.UserLogout.class,"ulogout");
        server_msg_factory.registerSubtype(ServerMessage.Event.UserLogin.class,"ulogin");
        gson=gsb.registerTypeAdapterFactory(client_msg_factory).registerTypeAdapterFactory(server_msg_factory).create();
    }

    @Override
    public byte[] serialize(Serializable obj) {
        return gson.toJson(obj).getBytes();
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> objectClass) {
        return gson.fromJson(new String(bytes), objectClass);
    }
}
