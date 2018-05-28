package com.papercutlabs.jobstar.model;

import java.io.Serializable;

/**
 * Created by ritwik on 17-05-2018.
 */

public class YoutubeVideoClass implements Serializable {

    private String kind = "";

    private String etag = "";

    private String videoID = "";

    private String publishedAt = "";

    private String channelID = "";

    private String title = "";

    private String description = "";

    private String thumbnailDefault = "";

    private String thumbnailMedium = "";

    private String thumbnailHigh = "";

    private String channelTitle = "";

    private String liveBroadcastContent = "";

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getChannelID() {
        return channelID;
    }

    public void setChannelID(String channelID) {
        this.channelID = channelID;
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

    public String getThumbnailDefault() {
        return thumbnailDefault;
    }

    public void setThumbnailDefault(String thumbnailDefault) {
        this.thumbnailDefault = thumbnailDefault;
    }

    public String getThumbnailMedium() {
        return thumbnailMedium;
    }

    public void setThumbnailMedium(String thumbnailMedium) {
        this.thumbnailMedium = thumbnailMedium;
    }

    public String getThumbnailHigh() {
        return thumbnailHigh;
    }

    public void setThumbnailHigh(String thumbnailHigh) {
        this.thumbnailHigh = thumbnailHigh;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public void setChannelTitle(String channelTitle) {
        this.channelTitle = channelTitle;
    }

    public String getLiveBroadcastContent() {
        return liveBroadcastContent;
    }

    public void setLiveBroadcastContent(String liveBroadcastContent) {
        this.liveBroadcastContent = liveBroadcastContent;
    }
}
