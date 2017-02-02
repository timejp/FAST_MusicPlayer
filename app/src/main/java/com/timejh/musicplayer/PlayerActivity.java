package com.timejh.musicplayer;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PlayerActivity extends AppCompatActivity {

    public static PlayerAdapter playerAdapter;

    private ViewPager viewPager;
    private ImageButton bt_play, bt_back, bt_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        bt_back = (ImageButton) findViewById(R.id.bt_back);
        bt_play = (ImageButton) findViewById(R.id.bt_play);
        bt_next = (ImageButton) findViewById(R.id.bt_next);

        bt_back.setOnClickListener(clickListener);
        bt_play.setOnClickListener(clickListener);
        bt_next.setOnClickListener(clickListener);

        playerAdapter = new PlayerAdapter(this);
        playerAdapter.set(MusicManager.getMusicDataList(this));
        viewPager.setAdapter(PlayerActivity.playerAdapter);

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            int position =  bundle.getInt("position");
            viewPager.setCurrentItem(position);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_back:
                    back();
                    break;
                case R.id.bt_play:
                    play();
                    break;
                case R.id.bt_next:
                    next();
                    break;
            }
        }
    };

    private void back() {

    }

    private void play() {

    }

    private void next() {

    }
}
