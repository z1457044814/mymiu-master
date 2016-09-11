package com.mymiu.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mymiu.MVDetailActivity;
import com.mymiu.R;
import com.mymiu.model.mvdata.VideoBean;


import java.util.ArrayList;
import java.util.List;



public class VCharRecycleViewAdapter extends RecyclerView.Adapter<VCharRecycleViewAdapter.ViewHolder> {

    private List<VideoBean> videosBeen = new ArrayList<>();
    private Activity activity;
    private RelativeLayout.LayoutParams layoutParams;

    public VCharRecycleViewAdapter(Activity activity, List<VideoBean> videosBeen, int mWidth, int mHeight) {
        this.activity = activity;
        this.videosBeen = videosBeen;
        layoutParams = new RelativeLayout.LayoutParams(mWidth, mHeight);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vchart_recycleview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VideoBean videoBean = videosBeen.get(position);
        holder.posterImg.setLayoutParams(layoutParams);
        holder.itemTransbg.setLayoutParams(layoutParams);
        holder.serialNumber.setText("" + (position + 1));
        holder.score.setText(videoBean.getScore());
        holder.author.setText(videoBean.getArtistName());
        holder.title.setText(videoBean.getTitle());
        Glide.with(activity).load(videoBean.getAlbumImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.posterImg);
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(activity, MVDetailActivity.class);
                intent.putExtra("id", videoBean.getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videosBeen.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView posterImg;

        TextView serialNumber;

        TextView score;

        TextView title;

        TextView author;

        ImageView itemTransbg;

        RelativeLayout itemRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            posterImg=(ImageView)itemView.findViewById(R.id.poster_img);
            itemTransbg=(ImageView)itemView.findViewById(R.id.item_transbg);
            serialNumber=(TextView)itemView.findViewById(R.id.serialNumber);
            score=(TextView)itemView.findViewById(R.id.score);
            title=(TextView)itemView.findViewById(R.id.title);
            author=(TextView)itemView.findViewById(R.id.author);
            itemRoot=(RelativeLayout)itemView.findViewById(R.id.item_root);
        }
    }
}
