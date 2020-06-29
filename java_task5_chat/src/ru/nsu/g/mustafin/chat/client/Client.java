package ru.nsu.g.mustafin.chat.client;

import ru.nsu.g.mustafin.chat.client.gui.Updater;
import ru.nsu.g.mustafin.chat.server.Server;
import ru.nsu.g.mustafin.chat.utils.*;
import ru.nsu.g.mustafin.chat.utils.exceptions.ServerErrorException;
import ru.nsu.g.mustafin.chat.utils.exceptions.UnknownResponseException;
import ru.nsu.g.mustafin.chat.utils.serializers.Serializer;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class Client {
    private static final Logger log = Logger.getLogger(Server.class.getName());
    private Socket socket;
    private int session;
    private SerializedWriter to_server;
    private SerializedReader from_server;
    private final ClientReadThread reader;
    private ServerMessage answer;
    private boolean is_answered = false;
    private Updater view;
    private int TIMEOUT = 5000;

    public Client(String hostname, int port, Serializer serializer) throws IOException {
        socket = new Socket(hostname, port);
        to_server = new SerializedWriter(socket.getOutputStream(), serializer);
        from_server = new SerializedReader(socket.getInputStream(), serializer);
        reader = new ClientReadThread();
        view = null;
        reader.start();
    }

    public synchronized void login(String name) throws ServerErrorException, InterruptedException, IOException, UnknownResponseException {
        to_server.send(new ClientMessage.Login(name, "Client " + name));
        this.wait(TIMEOUT);

        if (!is_answered) {
            throw new ServerErrorException("Server did not respond in time");
        }
        is_answered = false;

        if (answer instanceof ServerMessage.Error) {
            log.info("Failed to login: " + ((ServerMessage.Error) answer).reason);
            throw new ServerErrorException("Failed to send message: " + ((ServerMessage.Error) answer).reason);
        } else if (answer instanceof ServerMessage.Success.Login) {
            session = ((ServerMessage.Success.Login) answer).session;
        } else {
            throw new UnknownResponseException("Unknown response from server");
        }
    }

    public synchronized void list() throws IOException, UnknownResponseException, InterruptedException, ServerErrorException {
        to_server.send(new ClientMessage.List(this.session));
        this.wait(TIMEOUT);

        if (!is_answered) {
            throw new ServerErrorException("Server did not respond in time");
        }
        is_answered = false;

        if (answer instanceof ServerMessage.Success.Error) {
            log.info("Failed to get users list: " + ((ServerMessage.Error) answer).reason);
            throw new ServerErrorException("Failed to get users list: " + ((ServerMessage.Error) answer).reason);
        } else if (answer instanceof ServerMessage.Success.List) {
            var users_t = ((ServerMessage.Success.List) answer).list;
            var users = new String[users_t.length];
            int i = 0;
            for (var user : users_t) {
                users[i] = user.name;
                i++;
            }
            view.updateUserList(users);
        } else {
            throw new UnknownResponseException("Unknown response from server");
        }
    }

    public synchronized void sendText(String text) throws ServerErrorException, IOException, InterruptedException, UnknownResponseException {
        to_server.send(new ClientMessage.Message(text, session));
        this.wait(TIMEOUT);

        if (!is_answered) {
            throw new ServerErrorException("Server did not respond in time");
        }
        is_answered = false;

        if (answer instanceof ServerMessage.Success.Error) {
            log.info("Failed to send message: " + ((ServerMessage.Error) answer).reason);
            throw new ServerErrorException("Failed to send message: " + ((ServerMessage.Error) answer).reason);
        } else if (answer instanceof ServerMessage.Success) {
            log.info("success server answer");
            view.updateChat("You: " + text + "\n");
        } else {
            throw new UnknownResponseException("Unknown response from server");
        }
    }

    /*
    public synchronized void logout() throws IOException, InterruptedException, ServerErrorException, UnknownResponseException {
        to_server.send(new ClientMessage.Logout(this.session));
        this.wait();
        if (answer instanceof ServerAnswer.Success.Error) {
            log.info("Failed to logout: " + ((ServerAnswer.Error) answer).reason);
            throw new ServerErrorException("Failed to logout: " + ((ServerAnswer.Error) answer).reason);
        } else if (answer instanceof ServerAnswer.Success.Logout) {
            view.close();
        } else {
            throw new UnknownResponseException("Unknown response from server");
        }
    }
    */

    public void setView(Updater updater) {
        view = updater;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ClientReadThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    answer = (ServerMessage) from_server.receive(ServerMessage.class);
                    if (answer instanceof ServerMessage.Success || answer instanceof ServerMessage.Error) {
                        synchronized (Client.this) {
                            is_answered = true;
                            Client.this.notify();
                        }
                    } else if (answer instanceof ServerMessage.Event) {
                        log.info("handling event");
                        handleEvent((ServerMessage.Event) answer);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    break;
                }
            }
        }

        public void handleEvent(ServerMessage.Event event) {
            if (event instanceof ServerMessage.Event.Message) {
                view.updateChat(((ServerMessage.Event.Message) event).from + ": " + ((ServerMessage.Event.Message) event).message + "\n");
            } else if (event instanceof ServerMessage.Event.UserLogin) {
                view.updateChat(((ServerMessage.Event.UserLogin) event).name + " joined the chat\n");
                view.addUser(((ServerMessage.Event.UserLogin) event).name);
            } else if (event instanceof ServerMessage.Event.UserLogout) {
                view.updateChat(((ServerMessage.Event.UserLogout) event).name + " left the chat\n");
                view.removeUser(((ServerMessage.Event.UserLogout) event).name);
            }
        }

    }
}
