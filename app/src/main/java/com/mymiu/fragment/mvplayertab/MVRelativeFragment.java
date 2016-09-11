package com.mymiu.fragment.mvplayertab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mymiu.R;
import com.mymiu.adapter.RelativeMvRecycleAdapter;
import com.mymiu.model.mvdata.MVDetailBean;
import com.mymiu.myview.RecycleViewDivider;
import com.mymiu.presenter.yuedan.PlayVideoListener;


public class MVRelativeFragment extends Fragment {
    RecyclerView recyclerView;
    private MVDetailBean mvDetailBean;
    private View rootView;
    private boolean hasLoadOnce;
    private RelativeMvRecycleAdapter adapter;


    public static MVRelativeFragment newInstance(MVDetailBean mvDetailBean) {
        MVRelativeFragment fragment = new MVRelativeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("mvDetailBean", mvDetailBean);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mvDetailBean = getArguments().getParcelable("mvDetailBean");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.recycleview_layout, container, false);
        }
        if (!hasLoadOnce){
            hasLoadOnce = true;
            initData();
        }
        return rootView;
    }
    private void initData(){
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);
        adapter = new RelativeMvRecycleAdapter(getActivity(),mvDetailBean.getRelatedVideos());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL));
    }


}
