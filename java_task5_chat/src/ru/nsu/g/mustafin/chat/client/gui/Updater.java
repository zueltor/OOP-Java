package ru.nsu.g.mustafin.chat.client.gui;

public interface Updater {
    void updateChat(String text);
    void updateUserList(String[] users);
    void addUser(String user);
    void removeUser(String user);
}
