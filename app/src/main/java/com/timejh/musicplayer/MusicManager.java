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

/**
 * Created by tokijh on 2017. 2. 1..
 */

public class MusicManager {

    private static final String TAG = "MusicManager";
    private static ArrayList<MusicData> datas = new ArrayList<>();

    public static ArrayList<MusicData> getMusicDataList(Context context) {
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

                musicData.print();
                datas.add(musicData);
            }
        }
    }

    public static Uri getAlbumImageUri(String album_id) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), Long.parseLong(album_id));
    }

    public static Bitmap getAlbumImageBitmap(Context context, String album_id) {
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

    public static void setImageViewByGlide(Context context, ImageView imageView, String album_id) {
        Glide.with(context)
                .load(MusicManager.getAlbumImageUri(album_id))
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}