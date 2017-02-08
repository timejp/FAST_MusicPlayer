package com.timejh.musicplayer.Utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by tokijh on 2017. 2. 8..
 */

public class Message {
    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
