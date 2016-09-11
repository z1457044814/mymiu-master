package com.mymiu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mymiu.fragment.yuedantab.YueDanCommandFragment;
import com.mymiu.fragment.yuedantab.YueDanDescribeFragment;
import com.mymiu.fragment.yuedantab.YueDanListFragment;
import com.mymiu.http.OkHttpManager;
import com.mymiu.http.callback.StringCallBack;
import com.mymiu.model.URLProviderUtil;
import com.mymiu.model.mvdata.YueDanDetailBean;
import com.mymiu.presenter.yuedan.PlayVideoListener;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import okhttp3.Call;

public class YueDanDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private JCVideoPlayerStandard videoplayer;
    private ImageView yuedanDescribe;
    private ImageView yuedanComment;
    private ImageView yuedanList;

    private int id;
    YueDanDetailBean yueDanDetailBean;

    private YueDanDescribeFragment describeFragment;
    private YueDanCommandFragment commandFragment;
    private YueDanListFragment yueDanListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yuedan_detail_layout);
        id = getIntent().getIntExtra("id", -10);
        initView();
        getData();
    }

    private void initView(){
        videoplayer=(JCVideoPlayerStandard)findViewById(R.id.video_player);
        yuedanDescribe=(ImageView)findViewById(R.id.yuedan_describe);
        yuedanComment=(ImageView)findViewById(R.id.yuedan_comment);
        yuedanList=(ImageView)findViewById(R.id.yuedan_list);
        yuedanComment.setOnClickListener(this);
        yuedanDescribe.setOnClickListener(this);
        yuedanList.setOnClickListener(this);
    }
    private void getData() {
        OkHttpManager.getOkHttpManager().asyncGet(URLProviderUtil.getPeopleYueDanList(id), this, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                Toast.makeText(YueDanDetailActivity.this, "获取数据失败.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {

                try {
                    yueDanDetailBean = new Gson().fromJson(response, YueDanDetailBean.class);
                    videoplayer.setUp(yueDanDetailBean.getVideos().get(0).getHdUrl(), JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, yueDanDetailBean.getVideos().get(0).getTitle());
                    videoplayer.startButton.performClick();
                    describeFragment = YueDanDescribeFragment.newInstance(yueDanDetailBean);
                    commandFragment=YueDanCommandFragment.newInstance();
                    yueDanListFragment = YueDanListFragment.newInstance(yueDanDetailBean);
                    yueDanListFragment.setPlayVideoListener(playVideoListener);
                    setFragment(describeFragment);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                    Toast.makeText(YueDanDetailActivity.this, "error:" + response, Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yuedan_describe:
                setImageBackground(yuedanDescribe,R.drawable.player_yue_p);
                setImageBackground(yuedanComment,R.drawable.player_yue_comment);
                setImageBackground(yuedanList, R.drawable.player_yuelist);
                setFragment(describeFragment);
                break;
            case R.id.yuedan_comment:
                setImageBackground(yuedanDescribe,R.drawable.player_yue);
                setImageBackground(yuedanComment,R.drawable.player_yue_comment_p);
                setImageBackground(yuedanList,R.drawable.player_yuelist);
                setFragment(commandFragment);
                break;
            case R.id.yuedan_list:
                setImageBackground(yuedanDescribe,R.drawable.player_yue);
                setImageBackground(yuedanComment,R.drawable.player_yue_comment);
                setImageBackground(yuedanList, R.drawable.player_yuelist_p);
                setFragment(yueDanListFragment);
                break;
        }
    }

    private void setImageBackground(ImageView imageView,int resId){
        imageView.setBackgroundResource(resId);
    }

    private PlayVideoListener playVideoListener = new PlayVideoListener() {
        @Override
        public void playVideo(String url,String title) {
            videoplayer.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST,title);
            videoplayer.startButton.performClick();
        }
    };
}
