package com.basusingh.throwat.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class URLItems implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private int uid;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @ColumnInfo(name = "url_id")
    public String urlId;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "tiny_url")
    public String tinyUrl;

    @ColumnInfo(name = "timestamp")
    public String timestamp;

    @ColumnInfo(name = "tags", defaultValue = "")
    public String tags;

    @ColumnInfo(name = "urlProtected", defaultValue = "no")
    public String urlProtected;

    public String getUrlProtected() {
        return urlProtected;
    }

    public void setUrlProtected(String urlProtected) {
        this.urlProtected = urlProtected;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTinyUrl() {
        return tinyUrl;
    }

    public void setTinyUrl(String tinyUrl) {
        this.tinyUrl = tinyUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
