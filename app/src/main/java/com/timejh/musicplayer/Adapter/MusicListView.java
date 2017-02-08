package com.timejh.musicplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.timejh.musicplayer.Datas.Music;
import com.timejh.musicplayer.MusicPlayerActivity;
import com.timejh.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tokijh on 2017. 2. 1..
 */

public class MusicListView extends RecyclerView.Adapter<MusicListView.CustomViewHolder> {

    private final String TAG = "MusicListView";

    List<Music> datas;
    Context context;

    public MusicListView(Context context) {
        datas = new ArrayList<>();
        this.context = context;
    }

    public void add(Music music) {
        datas.add(music);
        this.notifyDataSetChanged();
    }

    public void set(List<Music> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final Music music = datas.get(position);

        holder.tv_title.setText(music.getTitle());
        holder.tv_artist.setText(music.getArtist());
        holder.position = position;

        //holder.iv_image.setImageURI(getAlbumImageUri(music.getAlbum_id()));
//        Bitmap albumImage = getAlbumImageBitmap(music.getAlbum_id());
//        if (albumImage != null)
//            holder.iv_image.setImageBitmap(albumImage);
//        else
//            holder.iv_image.setImageResource(R.mipmap.ic_launcher);

        //로딩 라이브러리 Glide
//        Glide.with(context)
//                .load(Music.getAlbumImageUri(music.getAlbum_id()))
//                .placeholder(R.mipmap.ic_launcher)
//                .into(holder.iv_image);
        com.timejh.musicplayer.Manager.Music.setImageViewByGlide(context, holder.iv_image, music);

        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
        holder.cardView.setAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        TextView tv_artist;
        ImageView iv_image;
        CardView cardView;

        int position;

        public CustomViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_artist = (TextView) itemView.findViewById(R.id.tv_artist);
            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MusicPlayerActivity.class);
                    intent.putExtra("musicPosition", position);
                    context.startActivity(intent);
                }
            });
        }
    }
}