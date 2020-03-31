package com.jadd.friends.Classes;

import java.util.ArrayList;

public class Question {
    ArrayList<String> questions = new ArrayList<>();

    public Question(ArrayList<String> questions) {
        this.questions = questions;
    }

    public Question() {
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }

    public void addQuestion(String question){
        questions.add(questions.size(),question);
    }
}
