package com.example.todoboom;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class Todo implements Parcelable {

    private String description;
    private boolean isDone;

    Todo(String message){
        this.description = message;
        this.isDone = false;
    }
    private Todo(Parcel in) {
        description = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isDone = in.readBoolean();
        }
    }
    public void setIdDone(){
        this.isDone = true;
    }
    boolean getisDone(){
        return this.isDone;
    }
    String getMessage() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(description);
        dest.writeValue(isDone);
    }
    public static final Parcelable.Creator<Todo> CREATOR = new Parcelable.Creator<Todo>() {
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };
}
