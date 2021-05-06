
package com.oxoo.spagreen.models.single_details;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SingleDetails {

    @SerializedName("videos_id")
    @Expose
    private String videosId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("release")
    @Expose
    private String release;
    @SerializedName("runtime")
    @Expose
    private String runtime;
    @SerializedName("video_quality")
    @Expose
    private String videoQuality;
    @SerializedName("is_tvseries")
    @Expose
    private String isTvseries;
    @SerializedName("is_paid")
    @Expose
    private String isPaid;
    @SerializedName("enable_download")
    @Expose
    private String enableDownload;
    @SerializedName("download_links")
    @Expose
    private List<DownloadLink> downloadLinks = null;
    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailUrl;
    @SerializedName("poster_url")
    @Expose
    private String posterUrl;
    @SerializedName("videos")
    @Expose
    private List<Video> videos = null;
    @SerializedName("genre")
    @Expose
    private List<Genre> genre = null;
    @SerializedName("country")
    @Expose
    private List<Country> country = null;
    @SerializedName("director")
    @Expose
    private List<Director> director = null;
    @SerializedName("writer")
    @Expose
    private List<Writer> writer = null;
    @SerializedName("cast")
    @Expose
    private List<Cast> cast = null;
    @SerializedName("cast_and_crew")
    @Expose
    private List<CastAndCrew> castAndCrew = null;
    @SerializedName("related_movie")
    @Expose
    private List<RelatedMovie> relatedMovie = null;
    @SerializedName("season")
    @Expose
    private List<Season> season = null;
    @SerializedName("related_tvseries")
    @Expose
    private List<RelatedMovie> relatedTvseries = null;
    @SerializedName("trailler_youtube_source")
    @Expose
    private String trailerUrl = null;

    public String getVideosId() {
        return videosId;
    }

    public void setVideosId(String videosId) {
        this.videosId = videosId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getVideoQuality() {
        return videoQuality;
    }

    public void setVideoQuality(String videoQuality) {
        this.videoQuality = videoQuality;
    }

    public String getIsTvseries() {
        return isTvseries;
    }

    public void setIsTvseries(String isTvseries) {
        this.isTvseries = isTvseries;
    }

    public String getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(String isPaid) {
        this.isPaid = isPaid;
    }

    public String getEnableDownload() {
        return enableDownload;
    }

    public void setEnableDownload(String enableDownload) {
        this.enableDownload = enableDownload;
    }

    public List<DownloadLink> getDownloadLinks() {
        return downloadLinks;
    }

    public void setDownloadLinks(List<DownloadLink> downloadLinks) {
        this.downloadLinks = downloadLinks;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenre(List<Genre> genre) {
        this.genre = genre;
    }

    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

    public List<Director> getDirector() {
        return director;
    }

    public void setDirector(List<Director> director) {
        this.director = director;
    }

    public List<Writer> getWriter() {
        return writer;
    }

    public void setWriter(List<Writer> writer) {
        this.writer = writer;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public List<CastAndCrew> getCastAndCrew() {
        return castAndCrew;
    }

    public void setCastAndCrew(List<CastAndCrew> castAndCrew) {
        this.castAndCrew = castAndCrew;
    }

    public List<RelatedMovie> getRelatedMovie() {
        return relatedMovie;
    }

    public void setRelatedMovie(List<RelatedMovie> relatedMovie) {
        this.relatedMovie = relatedMovie;
    }

    public List<Season> getSeason() {
        return season;
    }

    public void setSeason(List<Season> season) {
        this.season = season;
    }

    public List<RelatedMovie> getRelatedTvseries() {
        return relatedTvseries;
    }

    public void setRelatedTvseries(List<RelatedMovie> relatedTvseries) {
        this.relatedTvseries = relatedTvseries;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }
}
