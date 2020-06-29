package ru.nsu.g.mustafin.chat.utils;

import java.io.*;

public interface ClientMessage extends Serializable{
    class Login implements ClientMessage{
        public String ctype="login";
        public String name;
        public String type;
        public Login(String name, String type){
            this.name=name;
            this.type=type;
        }
    }

    class List implements ClientMessage{
        public String ctype="list";
        public int session;
        public List(int session){
            this.session=session;
        }
    }

    class Message implements ClientMessage{
        public String ctype="message";
        public String message;
        public int session;

        public Message(String message, int session){
            this.message=message;
            this.session=session;
        }
    }

    class Logout implements ClientMessage{
        public String ctype="logout";
        public int session;
        public Logout(int session){
            this.session=session;
        }
    }
}
