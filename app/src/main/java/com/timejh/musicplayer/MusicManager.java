package com.timejh.musicplayer;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tokijh on 2017. 2. 1..
 */

public class MusicManager {

    private static final String TAG = "MusicManager";
    private static List<MusicData> datas = new ArrayList<>();

    public static List<MusicData> getMusicDataList(Context context) {
        if(datas.size() == 0)
            loadMusicDataList(context);
        return datas;
    }

    private static void loadMusicDataList(Context context) {
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

                musicData.setImageuri(getAlbumImageUri(musicData.getAlbum_id()));
                musicData.setUri(getMusicUri(musicData.getId()));

                datas.add(musicData);
            }
        }
    }

    private static Uri getMusicUri(String music_id){
        Uri content_uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(content_uri, music_id);
    }

    public static Uri getAlbumImageUri(String album_id) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), Long.parseLong(album_id));
    }

    public static Bitmap getAlbumImageBitmapbyID(Context context, String album_id) {
        Uri uri = getAlbumImageUri(album_id);
        ContentResolver resolver = context.getContentResolver();
        InputStream inputStream = null;
        try {
            inputStream = resolver.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (FileNotFoundException e) {
            Logger.logE(TAG, e.getMessage());
        }
        return null;
    }

    public static void setImageViewByGlide(Context context, ImageView imageView, MusicData musicData) {
        Glide.with(context)
                .load(musicData.getImageuri())
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}