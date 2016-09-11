package com.mymiu.fragment.foundtab;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.mymiu.adapter.MVRecycleViewAdapter;
import com.mymiu.base.BaseFragment;
import com.mymiu.model.mvdata.VideoBean;
import com.mymiu.presenter.mvpage.MVItemFragmentContract;
import com.mymiu.presenter.mvpage.MVItemFragmentPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/5/11
 * YinYueTai
 */
public class MVViewPagerItemFragment extends BaseFragment implements MVItemFragmentContract.View,SwipeRefreshLayout.OnRefreshListener,SwipeRefreshAdapterView.OnListLoadListener{

    public static MVViewPagerItemFragment getInstance(String areaCode) {
        MVViewPagerItemFragment mvViewPagerItemFragment = new MVViewPagerItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("areaCode", areaCode);
        mvViewPagerItemFragment.setArguments(bundle);
        return mvViewPagerItemFragment;
    }
    private String areaCode;
    SwipeRefreshRecyclerView mvRecyclerView;
    MVRecycleViewAdapter recycleViewAdapter;
    private ArrayList<VideoBean> videosList = new ArrayList<>();
    private MVItemFragmentContract.Presenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("setUserVisibleHint", "onCreateView=" + areaCode);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.mv_viewpager_item_fragment, container, false);
            initView();
            Log.i("println", "mv" + recycleViewAdapter.getItemCount());
            observerView(360, 640);
            new MVItemFragmentPresenter(this);
           // presenter.getData(mOffset,SIZE,areaCode);
            mvRecyclerView.autoRefresh(R.color.colorDefault);
        }

        return rootView;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        areaCode = bundle.getString("areaCode");
    }

    private void initView() {
        mvRecyclerView=(SwipeRefreshRecyclerView)rootView.findViewById(R.id.mv_recycler);
        recycleViewAdapter = new MVRecycleViewAdapter(videosList,getContext(),mWidth,mHeight);
        mvRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mvRecyclerView.setOnListLoadListener(this);
        mvRecyclerView.setOnRefreshListener(this);
        mvRecyclerView.setAdapter(recycleViewAdapter);

    }


    @Override
    public void setData(List<VideoBean> videoList) {
        if (refresh){
            refresh = false;
            mOffset = 0;
            this.videosList.clear();
        }
        if (videoList == null || videoList.size() == 0) {
            hasMore = false;
        } else {
            hasMore = true;

        }
        videosList.addAll(videoList);
        recycleViewAdapter.notifyDataSetChanged();
        mOffset += videoList.size();
        mvRecyclerView.setRefreshing(false);
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
    public void setPresenter(MVItemFragmentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onListLoad() {
        if(hasMore){
            presenter.getData(mOffset + 1, SIZE,areaCode);
            mvRecyclerView.setLoading(false);
        }
    }

    @Override
    public void onRefresh() {
        refresh=true;
        presenter.getData(0, SIZE, areaCode);

    }
}
