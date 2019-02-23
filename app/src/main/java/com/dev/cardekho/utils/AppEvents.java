package com.dev.cardekho.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AppEvents  implements Parcelable {
    private ArrayList<String> appEvents;

    public AppEvents(ArrayList<String> appEvents) {
        this.appEvents = appEvents;
    }


    protected AppEvents(Parcel in) {
        appEvents = in.createStringArrayList();
    }

    public static final Creator<AppEvents> CREATOR = new Creator<AppEvents>() {
        @Override
        public AppEvents createFromParcel(Parcel in) {
            return new AppEvents(in);
        }

        @Override
        public AppEvents[] newArray(int size) {
            return new AppEvents[size];
        }
    };

    public ArrayList<String> getAppEvents() {
        return appEvents;
    }

    public void setAppEvents(ArrayList<String> appEvents) {
        this.appEvents = appEvents;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(appEvents);
    }
}
