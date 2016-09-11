package com.mymiu.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
import com.mymiu.WebActivity;
import com.mymiu.YueDanDetailActivity;
import com.mymiu.model.mvdata.VideoBean;

import java.util.ArrayList;


/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/5/10
 * YinYueTai
 */
public class MHomeRecycleAdapter extends RecyclerView.Adapter<MHomeRecycleAdapter.FirstHolder> {


    private final ThreadLocal<ArrayList<VideoBean>> firstPageBeanList = new ThreadLocal<>();
    private Activity activity;
    private LinearLayout.LayoutParams localLayoutParams;
    int mWidth, mHeight;

    public MHomeRecycleAdapter(ArrayList<VideoBean> firstPageBeanList, Activity activity, int mWidth, int mHeight) {
        this.firstPageBeanList.set(firstPageBeanList);
        this.activity = activity;
        localLayoutParams = new LinearLayout.LayoutParams(mWidth, mHeight);
        this.mWidth = mWidth;
        this.mHeight = mHeight;
    }

    @Override
    public FirstHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mhome_recycler_item, parent, false);
        return new FirstHolder(view);
    }

    @Override
    public void onBindViewHolder(FirstHolder holder, int position) {
        final VideoBean pageBean = firstPageBeanList.get().get(position);
        holder.homeRec_item_transbg.setLayoutParams(new RelativeLayout.LayoutParams(mWidth, mHeight));
        holder.homeRecommendItemTitle.setText(pageBean.getTitle());
        holder.homeRecommendItemDescription.setText(pageBean.getDescription());
        final int tag;
        String type = pageBean.getType();
        //设置tab标签是 首播.mv.活动  等等
        if ("ACTIVITY".equalsIgnoreCase(type)) {//打开页面
            tag = 0;
            holder.homeRecommendItemType.setImageResource(R.drawable.home_page_activity);
        } else if ("VIDEO".equalsIgnoreCase(type)) {//首播，点击进去显示MV描述，相关MV
            tag = 1;
            holder.homeRecommendItemType.setImageResource(R.drawable.home_page_video);
        } else if ("WEEK_MAIN_STAR".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 2;
            holder.homeRecommendItemType.setImageResource(R.drawable.home_page_star);
        } else if ("PLAYLIST".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 3;
            holder.homeRecommendItemType.setImageResource(R.drawable.home_page_playlist);
        } else if ("AD".equalsIgnoreCase(type)) {
            tag = 4;
            holder.homeRecommendItemType.setImageResource(R.drawable.home_page_ad);
        } else if ("PROGRAM".equalsIgnoreCase(type)) {//跳到MV详情
            tag = 5;
            holder.homeRecommendItemType.setImageResource(R.drawable.home_page_program);
        } else if ("bulletin".equalsIgnoreCase(type)) {
            tag = 6;
            holder.homeRecommendItemType.setImageResource(R.drawable.home_page_bulletin);
        } else if ("fanart".equalsIgnoreCase(type)) {
            tag = 7;
            holder.homeRecommendItemType.setImageResource(R.drawable.home_page_fanart);
        } else if ("live".equalsIgnoreCase(type)) {
            tag = 8;
            holder.homeRecommendItemType.setImageResource(R.drawable.home_page_live);
        } else if ("LIVENEW".equalsIgnoreCase(type)|| ("LIVENEWLIST".equals(type))) {
            tag = 9;
            holder.homeRecommendItemType.setImageResource(R.drawable.home_page_live_new);
        } else if ("INVENTORY".equalsIgnoreCase(pageBean.getType())){//打开页面
            tag = 10;
            holder.homeRecommendItemType.setImageResource(R.drawable.home_page_project);
        }else {
            tag = -100;
            holder.homeRecommendItemType.setImageResource(0);
        }
        //设置背景图片
        holder.homeRecommendItemJpg.setLayoutParams(localLayoutParams);
        holder.homeRecommendItemJpg.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(activity).load(pageBean.getPosterPic()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.homeRecommendItemJpg);
        holder.item_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                switch (tag) {
                    case 0:
                    case 4:
                    case 10:
                        intent.setClass(activity, WebActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", pageBean.getUrl());
                        intent.putExtras(bundle);
                        break;
                    case 1:
                    case 5:
                    case 7:
                        intent.setClass(activity, MVDetailActivity.class);
                        intent.putExtra("id", pageBean.getId());
                        break;
                    case 2:
                    case 3:
                        intent.setClass(activity, YueDanDetailActivity.class);
                        intent.putExtra("id", pageBean.getId());
                        break;
                    default:
                        return;
                }
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return firstPageBeanList.get().size();
    }

    static class FirstHolder extends RecyclerView.ViewHolder {
        ImageView homeRec_item_transbg;
        ImageView homeRecommendItemJpg;
        ImageView homeRecommendItemType;
        TextView  homeRecommendItemTitle;
        TextView  homeRecommendItemDescription;
        RelativeLayout item_root;
        public FirstHolder(View itemView) {
            super(itemView);
            homeRec_item_transbg=(ImageView)itemView.findViewById(R.id.home_recommend_item_transbg);
            homeRecommendItemJpg=(ImageView)itemView.findViewById(R.id.home_recommend_item_jpg);
            homeRecommendItemType=(ImageView)itemView.findViewById(R.id.home_recommend_item_type);
            homeRecommendItemTitle=(TextView)itemView.findViewById(R.id.home_recommend_item_title);
            homeRecommendItemDescription=(TextView)itemView.findViewById(R.id.home_recommend_item_description);
            item_root=(RelativeLayout)itemView.findViewById(R.id.item_root);
        }
    }
}
