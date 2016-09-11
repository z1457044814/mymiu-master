package com.mymiu.fragment.foundtab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mymiu.R;
import com.mymiu.adapter.MVViewPagerAdapter;
import com.mymiu.model.mvdata.AreaBean;
import com.mymiu.presenter.mvpage.MVFragmentContract;
import com.mymiu.presenter.mvpage.MVFragmentPresenter;

import java.util.ArrayList;



/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/5/10
 * YinYueTai
 */
public class MVFragment extends Fragment implements MVFragmentContract.View{


    TabLayout tabLayout;

    ViewPager mvPager;

    private View rootView;
    private MVViewPagerAdapter pagerAdapter;

    ArrayList<MVViewPagerItemFragment> fragments = new ArrayList<>();
    private MVFragmentContract.Presenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.mv_page_fragment, container, false);
            initView();
            new MVFragmentPresenter(this);
            presenter.getData(0,0);
        }
        return rootView;
    }
    private void initView(){
            tabLayout=(TabLayout)rootView.findViewById(R.id.tabLayout);
            mvPager=(ViewPager)rootView.findViewById(R.id.mv_pager);
    }

    private void initViewPager(ArrayList<MVViewPagerItemFragment> fragments,ArrayList<AreaBean> areaBeanArrayList){
        pagerAdapter = new MVViewPagerAdapter(getFragmentManager(),fragments,areaBeanArrayList);
        mvPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mvPager);
    }

    @Override
    public void setPresenter(MVFragmentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setData(ArrayList<AreaBean> areaBeanArrayList) {
        for (AreaBean area :
                areaBeanArrayList) {
            fragments.add(MVViewPagerItemFragment.getInstance(area.getCode()));
        }
        initViewPager(fragments,areaBeanArrayList);
    }


    @Override
    public void setError(String msg) {
        Toast.makeText(getActivity(),"获取数据失败:"+msg,Toast.LENGTH_SHORT).show();
    }

}
