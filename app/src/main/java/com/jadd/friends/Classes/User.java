package com.jadd.friends.Classes;

public class User {
    String name;
    String email;
    String uid;
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    String answer,answerOf;
    boolean isReady ,isAnswered,isLeader,completed;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    int point = 0;

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public User(String name, String email, String answer, boolean isReady, boolean isAnswered, boolean isLeader, int point,
                boolean completed,String answerOf) {
        this.name = name;
        this.point = point;
        this.answerOf = answerOf;
        this.email = email;
        this.completed = completed;
        this.isReady = isReady;
        this.isAnswered = isAnswered;
        this.answer = answer;
        this.isLeader = isLeader;
    }

    public String getAnswerOf() {
        return answerOf;
    }

    public void setAnswerOf(String answerOf) {
        this.answerOf = answerOf;
    }

    public User(boolean isReady, boolean isAnswered, boolean isLeader) {
        this.isReady = isReady;
        this.isAnswered = isAnswered;
        this.isLeader = isLeader;
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User(String name, String email, String uid) {
        this.name = name;
        this.uid = uid;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;

    }


    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }
}
