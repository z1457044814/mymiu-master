package com.mymiu.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mymiu.MVDetailActivity;
import com.mymiu.R;
import com.mymiu.model.mvdata.MVDetailBean;
import com.mymiu.presenter.yuedan.PlayVideoListener;

import java.util.List;

public class RelativeMvRecycleAdapter extends RecyclerView.Adapter<RelativeMvRecycleAdapter.ViewHolder> {

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
    private List<MVDetailBean.RelatedVideosBean> relatedVideosBeen;

    public RelativeMvRecycleAdapter(Activity activity, List<MVDetailBean.RelatedVideosBean> relatedVideosBeen) {
        this.activity = activity;
        this.relatedVideosBeen = relatedVideosBeen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.relative_mv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MVDetailBean.RelatedVideosBean videosBean = relatedVideosBeen.get(position);
        holder.artistName.setText(videosBean.getArtistName());
        holder.playCount.setText("播放次数：" + videosBean.getTotalViews());
        holder.title.setText(videosBean.getTitle());
        Glide.with(activity).load(videosBean.getPosterPic()).placeholder(R.drawable.logo).centerCrop().into(holder.posterImg);
        holder.itemRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(activity, MVDetailActivity.class);
                intent.putExtra("id", videosBean.getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return relatedVideosBeen.size();
    }
}
