package com.androiddevs.videostreamplayer;

public class Track {

    private String title;
    private String url;

    public Track(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() { return title; }

    public String getUrl() { return url; }
}
