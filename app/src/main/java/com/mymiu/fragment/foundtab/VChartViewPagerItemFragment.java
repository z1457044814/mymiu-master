package com.mymiu.fragment.foundtab;


import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;


import com.mylhyl.crlayout.SwipeRefreshAdapterView;
import com.mylhyl.crlayout.SwipeRefreshRecyclerView;
import com.mymiu.R;
import com.mymiu.adapter.VCharRecycleViewAdapter;
import com.mymiu.base.BaseFragment;
import com.mymiu.model.mvdata.VChartPeriod;
import com.mymiu.model.mvdata.VideoBean;
import com.mymiu.presenter.mchart.VChartPagerItemContract;
import com.mymiu.presenter.mchart.VChartPagerItemPresenter;

import java.util.ArrayList;
import java.util.List;




public class VChartViewPagerItemFragment extends BaseFragment implements VChartPagerItemContract.View,SwipeRefreshLayout.OnRefreshListener{


    SwipeRefreshRecyclerView recyclerView;

    private String areaCode;

    private List<VChartPeriod.PeriodsBean> periodsBeanArrayList;
    private List<VideoBean> videosBeen = new ArrayList<>();
    private VCharRecycleViewAdapter viewAdapter;

    private List<Integer> years;

    private SparseArray<List<VChartPeriod.PeriodsBean>> sparseArray;
    private VChartPagerItemContract.Presenter presenter;

    private int dateCode;

    public static Fragment newInstance(String areaCode) {
        VChartViewPagerItemFragment vChartViewPagerItemFragment = new VChartViewPagerItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("areaCode", areaCode);
        vChartViewPagerItemFragment.setArguments(bundle);
        return vChartViewPagerItemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.vchart_viewpager_fragment, container, false);
            observerView(360,640);
            initView();
            new VChartPagerItemPresenter(this);
           // presenter.getPeriod(areaCode);
            recyclerView.autoRefresh(R.color.colorDefault);
        }

        return rootView;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        areaCode = getArguments().getString("areaCode");
    }
    private void initView() {
        recyclerView=(SwipeRefreshRecyclerView)rootView.findViewById(R.id.vchar_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setOnRefreshListener(this);
        viewAdapter = new VCharRecycleViewAdapter(getActivity(), videosBeen,mWidth,mHeight);
        recyclerView.setAdapter(viewAdapter);
    }


    @Override
    public void setAreaData(VChartPeriod vChartPeriod) {
        //获取v榜周期
        periodsBeanArrayList = vChartPeriod.getPeriods();
        //取得最新周期
        VChartPeriod.PeriodsBean currentPeriodsBean = periodsBeanArrayList.get(0);
        presenter.getDataByPeriod(areaCode, currentPeriodsBean.getDateCode());
        years = vChartPeriod.getYears();
        sparseArray = new SparseArray<>();
        int index=0;
        for (Integer integer : years){
            List<VChartPeriod.PeriodsBean> list = new ArrayList<>();
            for (VChartPeriod.PeriodsBean p : periodsBeanArrayList){
                if (integer == p.getYear()){
                    list.add(p);
                }
            }
            sparseArray.put(index,list);
            index++;
        }
    }

    @Override
    public void setVideoData(List<VideoBean> videoBeen) {

        if (refresh){
            refresh = false;
            videosBeen.clear();
        }
        videosBeen.addAll(videoBeen);
        viewAdapter.notifyDataSetChanged();
        recyclerView.setRefreshing(false);
    }

    @Override
    public void setError(String msg) {
        if (refresh){
            refresh = false;
            Toast.makeText(getActivity(),"刷新失败",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void setPresenter(VChartPagerItemContract.Presenter presenter) {
        this.presenter = presenter;
    }



    @Override
    public void onRefresh() {
        refresh = true;
        presenter.getDataByPeriod(areaCode, dateCode);
    }
}
