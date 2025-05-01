package com.example.topquiz.model;

import java.io.Serializable;
import java.util.List;

public class Question implements Serializable {

    private final String mQuestion;
    private final List<String> mChoiceList;
    private final int mAnswerIndex;

    // Constructeur
    public Question(String question, List<String> choiceList, int answerIndex) {
        mQuestion = question;
        mChoiceList = choiceList;
        mAnswerIndex = answerIndex;
    }

    // Getter pour la question
    public String getQuestion() {
        return mQuestion;
    }

    // Getter pour les choix
    public List<String> getChoiceList() {
        return mChoiceList;
    }

    // Getter pour l'index de la bonne réponse
    public int getAnswerIndex() {
        return mAnswerIndex;
    }
}
