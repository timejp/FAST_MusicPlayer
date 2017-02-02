package com.timejh.musicplayer;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class PlayerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ImageButton bt_play, bt_back, bt_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        bt_back = (ImageButton) findViewById(R.id.bt_back);
        bt_play = (ImageButton) findViewById(R.id.bt_play);
        bt_next= (ImageButton) findViewById(R.id.bt_next);
    }
}
