package com.rizky92.madedicodingsubmission2.notification;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationItems implements Parcelable {
    private int id;
    private String title;
    private String desc;

    public NotificationItems() {
    }

    private NotificationItems(Parcel in) {
        id = in.readInt();
        title = in.readString();
        desc = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NotificationItems> CREATOR = new Creator<NotificationItems>() {
        @Override
        public NotificationItems createFromParcel(Parcel in) {
            return new NotificationItems(in);
        }

        @Override
        public NotificationItems[] newArray(int size) {
            return new NotificationItems[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
