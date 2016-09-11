package com.mymiu.fragment.yuedantab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymiu.R;
import com.mymiu.model.mvdata.YueDanDetailBean;


/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/5/23
 * YinYueTai
 */
public class YueDanDescribeFragment extends Fragment {

    TextView artistName;
    TextView author;
    TextView updateDate;
    TextView playCount;
    TextView playPcCount;
    TextView playMobileCount;
    TextView describe;

    private View rootView;
    private boolean hasLoadOnce;

    public static YueDanDescribeFragment newInstance(YueDanDetailBean yueDanDetailBean){
        YueDanDescribeFragment fragment = new YueDanDescribeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("yueDanDetailBean",yueDanDetailBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.yuedan_describe_layout, container, false);
        }
        if (!hasLoadOnce){
            initView();
            hasLoadOnce = true;
        }
        return rootView;
    }

    private void initView(){
        artistName=(TextView)rootView.findViewById(R.id.artistName);
        author=(TextView)rootView.findViewById(R.id.author);
        updateDate=(TextView)rootView.findViewById(R.id.update_date);
        playCount=(TextView)rootView.findViewById(R.id.play_count);
        playPcCount=(TextView)rootView.findViewById(R.id.play_pc_count);
        playMobileCount=(TextView)rootView.findViewById(R.id.play_mobile_count);
        describe=(TextView)rootView.findViewById(R.id.describe);
        Bundle bundle = getArguments();
        YueDanDetailBean yueDanDetailBean = bundle.getParcelable("yueDanDetailBean");
        artistName.setText(yueDanDetailBean.getTitle());
        author.setText("作者："+yueDanDetailBean.getCreator().getNickName());
        updateDate.setText("更新时间："+yueDanDetailBean.getUpdateTime());
        playCount.setText("播放次数："+yueDanDetailBean.getTotalViews());
        playPcCount.setText("收藏次数：" + yueDanDetailBean.getTotalFavorites());
        playMobileCount.setText("获得积分：" + yueDanDetailBean.getIntegral());
        describe.setText(yueDanDetailBean.getDescription());
    }

}
