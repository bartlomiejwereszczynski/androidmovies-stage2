
package com.example.werek.themoviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewList implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("results")
    @Expose
    public List<Review> results = null;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;
    @SerializedName("total_results")
    @Expose
    public Integer totalResults;

    @Override
    public String toString() {
        return "ReviewList{" +
                "id=" + id +
                ", page=" + page +
                ", results=" + results +
                ", totalPages=" + totalPages +
                ", totalResults=" + totalResults +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.page);
        dest.writeTypedList(results);
        dest.writeValue(this.totalPages);
        dest.writeValue(this.totalResults);
    }

    public ReviewList() {
    }

    protected ReviewList(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = in.createTypedArrayList(Review.CREATOR);
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalResults = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<ReviewList> CREATOR = new Parcelable.Creator<ReviewList>() {
        public ReviewList createFromParcel(Parcel source) {
            return new ReviewList(source);
        }

        public ReviewList[] newArray(int size) {
            return new ReviewList[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewList)) return false;

        ReviewList that = (ReviewList) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (page != null ? !page.equals(that.page) : that.page != null) return false;
        if (results != null ? !results.equals(that.results) : that.results != null) return false;
        if (totalPages != null ? !totalPages.equals(that.totalPages) : that.totalPages != null)
            return false;
        return totalResults != null ? totalResults.equals(that.totalResults) : that.totalResults == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (page != null ? page.hashCode() : 0);
        result = 31 * result + (results != null ? results.hashCode() : 0);
        result = 31 * result + (totalPages != null ? totalPages.hashCode() : 0);
        result = 31 * result + (totalResults != null ? totalResults.hashCode() : 0);
        return result;
    }
}
