package com.mymiu.fragment.foundtab;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import com.mylhyl.crlayout.SwipeRefreshAdapterView;
import com.mylhyl.crlayout.SwipeRefreshRecyclerView;
import com.mymiu.R;
import com.mymiu.adapter.YueDanRecycleViewAdapter;
import com.mymiu.base.BaseFragment;
import com.mymiu.model.mvdata.YueDanBean;
import com.mymiu.presenter.yuedan.YueDanFragmentContract;
import com.mymiu.presenter.yuedan.YueDanFragmentPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/5/10
 * YinYueTai
 */
public class YueDanFragment extends BaseFragment implements YueDanFragmentContract.View,SwipeRefreshLayout.OnRefreshListener,SwipeRefreshAdapterView.OnListLoadListener{

    SwipeRefreshRecyclerView recyclerView;

    private YueDanRecycleViewAdapter recycleViewAdapter;
    List<YueDanBean.PlayListsBean> playLists = new ArrayList<>();
    private View rootView;
    private YueDanFragmentContract.Presenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.common_recycleview_layout, container, false);
            observerView(360, 640);
            initView();
            new YueDanFragmentPresenter(this);
            presenter.getData(mOffset, SIZE);
            recyclerView.autoRefresh(R.color.colorDefault);
        }
        return rootView;
    }

    private void initView() {
        recyclerView=(SwipeRefreshRecyclerView)rootView.findViewById(R.id.yuedan_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleViewAdapter = new YueDanRecycleViewAdapter(playLists, getActivity(),mWidth,mHeight);
        recyclerView.setOnListLoadListener(this);
        recyclerView.setOnRefreshListener(this);
        recyclerView.setAdapter(recycleViewAdapter);
    }

    @Override
    public void setData(List<YueDanBean.PlayListsBean> data) {
        showProgress(false);
        if (refresh){
            refresh = false;
            playLists.clear();
            mOffset = 0;
        }
        if (data == null || data.size() == 0) {
            hasMore = false;
        } else {
            hasMore = true;
            int pos = playLists.size() - 1;
            playLists.addAll(data);
            recycleViewAdapter.notifyItemRangeChanged(pos, data.size());
            mOffset += data.size();
        }
    }

    @Override
    public void setError(String msg) {
        showProgress(false);
        if (refresh){
            refresh = false;
            Toast.makeText(getActivity(),"刷新失败",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setPresenter(YueDanFragmentContract.Presenter presenter) {
        this.presenter = presenter;
    }



    @Override
    public void showProgress(boolean flag) {
        recyclerView.setRefreshing(flag);
    }

    @Override
    public void onListLoad() {

        if(hasMore){
            presenter.getData(mOffset + 1, SIZE);
            recyclerView.setLoading(false);
        }
    }

    @Override
    public void onRefresh() {
        refresh=true;
        presenter.getData(0, SIZE);
    }
}
