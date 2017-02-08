package com.timejh.musicplayer.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timejh.musicplayer.Datas.Music;
import com.timejh.musicplayer.Managers.MusicManager;
import com.timejh.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tokijh on 2017. 2. 2..
 */

public class MusicPlayerAdapter extends PagerAdapter {

    private List<Music> musics;
    private Context context;
    private LayoutInflater inflater;

    public MusicPlayerAdapter(Context context) {
        musics = new ArrayList<>();
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add(Music music) {
        musics.add(music);
        this.notifyDataSetChanged();
    }

    public void set(List<Music> musics) {
        this.musics = musics;
        this.notifyDataSetChanged();
    }

    public Music getMusicData(int position) {
        return musics.get(position);
    }

    @Override
    public int getCount() {
        return musics.size();
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

        tv_title.setText(musics.get(position).getTitle());
        tv_artist.setText(musics.get(position).getArtist());
        MusicManager.setMusicImageByGlide(context, iv_image, musics.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}
