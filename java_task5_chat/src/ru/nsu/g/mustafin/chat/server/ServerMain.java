package ru.nsu.g.mustafin.chat.server;

import ru.nsu.g.mustafin.chat.utils.serializers.JSerializer;
import ru.nsu.g.mustafin.chat.utils.serializers.ObjectSerializer;

public class ServerMain {
    public static void main(String[] args) {
        Server server;
        if (args.length > 0 && args[0].equals("-j")) {
            server = new Server(1337, new JSerializer());
        } else {
            server = new Server(1337, new ObjectSerializer());
        }
        server.run();
    }
}
