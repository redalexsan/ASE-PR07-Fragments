package com.example.alex.ase_pr07_fragments.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.DrawableRes;

public class Avatar implements Parcelable {

    private long id;
    @DrawableRes
    private final int imageResId;
    private final String name;

    public Avatar(int imageResId, String name) {
        this.imageResId = imageResId;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeInt(this.imageResId);
        dest.writeString(this.name);
    }

    protected Avatar(Parcel in) {
        this.id = in.readLong();
        this.imageResId = in.readInt();
        this.name = in.readString();
    }

    public static final Creator<Avatar> CREATOR = new Creator<Avatar>() {
        @Override
        public Avatar createFromParcel(Parcel source) {
            return new Avatar(source);
        }

        @Override
        public Avatar[] newArray(int size) {
            return new Avatar[size];
        }
    };
}


