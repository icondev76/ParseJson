package com.example.parsejson;

public class Items {
    String subreddit;
    String title;
    String thumbnail;
    String itemUrl;

    public Items() {
    }

    public Items(String subreddit, String title, String thumbnail, String itemUrl) {
        this.subreddit = subreddit;
        this.title = title;
        this.thumbnail = thumbnail;
        this.itemUrl = itemUrl;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public void setItemUrl(String itemUrl) {
        this.itemUrl = itemUrl;
    }
}
