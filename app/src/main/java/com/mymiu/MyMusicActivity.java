package com.mymiu;

import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mymiu.fragment.mymusic.FolderPager;
import com.mymiu.fragment.mymusic.LocalMusic;
import com.mymiu.fragment.mymusic.SingerPager;
import com.mymiu.fragment.mymusic.SpecialPager;
import com.mymiu.myview.mpager.CustomPagerSlidingTabStrip;
import com.mymiu.myview.mpager.ViewHolder;

public class MyMusicActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private CustomPagerSlidingTabStrip mPagerTab;
    private ViewPager mViewPager;
    private LayoutInflater mInflater;

    private static final int VIEW_FIRST 		= 0;
    private static final int VIEW_SECOND	    = 1;
    private static final int VIEW_THIRD       = 2;
    private static final int VIEW_FOURTH    = 3;

    private static final int VIEW_SIZE = 4;

    private LocalMusic localMusicF;
    private SingerPager singerPagerF;
    private SpecialPager specialPagerF;
    private FolderPager folderPagerF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_music);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.back_vector);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mPagerTab=(CustomPagerSlidingTabStrip)findViewById(R.id.my_music_top_tab);
        mViewPager=(ViewPager)findViewById(R.id.my_music_tab_content);
        init();
    }

    private void init(){
        mViewPager.setOffscreenPageLimit(VIEW_SIZE);
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
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


    public  class FragmentAdapter extends FragmentStatePagerAdapter implements CustomPagerSlidingTabStrip.CustomTabProvider{

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
            mInflater = LayoutInflater.from(getBaseContext());
        }

        @Override
        public Fragment getItem(int position) {
            if(position >= 0 && position < VIEW_SIZE){
                switch (position){
                    case  VIEW_FIRST:
                        if(null == localMusicF)
                            localMusicF = new LocalMusic();
                        return localMusicF;

                    case VIEW_SECOND:
                        if(null == singerPagerF)
                            singerPagerF =new SingerPager();
                        return singerPagerF;

                    case VIEW_THIRD:
                        if(null == specialPagerF)
                            specialPagerF = new SpecialPager();
                        return specialPagerF;

                    case VIEW_FOURTH:
                        if(null == folderPagerF)
                            folderPagerF = new FolderPager();
                        return folderPagerF;
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
                        return  getResources().getString(R.string.my_music_tab_local);
                    case  VIEW_SECOND:
                        return   getResources().getString(R.string.my_music_tab_singer);
                    case  VIEW_THIRD:
                        return   getResources().getString(R.string.my_music_tab_special);
                    case  VIEW_FOURTH:
                        return   getResources().getString(R.string.my_music_tab_folder);
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
