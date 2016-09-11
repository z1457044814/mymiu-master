package com.mymiu.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mymiu.R;
import com.mymiu.model.mvdata.YueDanDetailBean;
import com.mymiu.presenter.yuedan.PlayVideoListener;

import java.util.List;

public class YueDanDetailRecycleViewAdapter extends RecyclerView.Adapter<YueDanDetailRecycleViewAdapter.ViewHolder>{
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        TextView artistName;

        TextView playCount;

        ImageView posterImg;

        LinearLayout itemRoot;

        public ViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            artistName=(TextView)itemView.findViewById(R.id.artistName);
            playCount=(TextView)itemView.findViewById(R.id.play_count);
            posterImg=(ImageView)itemView.findViewById(R.id.poster_img);
            itemRoot=(LinearLayout)itemView.findViewById(R.id.item_root);

        }
    }
    private Activity activity;
    private List<YueDanDetailBean.VideosBean> videosBeanList;
    private PlayVideoListener playVideoListener;

    public YueDanDetailRecycleViewAdapter(Activity activity, List<YueDanDetailBean.VideosBean> videosBeanList, PlayVideoListener playVideoListener) {
        this.activity = activity;
        this.videosBeanList = videosBeanList;
        this.playVideoListener = playVideoListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.relative_mv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final YueDanDetailBean.VideosBean videosBean = videosBeanList.get(position);
        holder.artistName.setText(videosBean.getArtistName());
        holder.playCount.setText("播放次数：" + videosBean.getTotalViews());
        holder.title.setText(videosBean.getTitle());
        Glide.with(activity).load(videosBean.getPosterPic()).placeholder(R.drawable.logo).centerCrop().into(holder.posterImg);
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playVideoListener.playVideo(videosBean.getHdUrl(),videosBean.getTitle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return videosBeanList.size();
    }
}
