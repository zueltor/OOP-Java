package ru.nsu.g.mustafin.chat.server;

import ru.nsu.g.mustafin.chat.utils.*;
import ru.nsu.g.mustafin.chat.utils.serializers.Serializer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.logging.Logger;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private ArrayList<SerializedWriter> writers;
    private final ArrayDeque<ServerMessage.Event.Message> messages;
    private final int HISTORY_SIZE = 20;
    private ArrayList<Client> clients;
    private int session_number = 1;
    private Serializer serializer;
    private static final Logger log = Logger.getLogger(Server.class.getName());

    static class Client {
        public String name;
        public int session;
        public String type;

        Client(String name, int session, String type) {
            this.name = name;
            this.session = session;
            this.type = type;
        }
    }

    public Server(int port, Serializer serializer) {
        this.serializer = serializer;
        clients = new ArrayList<>();
        writers = new ArrayList<>();
        messages = new ArrayDeque<>();

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        SerializedWriter writer;
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                log.info("New user accepted");
                writer = new SerializedWriter(socket.getOutputStream(), serializer);
                writers.add(writer);

                ServerWriteThread client_handler = new ServerWriteThread(socket, writer);
                client_handler.start();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public int getSession() {
        return session_number++;
    }

    public class ServerWriteThread extends Thread {
        private SerializedWriter to_client;
        private SerializedReader from_client;
        private Client client = null;
        private Socket socket;

        public ServerWriteThread(Socket socket, SerializedWriter to_client) throws IOException {
            this.socket = socket;
            this.to_client = to_client;
            from_client = new SerializedReader(socket.getInputStream(), serializer);
        }

        private void sendList() {
            ServerMessage.Success.List.User[] list = new ServerMessage.Success.List.User[clients.size()];
            int i = 0;
            for (var client : clients) {
                list[i] = new ServerMessage.Success.List.User(client.name, client.type);
                i++;
            }
            try {
                to_client.send(new ServerMessage.Success.List(list));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleList(ClientMessage.List clientMessage) throws IOException {
            int client_session = clientMessage.session;
            if (client == null) {
                throw new IOException("Not logged in");
            }
            if (client.session != client_session) {
                to_client.send(new ServerMessage.Error("Invalid session"));
            } else {
                sendList();
            }
        }

        void handleLogin(ClientMessage.Login new_client) throws IOException {
            if (isLoginTaken(new_client.name)) {
                to_client.send(new ServerMessage.Error("Name is already taken"));
                return;
            } else {
                client = new Client(new_client.name, getSession(), new_client.type);
                clients.add(client);
                to_client.send(new ServerMessage.Success.Login(client.session));
                log.info("New user joined to chat");

                for (var message : messages) {
                    to_client.send(message);
                }
            }

            for (var writer : writers) {
                if (!writer.equals(to_client)) {
                    writer.send(new ServerMessage.Event.UserLogin(client.name));
                }
            }
        }

        boolean isLoginTaken(String name) {
            for (var client : clients) {
                if (client.name.equals(name)) {
                    return true;
                }
            }
            return false;
        }

        void handleMessage(ClientMessage.Message clientMessage) throws IOException {
            String text = clientMessage.message;
            int client_session = clientMessage.session;
            if (client.session != client_session) {
                to_client.send(new ServerMessage.Error("Invalid session"));
                log.info("Invalid session of client " + socket);
            } else {
                var message = new ServerMessage.Event.Message(text, client.name);
                if (messages.size() == HISTORY_SIZE) {
                    messages.pollFirst();
                }
                messages.add(message);
                for (var writer : writers) {
                    if (!writer.equals(to_client)) {
                        writer.send(message);
                        log.info("Message sent to client");
                    } else {
                        to_client.send(new ServerMessage.Success());
                        log.info("Success sent to client");
                    }
                }
            }
        }

        @Override
        public void run() {
            ClientMessage clientMessage;
            while (true) {
                try {
                    clientMessage = (ClientMessage) from_client.receive(ClientMessage.class);
                    if (clientMessage instanceof ClientMessage.Login) {
                        handleLogin((ClientMessage.Login) clientMessage);
                        log.info("Handled login request");
                    } else if (clientMessage instanceof ClientMessage.List) {
                        handleList((ClientMessage.List) clientMessage);
                        log.info("Handled list request");
                    } else if (clientMessage instanceof ClientMessage.Message) {
                        handleMessage((ClientMessage.Message) clientMessage);
                        log.info("Handled message request");
                    } else if (clientMessage instanceof ClientMessage.Logout) {
                        int client_session = ((ClientMessage.Logout) clientMessage).session;
                        if (client.session != client_session) {
                            to_client.send(new ServerMessage.Error("Invalid session"));
                        } else {
                            to_client.send(new ServerMessage.Success.Logout());
                            clients.remove(client);
                            onLogout();
                            break;
                        }
                    }
                } catch (Exception e) {
                    onLogout();
                    break;
                }
            }
        }

        void onLogout() {
            writers.remove(to_client);
            try {
                socket.close();
                if (client != null) {
                    clients.remove(client);
                    var message = new ServerMessage.Event.UserLogout(client.name);
                    for (var writer : writers) {
                        writer.send(message);
                    }
                    log.info("User " + client.name + " left chat");
                } else{
                    log.info("Unknown accepted user left the chat");
                }
            } catch (IOException ignored) {
            }
        }
    }

}
