package com.mymiu.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mymiu.MVDetailActivity;
import com.mymiu.R;
import com.mymiu.model.mvdata.VideoBean;

import java.util.ArrayList;


/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/5/11
 * YinYueTai
 */
public class MVRecycleViewAdapter extends RecyclerView.Adapter<MVRecycleViewAdapter.ViewHolder> {

    private ArrayList<VideoBean> videoList = new ArrayList<>();
    private Context context;
    private LinearLayout.LayoutParams layoutParams;

    public MVRecycleViewAdapter(ArrayList<VideoBean> videoList, Context context, int mWidth, int mHeight) {
        this.videoList = videoList;
        this.context = context;
        layoutParams = new LinearLayout.LayoutParams(mWidth, mHeight);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mv_recycleview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VideoBean videoBean = videoList.get(position);
       // holder.posterImg.setLayoutParams(layoutParams);
       // holder.posterImg.setScaleType(ImageView.ScaleType.FIT_XY);
      //  holder.itemTransbg.setLayoutParams(layoutParams);
        holder.name.setText(videoBean.getTitle());
        holder.author.setText(videoBean.getDescription());
        if (videoBean.isAd()){
            Glide.with(context).load(videoBean.getThumbnailPic()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.posterImg);
            holder.playCount.setText("");
        }else {
            Glide.with(context).load(videoBean.getAlbumImg()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.posterImg);
            holder.playCount.setText("播放次数：" + videoBean.getTotalViews());

        }
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, MVDetailActivity.class);
                intent.putExtra("id", videoBean.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView posterImg;

        TextView name;

        TextView author;

        TextView playCount;


        RelativeLayout itemRoot;
        public ViewHolder(View itemView) {
            super(itemView);
            posterImg=(ImageView)itemView.findViewById(R.id.poster_mv_img);
            name=(TextView)itemView.findViewById(R.id.name);
            author=(TextView)itemView.findViewById(R.id.author);
            playCount=(TextView)itemView.findViewById(R.id.play_count);
            itemRoot=(RelativeLayout)itemView.findViewById(R.id.item_root);
        }
    }
}
