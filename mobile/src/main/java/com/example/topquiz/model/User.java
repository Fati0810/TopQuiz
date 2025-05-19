package com.example.topquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String mFirstName;
    private int mScore;

    public User() {
        this.mFirstName = "";
        this.mScore = 0;
    }

    public User(String firstName) {
        this.mFirstName = firstName;
        this.mScore = 0;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public int getScore() {
        return mScore;
    }

    public void incrementScore() {
        mScore++;
    }

    protected User(Parcel in) {
        mFirstName = in.readString();
        mScore = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFirstName);
        dest.writeInt(mScore);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
