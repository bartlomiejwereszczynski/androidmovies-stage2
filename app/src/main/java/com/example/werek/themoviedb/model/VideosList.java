
package com.example.werek.themoviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideosList implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("results")
    @Expose
    public List<Video> results = null;

    @Override
    public String toString() {
        return "VideosList{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeTypedList(results);
    }

    public VideosList() {
    }

    protected VideosList(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(Video.CREATOR);
    }

    public static final Parcelable.Creator<VideosList> CREATOR = new Parcelable.Creator<VideosList>() {
        public VideosList createFromParcel(Parcel source) {
            return new VideosList(source);
        }

        public VideosList[] newArray(int size) {
            return new VideosList[size];
        }
    };
}
