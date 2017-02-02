package com.timejh.musicplayer;

import android.util.Log;

/**
 * Created by tokijh on 2017. 2. 1..
 */

public class MusicData {

    private String id;
    private String album_id;
    private String title;
    private String artist;

    public MusicData() {

    }

    public MusicData(String id, String title, String artist, String album_id) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album_id = album_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void print() {
        Log.d("ContactData", "------------------------Data----------------------");
        Log.d("ContactData", "id : " + id);
        Log.d("ContactData", "album_id : " + album_id);
        Log.d("ContactData", "title : " + title);
        Log.d("ContactData", "artist : " + artist);
        Log.d("ContactData", "---------------------------------------------------");
    }
}
