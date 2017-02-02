package com.timejh.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by tokijh on 2017. 2. 1..
 */

public class MusicManager {

    private Context context;
    private ArrayList<MusicData> datas = new ArrayList<>();

    public MusicManager(Context context) {
        this.context = context;
        getUserContactsList();
    }

    public ArrayList<MusicData> getMusicDataList() {
        return datas;
    }

    private void getUserContactsList() {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String projections[] ={
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = resolver.query(uri, projections, null, null, null);
        if(cursor != null) {
            while(cursor.moveToNext()) {
                MusicData musicData = new MusicData();

                int index = cursor.getColumnIndex(projections[0]);
                musicData.setId(cursor.getString(index));
                index = cursor.getColumnIndex(projections[1]);
                musicData.setAlbum_id(cursor.getString(index));
                index = cursor.getColumnIndex(projections[2]);
                musicData.setTitle(cursor.getString(index));
                index = cursor.getColumnIndex(projections[3]);
                musicData.setArtist(cursor.getString(index));

                musicData.print();
                datas.add(musicData);
            }
        }
    }
}