
package com.example.werek.themoviedb.model;

import com.example.werek.themoviedb.util.MovieDbApi;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.List;

public class MovieDetails {

    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("belongs_to_collection")
    @Expose
    private BelongsToCollection belongsToCollection;
    @SerializedName("budget")
    @Expose
    private Integer budget;
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("production_companies")
    @Expose
    private List<ProductionCompany> productionCompanies = null;
    @SerializedName("production_countries")
    @Expose
    private List<ProductionCountry> productionCountries = null;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("revenue")
    @Expose
    private Integer revenue;
    @SerializedName("runtime")
    @Expose
    private Integer runtime;
    @SerializedName("spoken_languages")
    @Expose
    private List<SpokenLanguage> spokenLanguages = null;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

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

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public BelongsToCollection getBelongsToCollection() {
        return belongsToCollection;
    }

    public void setBelongsToCollection(BelongsToCollection belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieDetails)) return false;

        MovieDetails that = (MovieDetails) o;

        if (getAdult() != null ? !getAdult().equals(that.getAdult()) : that.getAdult() != null)
            return false;
        if (getBackdropPath() != null ? !getBackdropPath().equals(that.getBackdropPath()) : that.getBackdropPath() != null)
            return false;
        if (getBelongsToCollection() != null ? !getBelongsToCollection().equals(that.getBelongsToCollection()) : that.getBelongsToCollection() != null)
            return false;
        if (getBudget() != null ? !getBudget().equals(that.getBudget()) : that.getBudget() != null)
            return false;
        if (getGenres() != null ? !getGenres().equals(that.getGenres()) : that.getGenres() != null)
            return false;
        if (getHomepage() != null ? !getHomepage().equals(that.getHomepage()) : that.getHomepage() != null)
            return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getImdbId() != null ? !getImdbId().equals(that.getImdbId()) : that.getImdbId() != null)
            return false;
        if (getOriginalLanguage() != null ? !getOriginalLanguage().equals(that.getOriginalLanguage()) : that.getOriginalLanguage() != null)
            return false;
        if (getOriginalTitle() != null ? !getOriginalTitle().equals(that.getOriginalTitle()) : that.getOriginalTitle() != null)
            return false;
        if (getOverview() != null ? !getOverview().equals(that.getOverview()) : that.getOverview() != null)
            return false;
        if (getPopularity() != null ? !getPopularity().equals(that.getPopularity()) : that.getPopularity() != null)
            return false;
        if (getPosterPath() != null ? !getPosterPath().equals(that.getPosterPath()) : that.getPosterPath() != null)
            return false;
        if (getProductionCompanies() != null ? !getProductionCompanies().equals(that.getProductionCompanies()) : that.getProductionCompanies() != null)
            return false;
        if (getProductionCountries() != null ? !getProductionCountries().equals(that.getProductionCountries()) : that.getProductionCountries() != null)
            return false;
        if (getReleaseDate() != null ? !getReleaseDate().equals(that.getReleaseDate()) : that.getReleaseDate() != null)
            return false;
        if (getRevenue() != null ? !getRevenue().equals(that.getRevenue()) : that.getRevenue() != null)
            return false;
        if (getRuntime() != null ? !getRuntime().equals(that.getRuntime()) : that.getRuntime() != null)
            return false;
        if (getSpokenLanguages() != null ? !getSpokenLanguages().equals(that.getSpokenLanguages()) : that.getSpokenLanguages() != null)
            return false;
        if (getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null)
            return false;
        if (getTagline() != null ? !getTagline().equals(that.getTagline()) : that.getTagline() != null)
            return false;
        if (getTitle() != null ? !getTitle().equals(that.getTitle()) : that.getTitle() != null)
            return false;
        if (getVideo() != null ? !getVideo().equals(that.getVideo()) : that.getVideo() != null)
            return false;
        if (getVoteAverage() != null ? !getVoteAverage().equals(that.getVoteAverage()) : that.getVoteAverage() != null)
            return false;
        return getVoteCount() != null ? getVoteCount().equals(that.getVoteCount()) : that.getVoteCount() == null;

    }

    @Override
    public int hashCode() {
        int result = getAdult() != null ? getAdult().hashCode() : 0;
        result = 31 * result + (getBackdropPath() != null ? getBackdropPath().hashCode() : 0);
        result = 31 * result + (getBelongsToCollection() != null ? getBelongsToCollection().hashCode() : 0);
        result = 31 * result + (getBudget() != null ? getBudget().hashCode() : 0);
        result = 31 * result + (getGenres() != null ? getGenres().hashCode() : 0);
        result = 31 * result + (getHomepage() != null ? getHomepage().hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getImdbId() != null ? getImdbId().hashCode() : 0);
        result = 31 * result + (getOriginalLanguage() != null ? getOriginalLanguage().hashCode() : 0);
        result = 31 * result + (getOriginalTitle() != null ? getOriginalTitle().hashCode() : 0);
        result = 31 * result + (getOverview() != null ? getOverview().hashCode() : 0);
        result = 31 * result + (getPopularity() != null ? getPopularity().hashCode() : 0);
        result = 31 * result + (getPosterPath() != null ? getPosterPath().hashCode() : 0);
        result = 31 * result + (getProductionCompanies() != null ? getProductionCompanies().hashCode() : 0);
        result = 31 * result + (getProductionCountries() != null ? getProductionCountries().hashCode() : 0);
        result = 31 * result + (getReleaseDate() != null ? getReleaseDate().hashCode() : 0);
        result = 31 * result + (getRevenue() != null ? getRevenue().hashCode() : 0);
        result = 31 * result + (getRuntime() != null ? getRuntime().hashCode() : 0);
        result = 31 * result + (getSpokenLanguages() != null ? getSpokenLanguages().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getTagline() != null ? getTagline().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getVideo() != null ? getVideo().hashCode() : 0);
        result = 31 * result + (getVoteAverage() != null ? getVoteAverage().hashCode() : 0);
        result = 31 * result + (getVoteCount() != null ? getVoteCount().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MovieDetails{" +
                "adult=" + adult +
                ", backdropPath='" + backdropPath + '\'' +
                ", belongsToCollection=" + belongsToCollection +
                ", budget=" + budget +
                ", genres=" + genres +
                ", homepage='" + homepage + '\'' +
                ", id=" + id +
                ", imdbId='" + imdbId + '\'' +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", posterPath='" + posterPath + '\'' +
                ", productionCompanies=" + productionCompanies +
                ", productionCountries=" + productionCountries +
                ", releaseDate='" + releaseDate + '\'' +
                ", revenue=" + revenue +
                ", runtime=" + runtime +
                ", spokenLanguages=" + spokenLanguages +
                ", status='" + status + '\'' +
                ", tagline='" + tagline + '\'' +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", voteAverage=" + voteAverage +
                ", voteCount=" + voteCount +
                '}';
    }
}
