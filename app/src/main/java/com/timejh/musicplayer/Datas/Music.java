package com.timejh.musicplayer.Datas;

import android.net.Uri;

import com.timejh.musicplayer.Utils.Logger;

/**
 * Created by tokijh on 2017. 2. 1..
 */

public class Music {

    private String id;
    private String album_id;
    private String title;
    private String artist;
    private Uri imageuri;
    private Uri uri;

    public Music() {

    }

    public Music(String id, String title, String artist, String album_id, Uri uri) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album_id = album_id;
        this.uri = uri;
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

    public Uri getImageuri() {
        return imageuri;
    }

    public void setImageuri(Uri imageuri) {
        this.imageuri = imageuri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void print() {
        Logger.logD("ContactData", "------------------------Data----------------------");
        Logger.logD("ContactData", "id : " + id);
        Logger.logD("ContactData", "album_id : " + album_id);
        Logger.logD("ContactData", "title : " + title);
        Logger.logD("ContactData", "artist : " + artist);
        Logger.logD("ContactData", "---------------------------------------------------");
    }
}
