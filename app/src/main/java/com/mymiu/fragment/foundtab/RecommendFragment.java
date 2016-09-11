package com.mymiu.fragment.foundtab;


import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mylhyl.crlayout.SwipeRefreshAdapterView;
import com.mylhyl.crlayout.SwipeRefreshRecyclerView;
import com.mymiu.R;
import com.mymiu.adapter.MHomeRecycleAdapter;
import com.mymiu.base.BaseFragment;
import com.mymiu.model.mvdata.VideoBean;
import com.mymiu.presenter.homepage.HomePageFragmentContract;
import com.mymiu.presenter.homepage.HomePagePresenter;

import java.util.ArrayList;


public class RecommendFragment extends BaseFragment implements HomePageFragmentContract.View,SwipeRefreshLayout.OnRefreshListener,SwipeRefreshAdapterView.OnListLoadListener{

    private ArrayList<VideoBean> mHomeBeanList;

    private SwipeRefreshRecyclerView swipeRefreshRecyclerView;
    private HomePageFragmentContract.Presenter presenter;
    private MHomeRecycleAdapter adapter;

    public RecommendFragment() {
        // Required empty public constructor
    }

    public static RecommendFragment newInstance() {
        RecommendFragment fragment = new RecommendFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_recommend, container, false);
            swipeRefreshRecyclerView=(SwipeRefreshRecyclerView)rootView.findViewById(R.id.mhome_recycler);
            mHomeBeanList=new ArrayList<>();
            observerView(440,540);
            new HomePagePresenter(this);
            initView();
           // presenter.getData(mOffset, SIZE);
            swipeRefreshRecyclerView.autoRefresh(R.color.colorDefault);
        }
        return rootView;
    }

    private void initView(){
        adapter=new MHomeRecycleAdapter(mHomeBeanList,getActivity(),mWidth,mHeight);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        swipeRefreshRecyclerView.setLayoutManager(linearLayoutManager);
        swipeRefreshRecyclerView.setOnListLoadListener(this);
        swipeRefreshRecyclerView.setOnRefreshListener(this);
        swipeRefreshRecyclerView.setAdapter(adapter);
        swipeRefreshRecyclerView.setEmptyText("数据又没有了!");
    }
    @Override
    public void setData(ArrayList<VideoBean> dataList) {

        if (refresh){
            refresh = false;
            mHomeBeanList.clear();
            mOffset = 0;
        }
        if (dataList.size() > 0){
            hasMore = true;
        }else {
            hasMore = false;
        }
        mOffset += dataList.size();
        mHomeBeanList.addAll(dataList);
        adapter.notifyDataSetChanged();
        swipeRefreshRecyclerView.setRefreshing(false);
    }

    @Override
    public void setError(String msg) {
        swipeRefreshRecyclerView.setRefreshing(false);
        if (refresh){
            refresh = false;
            Toast.makeText(getActivity(), "刷新失败", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setPresenter(HomePageFragmentContract.Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void onListLoad() {
      //  swipeRefreshRecyclerView.setRefreshing(true);
        if(hasMore){
            presenter.getData(mOffset, SIZE);
            swipeRefreshRecyclerView.setLoading(false);
        }

    }

    @Override
    public void onRefresh() {
        refresh = true;
        presenter.getData(0, SIZE);
    }
}
