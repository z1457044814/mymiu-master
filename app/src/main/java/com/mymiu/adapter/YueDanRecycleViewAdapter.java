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
import com.mymiu.R;
import com.mymiu.YueDanDetailActivity;
import com.mymiu.model.mvdata.YueDanBean;
import com.mymiu.myview.CircleImageView;


import java.util.List;



/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/5/11
 * YinYueTai
 */
public class YueDanRecycleViewAdapter extends RecyclerView.Adapter<YueDanRecycleViewAdapter.YueDanHolder> {

    static class YueDanHolder extends RecyclerView.ViewHolder {

        RelativeLayout itemRoot;

        ImageView posterImg;

        CircleImageView profileImage;

        TextView title;

        TextView author;

        TextView playCount;

        ImageView itemTransbg;

        public YueDanHolder(View itemView) {
            super(itemView);
            itemRoot=(RelativeLayout)itemView.findViewById(R.id.item_root);
            posterImg=(ImageView)itemView.findViewById(R.id.poster_img);
            itemTransbg=(ImageView)itemView.findViewById(R.id.item_transbg);
            profileImage=(CircleImageView)itemView.findViewById(R.id.profile_image);
            title=(TextView)itemView.findViewById(R.id.title);
            author=(TextView)itemView.findViewById(R.id.author);
            playCount=(TextView)itemView.findViewById(R.id.play_count);

        }
    }

    private List<YueDanBean.PlayListsBean> playLists;
    private Activity activity;
    private RelativeLayout.LayoutParams layoutParams;

    public YueDanRecycleViewAdapter(List<YueDanBean.PlayListsBean> playLists, Activity activity, int mWidth, int mHeight) {
        this.playLists = playLists;
        this.activity = activity;
        layoutParams = new RelativeLayout.LayoutParams(mWidth, mHeight);
    }

    @Override
    public YueDanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yue_dan_item, parent, false);
        return new YueDanHolder(view);
    }

    @Override
    public void onBindViewHolder(YueDanHolder holder, int position) {
        final YueDanBean.PlayListsBean playListsBean = playLists.get(position);
        holder.posterImg.setLayoutParams(layoutParams);
        holder.itemTransbg.setLayoutParams(layoutParams);
        holder.title.setText(playListsBean.getTitle());
        holder.author.setText(playListsBean.getCreator().getNickName());
        holder.playCount.setText("收录高清MV" + playListsBean.getVideoCount() + "首");
        Glide.with(activity).load(playListsBean.getPlayListBigPic()).into(holder.posterImg);
        Glide.with(activity).load(playListsBean.getCreator().getLargeAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.profileImage);
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(activity, YueDanDetailActivity.class);
                intent.putExtra("id", playListsBean.getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }
}
