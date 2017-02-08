package com.timejh.musicplayer.Managers;

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
import com.timejh.musicplayer.Datas.Music;
import com.timejh.musicplayer.R;
import com.timejh.musicplayer.Utils.Logger;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tokijh on 2017. 2. 1..
 */

public class MusicManager {

    private static final String TAG = "MusicManager";

    private final static Uri URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private final static String PROJECTIONS[] = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST
    };

    private static List<Music> datas = new ArrayList<>();

    public static List<Music> getMusicDataList(Context context) {
        if (datas.size() == 0)
            loadMusicList(context);
        return datas;
    }

    private static void loadMusicList(Context context) {
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(URI, PROJECTIONS, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                datas.add(getMusicData(cursor));
            }
        }
    }

    private static Music getMusicData(Cursor cursor) {
        Music music = new Music();

        music.setId(getValue(cursor, PROJECTIONS[0]));
        music.setAlbum_id(getValue(cursor, PROJECTIONS[1]));
        music.setTitle(getValue(cursor, PROJECTIONS[2]));
        music.setArtist(getValue(cursor, PROJECTIONS[3]));

        music.setImageuri(getAlbumImageUri(music.getAlbum_id()));
        music.setUri(getMusicUri(music.getId()));

        return music;
    }

    private static String getValue(Cursor cursor, String projection) {
        int index = cursor.getColumnIndex(projection);
        return cursor.getString(index);
    }

    private static Uri getMusicUri(String music_id) {
        Uri content_uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        return Uri.withAppendedPath(content_uri, music_id);
    }

    public static Uri getAlbumImageUri(String album_id) {
        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), Long.parseLong(album_id));
    }

    public static Bitmap getAlbumImageBitmapByID(Context context, String album_id) {
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

    public static void setMusicImageByGlide(Context context, ImageView imageView, Music music) {
        Glide.with(context)
                .load(music.getImageuri())
                .placeholder(R.mipmap.ic_launcher)
                .into(imageView);
    }
}