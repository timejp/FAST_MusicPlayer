package com.timejh.musicplayer;

import android.util.Log;

/**
 * Created by tokijh on 2017. 1. 26..
 */

public class Logger {
    public final static boolean DEBUG_MODE = true;//BuildConfig.DEBUG;

    public static void logD(String TAG, String message) {
        if (DEBUG_MODE)
            Log.d(TAG, message);
    }

    public static void logE(String TAG, String message) {
        if (DEBUG_MODE)
            Log.e(TAG, message);
    }
}
