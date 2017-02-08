package com.timejh.musicplayer.Manager;

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
import com.timejh.musicplayer.R;
import com.timejh.musicplayer.Utils.Logger;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tokijh on 2017. 2. 1..
 */

public class Music {

    private static final String TAG = "Music";
    private static List<com.timejh.musicplayer.Datas.Music> datas = new ArrayList<>();

    public static List<com.timejh.musicplayer.Datas.Music> getMusicDataList(Context context) {
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
                com.timejh.musicplayer.Datas.Music music = new com.timejh.musicplayer.Datas.Music();

                int index = cursor.getColumnIndex(projections[0]);
                music.setId(cursor.getString(index));
                index = cursor.getColumnIndex(projections[1]);
                music.setAlbum_id(cursor.getString(index));
                index = cursor.getColumnIndex(projections[2]);
                music.setTitle(cursor.getString(index));
                index = cursor.getColumnIndex(projections[3]);
                music.setArtist(cursor.getString(index));

                music.setImageuri(getAlbumImageUri(music.getAlbum_id()));
                music.setUri(getMusicUri(music.getId()));

                datas.add(music);
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

    public static void setImageViewByGlide(Context context, ImageView imageView, com.timejh.musicplayer.Datas.Music music) {
        Glide.with(context)
                .load(music.getImageuri())
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}