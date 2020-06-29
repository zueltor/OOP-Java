package ru.nsu.g.mustafin.chat.utils;

import java.io.Serializable;

public interface ServerMessage extends Serializable {
    String ctype="servermessage";
    class Error implements ServerMessage {
        public String ctype="error";
        public String reason;

        public Error(String reason) {
            this.reason = reason;
        }
    }

    interface Event extends ServerMessage {
        String ctype="event";
        class Message implements Event {
            public String ctype="message";
            public String message;
            public String from;

            public Message(String message, String from) {
                this.message = message;
                this.from = from;
            }
        }

        class UserLogin implements Event {
            public String ctype="ulogin";
            public String name;

            public UserLogin(String name) {
                this.name = name;
            }
        }

        class UserLogout implements Event {
            public String ctype="ulogout";
            public String name;

            public UserLogout(String name) {
                this.name = name;
            }
        }

    }

    class Success implements ServerMessage {
        public String ctype="success";
        public static class Login extends Success {
            public int session;

            public Login(int session) {
                this.session = session;
                ctype="login";
            }
        }

        public static class List extends Success {
            public User[] list;

            public List(User[] list) {
                this.list = list;
                ctype="list";
            }

            public static class User implements Serializable {
                public String name;
                public String type;

                public User(String name, String type) {
                    this.name = name;
                    this.type = type;
                }
            }
        }

        public static class Logout extends Success {
            public Logout(){
                ctype="logout";
            }
        }
    }
}
