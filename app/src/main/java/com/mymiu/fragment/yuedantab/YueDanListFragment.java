package com.mymiu.fragment.yuedantab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mymiu.R;
import com.mymiu.adapter.YueDanDetailRecycleViewAdapter;
import com.mymiu.model.mvdata.YueDanDetailBean;
import com.mymiu.myview.RecycleViewDivider;
import com.mymiu.presenter.yuedan.PlayVideoListener;


public class YueDanListFragment extends Fragment {

    RecyclerView recyclerView;
    private View rootView;
    private boolean hasLoadOnce;

    public static YueDanListFragment newInstance(YueDanDetailBean yueDanDetailBean) {
        YueDanListFragment fragment = new YueDanListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("yueDanDetailBean", yueDanDetailBean);
        fragment.setArguments(bundle);
        return fragment;
    }
    private PlayVideoListener playVideoListener;

    public void setPlayVideoListener(PlayVideoListener playVideoListener) {
        this.playVideoListener = playVideoListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.recycleview_layout, container, false);
        }
        if (!hasLoadOnce){
            hasLoadOnce = true;
            initView();
        }
        return rootView;
    }
    private void initView(){
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);
        YueDanDetailBean yueDanDetailBean = getArguments().getParcelable("yueDanDetailBean");
        YueDanDetailRecycleViewAdapter adapter = new YueDanDetailRecycleViewAdapter(getActivity(),yueDanDetailBean.getVideos(),playVideoListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
    }
}
