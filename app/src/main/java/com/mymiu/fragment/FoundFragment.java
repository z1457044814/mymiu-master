package com.mymiu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mymiu.R;
import com.mymiu.fragment.foundtab.MVFragment;
import com.mymiu.fragment.foundtab.RecommendFragment;
import com.mymiu.fragment.foundtab.VChartFragment;
import com.mymiu.fragment.foundtab.YueDanFragment;
import com.mymiu.myview.mpager.CustomPagerSlidingTabStrip;
import com.mymiu.myview.mpager.ViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoundFragment extends Fragment {

    private CustomPagerSlidingTabStrip mPagerTab;
    private ViewPager mViewPager;
    private LayoutInflater mInflater;

    private static final int VIEW_FIRST 		= 0;
    private static final int VIEW_SECOND	    = 1;
    private static final int VIEW_THIRD       = 2;
    private static final int VIEW_FOURTH    = 3;

    private static final int VIEW_SIZE = 4;

    private RecommendFragment recommendFragment;
    private MVFragment mvFragment;
    private VChartFragment mFocusFragment;
    private YueDanFragment mListFragment;

    public FoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_found, container, false);
        mPagerTab=(CustomPagerSlidingTabStrip)view.findViewById(R.id.found_top_tab);
        mViewPager=(ViewPager)view.findViewById(R.id.found_tab_content);
        initTab();
        return view;
    }

    private void initTab(){
        mViewPager.setOffscreenPageLimit(VIEW_SIZE);
        mViewPager.setAdapter(new FoundFragmentAdapter(getFragmentManager()));
        mPagerTab.setViewPager(mViewPager);
        mPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public  class FoundFragmentAdapter extends FragmentStatePagerAdapter implements CustomPagerSlidingTabStrip.CustomTabProvider{

        public FoundFragmentAdapter(FragmentManager fm) {
            super(fm);
            mInflater = LayoutInflater.from(getContext());
        }

        @Override
        public Fragment getItem(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        if(null == recommendFragment)
                           recommendFragment= RecommendFragment.newInstance();
                        return recommendFragment;

                    case VIEW_SECOND:
                        if(null == mvFragment)
                            mvFragment=new MVFragment();
                        return mvFragment;

                    case VIEW_THIRD:
                        if(null == mFocusFragment)
                            mFocusFragment=new VChartFragment();
                        return mFocusFragment;

                    case VIEW_FOURTH:
                        if(null == mListFragment)
                            mListFragment=new YueDanFragment();
                        return mListFragment;
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return VIEW_SIZE;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        return "MHome";
                    case  VIEW_SECOND:
                        return  "MV";
                    case  VIEW_THIRD:
                        return   "M榜";
                    case  VIEW_FOURTH:
                        return   "M单";
                    default:
                        break;
                }
            }
            return null;
        }

        @Override
        public View getSelectTabView(int position, View convertView) {
            if (convertView == null){
                convertView = mInflater.inflate(R.layout.custom_select_tab, null);
            }

            TextView tv = ViewHolder.get(convertView, R.id.tvTab);

            tv.setText(getPageTitle(position));

            return convertView;
        }

        @Override
        public View getDisSelectTabView(int position, View convertView) {
            if (convertView == null){
                convertView = mInflater.inflate(R.layout.custom_disselect_tab, null);
            }

            TextView tv = ViewHolder.get(convertView, R.id.tvTab);

            tv.setText(getPageTitle(position));

            return convertView;
        }
    }

}
