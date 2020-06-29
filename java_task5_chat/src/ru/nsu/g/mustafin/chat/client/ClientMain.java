package ru.nsu.g.mustafin.chat.client;

import ru.nsu.g.mustafin.chat.client.gui.MenuView;
import ru.nsu.g.mustafin.chat.utils.serializers.JSerializer;
import ru.nsu.g.mustafin.chat.utils.serializers.ObjectSerializer;

public class ClientMain {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("-j")) {
            new MenuView(new JSerializer());
        } else {
            new MenuView(new ObjectSerializer());
        }
    }
}
