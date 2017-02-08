package com.timejh.musicplayer;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.timejh.musicplayer.Adapter.MusicListView;
import com.timejh.musicplayer.Manager.Music;
import com.timejh.musicplayer.Utils.Message;

public class MusicListViewActivity extends AppCompatActivity {

    private final int REQ_CODE = 100;

    private RecyclerView recyclerView;
    private MusicListView recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        } else {
            loadData();
        }
    }

    private void init() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewAdapter = new MusicListView(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            String permArr[] = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permArr, REQ_CODE);
        } else {
            loadData();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadData();
            } else {
                Message.show(this, "권한을 허용하지 않으면 프로그램을 실행 할 수 없습니다.");
                finish();
            }
        }
    }

    private void loadData() {
        recyclerViewAdapter.set(Music.getMusicDataList(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicPlayerActivity.stop();
    }
}
