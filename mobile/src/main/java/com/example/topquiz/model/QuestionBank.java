package com.example.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Collections;
import java.util.List;

public class QuestionBank implements Parcelable {

    private final List<Question> mQuestionList;
    private int mQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        mQuestionList = questionList;
        Collections.shuffle(mQuestionList); // Mélange les questions
    }

    protected QuestionBank(Parcel in) {
        mQuestionList = in.createTypedArrayList(Question.CREATOR);
        mQuestionIndex = in.readInt();
    }

    public static final Creator<QuestionBank> CREATOR = new Creator<QuestionBank>() {
        @Override
        public QuestionBank createFromParcel(Parcel in) {
            return new QuestionBank(in);
        }

        @Override
        public QuestionBank[] newArray(int size) {
            return new QuestionBank[size];
        }
    };

    public Question getCurrentQuestion() {
        return mQuestionList.get(mQuestionIndex);
    }

    public Question getNextQuestion() {
        mQuestionIndex++;
        if (mQuestionIndex >= mQuestionList.size()){
            return null;
        }
        return getCurrentQuestion();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeTypedList(mQuestionList);
        parcel.writeInt(mQuestionIndex);
    }
}
