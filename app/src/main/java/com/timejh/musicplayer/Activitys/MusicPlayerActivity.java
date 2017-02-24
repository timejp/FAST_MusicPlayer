package com.timejh.musicplayer.Activitys;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.timejh.musicplayer.Adapters.MusicPlayerAdapter;
import com.timejh.musicplayer.Managers.MusicManager;
import com.timejh.musicplayer.R;

import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity {

    private final String TAG = "MusicPlayerActivity";

    private static final int IDLE = 0;
    private static final int PLAY = 1;
    private static final int PAUSE = 2;

    public MusicPlayerAdapter musicPlayerManager;

    private ViewPager viewPager;
    private ImageButton bt_play, bt_back, bt_next;
    private SeekBar musicSeekbar;
    private TextView tvMusicTime;
    private TextView tvMusicMaxTime;

    private static MediaPlayer player;

    private static int PLAYSTATUS = IDLE;
    private static int musicPosition = -1;

    private boolean musicThreadEnable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        init();

        playStartup();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicThreadEnable = false;
    }

    private void init() {
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        uiInit();

        listenerInit();

        valueInit();

        adapterInit();
    }

    private void uiInit() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        musicSeekbar = (SeekBar) findViewById(R.id.musicSeekbar);
        tvMusicTime = (TextView) findViewById(R.id.tvMusicTime);
        tvMusicMaxTime = (TextView) findViewById(R.id.tvMusicMaxTime);
        bt_back = (ImageButton) findViewById(R.id.bt_back);
        bt_play = (ImageButton) findViewById(R.id.bt_play);
        bt_next = (ImageButton) findViewById(R.id.bt_next);
    }

    private void listenerInit() {
        bt_back.setOnClickListener(clickListener);
        bt_play.setOnClickListener(clickListener);
        bt_next.setOnClickListener(clickListener);

        musicSeekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.setPageTransformer(false, pageTransformer);
    }

    private void adapterInit() {
        viewPager.setAdapter(musicPlayerManager);
    }

    private void valueInit() {
        musicPlayerManager = new MusicPlayerAdapter(this);
        musicPlayerManager.set(MusicManager.getMusicDataList(this));
    }

    private void playStartup() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            int receivedPosition = bundle.getInt("musicPosition");
            if (receivedPosition == 0) {
                PLAYSTATUS = IDLE;
                musicPosition = 0;
                play();
            } else {
                viewPager.setCurrentItem(receivedPosition);
            }
        }

        musicThread.start();
    }

    private void play() {
        switch (PLAYSTATUS) {
            case IDLE:
                playIDLE();
                break;
            case PLAY:
                playPLAY();
                break;
            case PAUSE:
                playPAUSE();
                break;
        }
        updateUiStatus();
    }

    private void playerReset() {
        Uri uri = musicPlayerManager.getMusicData(musicPosition).getUri();
        if (player != null) {
            player.release();
        }
        player = MediaPlayer.create(this, uri);
        player.setOnCompletionListener(completionListener);
        player.setLooping(false);
    }

    private void playIDLE() {
        playerReset();

        player.start();
        PLAYSTATUS = PLAY;
    }

    private void playPLAY() {
        player.pause();
        PLAYSTATUS = PAUSE;
    }

    private void playPAUSE() {
        player.start();
        PLAYSTATUS = PLAY;
    }

    private void back() {
        if (player == null)
            return;
        int position = musicPosition;
        switch (PLAYSTATUS) {
            case IDLE:
                if (position > 0)
                    position--;
                break;
            case PLAY:
            case PAUSE:
                if (musicSeekbar.getProgress() < 2000) {
                    if (position > 0)
                        position--;
                    else
                        position = musicPlayerManager.getCount();
                } else {
                    PLAYSTATUS = IDLE;
                    play();
                    return;
                }
        }
        viewPager.setCurrentItem(position);
    }

    private void next() {
        if (player == null)
            return;
        int position = musicPosition;
        if (position < musicPlayerManager.getCount() - 1) {
            position++;
        } else {
            position = 0;
        }
        viewPager.setCurrentItem(position);
    }

    public static void stop() {
        if (player != null) {
            player.release();
        }
    }

    private void updateUiStatus() {
        switch (PLAYSTATUS) {
            case IDLE:
                bt_play.setImageResource(android.R.drawable.ic_media_play);
                break;
            case PAUSE:
                bt_play.setImageResource(android.R.drawable.ic_media_play);
                break;
            case PLAY:
                bt_play.setImageResource(android.R.drawable.ic_media_pause);
                break;
        }

        long time = player.getDuration();
        musicSeekbar.setMax((int) time);
        tvMusicMaxTime.setText(timeString(time));
    }

    private String timeString(long time) {
        long MIN = TimeUnit.MILLISECONDS.toMinutes(time);
        long SEC = TimeUnit.MILLISECONDS.toSeconds(time - (MIN * 60000));
        return String.format("%02d:%02d", MIN, SEC);
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser && player != null) {
                player.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            next();
        }
    };

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (musicPosition == position) {
                updateUiStatus();
            } else {
                PLAYSTATUS = IDLE;
                musicPosition = position;
                play();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
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

    private ViewPager.PageTransformer pageTransformer = new ViewPager.PageTransformer() {

        @Override
        public void transformPage(View page, float position) {
            //현재 Page의 위치가 조금이라도 바뀔때마다 호출되는 메소드
            //첫번째 파라미터 : 현재 존재하는 View 객체들 중에서 위치가 변경되고 있는 View들
            //두번째 파라미터 : 각 View 들의 상대적 위치( 0.0 ~ 1.0 : 화면 하나의 백분율)

            //           1.현재 보여지는 Page의 위치가 0.0
            //           Page가 왼쪽으로 이동하면 값이 -됨. (완전 왼쪽으로 빠지면 -1.0)
            //           Page가 오른쪽으로 이동하면 값이 +됨. (완전 오른쪽으로 빠지면 1.0)

            //주의할 것은 현재 Page가 이동하면 동시에 옆에 있는 Page(View)도 이동함.
            //첫번째와 마지막 Page 일때는 총 2개의 View가 메모리에 만들어져 잇음.
            //나머지 Page가 보여질 때는 앞뒤로 2개의 View가 메모리에 만들어져 총 3개의 View가 instance 되어 있음.
            //ViewPager 한번에 1장의 Page를 보여준다면 최대 View는 3개까지만 만들어지며
            //나머지는 메모리에서 삭제됨.-리소스관리 차원.

            //position 값이 왼쪽, 오른쪽 이동방향에 따라 음수와 양수가 나오므로 절대값 Math.abs()으로 계산
            //position의 변동폭이 (-2.0 ~ +2.0) 사이이기에 부호 상관없이 (0.0~1.0)으로 변경폭 조절
            //주석으로 수학적 연산을 설명하기에는 한계가 있으니 코드를 보고 잘 생각해 보시기 바랍니다.
            float normalizedposition = Math.abs( 1 - Math.abs(position) );

            page.setAlpha(normalizedposition);  //View의 투명도 조절
            page.setScaleX(normalizedposition/2 + 0.5f); //View의 x축 크기조절
            page.setScaleY(normalizedposition/2 + 0.5f); //View의 y축 크기조절
            page.setRotationY(position * 80); //View의 Y축(세로축) 회전 각도
        }
    };

    private Thread musicThread = new Thread() {
        @Override
        public void run() {
            try {
                while (musicThreadEnable) {
                    if (PLAYSTATUS == PLAY && player != null) {
                        Thread.sleep(1000);
                        final long time = player.getCurrentPosition();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                musicSeekbar.setProgress((int) time);
                                tvMusicTime.setText(timeString(time));
                            }
                        });
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
