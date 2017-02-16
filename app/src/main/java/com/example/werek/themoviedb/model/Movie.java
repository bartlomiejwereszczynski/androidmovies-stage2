package com.example.werek.themoviedb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.werek.themoviedb.util.MovieDbApi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;

public class Movie implements Parcelable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;



    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public URL getPosterUrl(String size) {
        return MovieDbApi.buildImageURL(size,getPosterPath());
    }

    public URL getPosterUrl()
    {
        return getPosterUrl(MovieDbApi.POSTER_WIDTH_185);
    }

    public URL getBackdropUrl(String size) {
        return MovieDbApi.buildImageURL(size,getBackdropPath());
    }

    public URL getBackdropUrl()
    {
        return getBackdropUrl(MovieDbApi.POSTER_WIDTH_780);
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        if (getId() != null ? !getId().equals(movie.getId()) : movie.getId() != null) return false;
        if (getTitle() != null ? !getTitle().equals(movie.getTitle()) : movie.getTitle() != null)
            return false;
        if (getPosterPath() != null ? !getPosterPath().equals(movie.getPosterPath()) : movie.getPosterPath() != null)
            return false;
        if (getAdult() != null ? !getAdult().equals(movie.getAdult()) : movie.getAdult() != null)
            return false;
        if (getOverview() != null ? !getOverview().equals(movie.getOverview()) : movie.getOverview() != null)
            return false;
        if (getReleaseDate() != null ? !getReleaseDate().equals(movie.getReleaseDate()) : movie.getReleaseDate() != null)
            return false;
        if (getOriginalTitle() != null ? !getOriginalTitle().equals(movie.getOriginalTitle()) : movie.getOriginalTitle() != null)
            return false;
        if (getOriginalLanguage() != null ? !getOriginalLanguage().equals(movie.getOriginalLanguage()) : movie.getOriginalLanguage() != null)
            return false;
        if (getBackdropPath() != null ? !getBackdropPath().equals(movie.getBackdropPath()) : movie.getBackdropPath() != null)
            return false;
        if (getPopularity() != null ? !getPopularity().equals(movie.getPopularity()) : movie.getPopularity() != null)
            return false;
        if (getVoteCount() != null ? !getVoteCount().equals(movie.getVoteCount()) : movie.getVoteCount() != null)
            return false;
        if (getVideo() != null ? !getVideo().equals(movie.getVideo()) : movie.getVideo() != null)
            return false;
        return getVoteAverage() != null ? getVoteAverage().equals(movie.getVoteAverage()) : movie.getVoteAverage() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getPosterPath() != null ? getPosterPath().hashCode() : 0);
        result = 31 * result + (getAdult() != null ? getAdult().hashCode() : 0);
        result = 31 * result + (getOverview() != null ? getOverview().hashCode() : 0);
        result = 31 * result + (getReleaseDate() != null ? getReleaseDate().hashCode() : 0);
        result = 31 * result + (getOriginalTitle() != null ? getOriginalTitle().hashCode() : 0);
        result = 31 * result + (getOriginalLanguage() != null ? getOriginalLanguage().hashCode() : 0);
        result = 31 * result + (getBackdropPath() != null ? getBackdropPath().hashCode() : 0);
        result = 31 * result + (getPopularity() != null ? getPopularity().hashCode() : 0);
        result = 31 * result + (getVoteCount() != null ? getVoteCount().hashCode() : 0);
        result = 31 * result + (getVideo() != null ? getVideo().hashCode() : 0);
        result = 31 * result + (getVoteAverage() != null ? getVoteAverage().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", adult=" + adult +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", popularity=" + popularity +
                ", voteCount=" + voteCount +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeValue(this.adult);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.popularity);
        dest.writeValue(this.voteCount);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.posterPath = in.readString();
        this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.backdropPath = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

