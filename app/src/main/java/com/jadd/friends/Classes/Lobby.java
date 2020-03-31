package com.jadd.friends.Classes;

public class Lobby {
    String code;
    String question;

    public Lobby(String code, String question) {
        this.code = code;
        this.question = question;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Lobby() {
    }

    public Lobby(String code) {
        this.code = code;
    }
}
