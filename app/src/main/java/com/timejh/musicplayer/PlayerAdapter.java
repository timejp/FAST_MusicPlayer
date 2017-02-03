package com.timejh.musicplayer;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tokijh on 2017. 2. 2..
 */

public class PlayerAdapter extends PagerAdapter {

    private ArrayList<MusicData> musicDatas;
    private Context context;
    private LayoutInflater inflater;

    public PlayerAdapter(Context context) {
        musicDatas = new ArrayList<>();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add(MusicData musicData) {
        musicDatas.add(musicData);
        this.notifyDataSetChanged();
    }

    public void set(ArrayList<MusicData> musicDatas) {
        this.musicDatas = musicDatas;
        this.notifyDataSetChanged();
    }

    public MusicData getMusicData(int position) {
        return musicDatas.get(position);
    }

    @Override
    public int getCount() {
        return musicDatas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = inflater.inflate(R.layout.player_card_item, null);
        ImageView iv_image = (ImageView) view.findViewById(R.id.iv_image);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_artist = (TextView) view.findViewById(R.id.tv_artist);

        tv_title.setText(musicDatas.get(position).getTitle());
        tv_artist.setText(musicDatas.get(position).getArtist());
        MusicManager.setImageViewByGlide(context, iv_image, musicDatas.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
