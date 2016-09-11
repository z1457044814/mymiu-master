package com.mymiu.fragment.foundtab;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.mymiu.presenter.mchart.VChartFragmentContract;
import com.mymiu.presenter.mchart.VChartFragmentPresenter;

import java.util.ArrayList;


public class VChartFragment extends Fragment implements  VChartFragmentContract.View{
    TabLayout tabLayout;
    ViewPager viewPager;
    private View rootView;
    private MVViewPagerAdapter pagerAdapter;
    private VChartFragmentContract.Presenter  presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView==null){
            rootView = inflater.inflate(R.layout.vchart_page_fragment, container, false);
            initView();
            new VChartFragmentPresenter(this);
            presenter.getData(0,0);
        }


        return rootView;
    }
    private void initView(){
        tabLayout=(TabLayout)rootView.findViewById(R.id.tabLayout);
        viewPager=(ViewPager)rootView.findViewById(R.id.view_pager);
    }
    private void initViewPager(ArrayList<Fragment> fragments,ArrayList<AreaBean> areaBeanArrayList){
        pagerAdapter = new MVViewPagerAdapter(getFragmentManager(),fragments,areaBeanArrayList);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setData(ArrayList<AreaBean> areaBeanArrayList) {
        ArrayList<Fragment> fragments = new ArrayList<>();
        for (AreaBean area :
                areaBeanArrayList) {
            fragments.add(VChartViewPagerItemFragment.newInstance(area.getCode()));
        }
        initViewPager(fragments,areaBeanArrayList);
    }

    @Override
    public void setError(String msg) {
        Toast.makeText(getActivity(),"获取数据失败",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(VChartFragmentContract.Presenter presenter) {
        this.presenter = presenter;
    }

}
