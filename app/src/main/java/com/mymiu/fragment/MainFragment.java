package com.mymiu.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.mymiu.MyMusicActivity;
import com.mymiu.R;
import com.mymiu.adapter.MainGridAdapter;
import com.mymiu.myview.LyricView;
import com.mymiu.utils.ImageBlur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xyz.sahildave.widget.SearchViewLayout;


public class MainFragment extends Fragment {
    private Integer[] mGridViewImage = {R.drawable.music_vector, R.drawable.download_vector, R.drawable.mv, R.drawable.like_vector};
    private Integer[] mGridViewText = {R.string.main_grid_music, R.string.main_grid_download, R.string.main_grid_mv, R.string.main_grid_like};

    private Toolbar toolbar;
    //private View main_bottom;

    public MainFragment() {
        // Required empty public constructor
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }
//    public void setLayout(View view){
//        main_bottom=view;
//    }


    public List<Map<String, Object>> resoures() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < mGridViewImage.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("image", mGridViewImage[i]);
            map.put("text", mGridViewText[i]);
            list.add(map);
        }
        return list;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        SearchViewLayout searchViewLayout = (SearchViewLayout) view.findViewById(R.id.search_view_container);
        LyricView lyricView=(LyricView)view.findViewById(R.id.lrc_bg);

        ImageBlur imageBlur = new ImageBlur();
        imageBlur.imageFactory(getActivity(), "lrcs.png", 40, lyricView);
        GridView gridView = (GridView) view.findViewById(R.id.main_gridview);
        searchViewLayout.setExpandedContentFragment(getActivity(), new SearchStaticScrollFragment());
        searchViewLayout.handleToolbarAnimation(toolbar);
        searchViewLayout.setCollapsedHint("搜索");
        searchViewLayout.setExpandedHint("请输入");

        ColorDrawable collapsed = new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.colorWhite));
        ColorDrawable expanded = new ColorDrawable(ContextCompat.getColor(getActivity(), R.color.default_color_expanded));

        searchViewLayout.setTransitionDrawables(collapsed, expanded);
       // searchViewLayout.setViewVisibility(main_bottom);
        gridView.setAdapter(new MainGridAdapter(getActivity(), resoures()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent=new Intent(getActivity(), MyMusicActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                }
            }
        });

        return view;
    }

}
