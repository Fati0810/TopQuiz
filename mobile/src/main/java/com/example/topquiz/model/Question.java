package com.example.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.List;

public class Question implements Parcelable {

    private final String mQuestion;
    private final List<String> mChoiceList;
    private final int mAnswerIndex;

    // Constructeur
    public Question(String question, List<String> choiceList, int answerIndex) {
        mQuestion = question;
        mChoiceList = choiceList;
        mAnswerIndex = answerIndex;
    }

    protected Question(Parcel in) {
        mQuestion = in.readString();
        mChoiceList = in.createStringArrayList();
        mAnswerIndex = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(mQuestion);
        parcel.writeStringList(mChoiceList);
        parcel.writeInt(mAnswerIndex);
    }
}
