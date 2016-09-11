package com.mymiu.fragment.mymusic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mymiu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingerPager extends Fragment {


    public SingerPager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_singer_pager, container, false);
    }

}
