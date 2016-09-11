package com.mymiu.fragment.mvplayertab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mymiu.R;
import com.mymiu.model.mvdata.MVDetailBean;
import com.mymiu.myview.CircleImageView;


public class MVDescribeFragment extends Fragment {

    CircleImageView profileImage;

    TextView artistName;
    TextView playCount;
    TextView playPcCount;
    TextView playMobileCount;
    TextView describe;
    private View rootView;

    private MVDetailBean mvDetailBean;

    public static MVDescribeFragment newInstance(MVDetailBean mvDetailBean){
        MVDescribeFragment fragment = new MVDescribeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("mvDetailBean",mvDetailBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.mv_describe_layout, container, false);
        }
        profileImage=(CircleImageView)rootView.findViewById(R.id.profile_image);
        artistName=(TextView)rootView.findViewById(R.id.artistName);
        playCount=(TextView)rootView.findViewById(R.id.play_count);
        playPcCount=(TextView)rootView.findViewById(R.id.play_pc_count);
        playMobileCount=(TextView)rootView.findViewById(R.id.play_mobile_count);
        describe=(TextView)rootView.findViewById(R.id.describe);
        initData();
        return rootView;
    }
    private void initData(){
        artistName.setText(mvDetailBean.getArtistName());
        playCount.setText("播放次数："+String.valueOf(mvDetailBean.getTotalViews()));
        playPcCount.setText("pc端："+String.valueOf(mvDetailBean.getTotalPcViews()));
        playMobileCount.setText("移动端：" + String.valueOf(mvDetailBean.getTotalMobileViews()));
        describe.setText(mvDetailBean.getDescription());
        Glide.with(this).load(mvDetailBean.getArtists().get(0).getArtistAvatar()).placeholder(R.drawable.default_head).centerCrop().into(profileImage);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvDetailBean = getArguments().getParcelable("mvDetailBean");
    }

}
