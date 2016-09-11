package com.mymiu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.mymiu.fragment.mvplayertab.MVDescribeFragment;
import com.mymiu.fragment.mvplayertab.MVRelativeFragment;
import com.mymiu.fragment.yuedantab.YueDanCommandFragment;
import com.mymiu.http.OkHttpManager;
import com.mymiu.http.callback.StringCallBack;
import com.mymiu.model.URLProviderUtil;
import com.mymiu.model.mvdata.MVDetailBean;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;


public class MVDetailActivity extends AppCompatActivity {

    private int id;

    private JCVideoPlayerStandard videoplayer;
    private ImageView mvDescribe;
    private ImageView mvComment;
    private ImageView relativeMv;

    private MVDetailBean detailBean;

    private YueDanCommandFragment commandFragment;
    private MVDescribeFragment describeFragment;
    private MVRelativeFragment relativeMvFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mv_detail_layout);
        videoplayer=(JCVideoPlayerStandard)findViewById(R.id.videoplayer);
        mvDescribe=(ImageView)findViewById(R.id.mv_describe);
        mvComment=(ImageView)findViewById(R.id.mv_comment);
        relativeMv=(ImageView)findViewById(R.id.relative_mv);
        id = getIntent().getIntExtra("id", -10);
        mvDescribe.setOnClickListener(imageClickListener);
        mvComment.setOnClickListener(imageClickListener);
        relativeMv.setOnClickListener(imageClickListener);
        getData();
    }
    private View.OnClickListener imageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mv_describe:
                    setImageBackground(mvDescribe,R.drawable.player_mv_p);
                    setImageBackground(mvComment,R.drawable.player_comment);
                    setImageBackground(relativeMv, R.drawable.player_relative_mv);
                   setFragment(describeFragment);
                    break;
                case R.id.mv_comment:
                    setImageBackground(mvDescribe,R.drawable.player_mv);
                    setImageBackground(mvComment,R.drawable.player_comment_p);
                    setImageBackground(relativeMv,R.drawable.player_relative_mv);
                      setFragment(commandFragment);
                    break;
                case R.id.relative_mv:
                    setImageBackground(mvDescribe,R.drawable.player_mv);
                    setImageBackground(mvComment,R.drawable.player_comment);
                    setImageBackground(relativeMv, R.drawable.player_relative_mv_p);
                    setFragment(relativeMvFragment);
                    break;
            }
        }
    };
    private void setImageBackground(ImageView imageView,int resId){
        imageView.setBackgroundResource(resId);
    }
    private void getData() {

        OkHttpManager.getOkHttpManager().asyncGet(URLProviderUtil.getRelativeVideoListUrl(id), this, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(MVDetailActivity.this, "获取数据失败.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                detailBean = new Gson().fromJson(response, MVDetailBean.class);
                videoplayer.setUp(detailBean.getHdUrl(), JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, detailBean.getTitle());
                videoplayer.startButton.performClick();
                 describeFragment = MVDescribeFragment.newInstance(detailBean);
                commandFragment=YueDanCommandFragment.newInstance();
                relativeMvFragment = MVRelativeFragment.newInstance(detailBean);
                  setFragment(describeFragment);
            }
        });
    }

    private void setFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded() && fragment.isVisible()) {
            return;
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.replace(R.id.fragment_content, fragment);
        }
        transaction.commit();
    }



    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
