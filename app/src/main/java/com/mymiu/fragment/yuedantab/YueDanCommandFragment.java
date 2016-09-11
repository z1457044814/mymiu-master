package com.mymiu.fragment.yuedantab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mymiu.R;
import com.mymiu.model.mvdata.YueDanDetailBean;

/**
 * A simple {@link Fragment} subclass.
 */
public class YueDanCommandFragment extends Fragment {


    public YueDanCommandFragment() {
        // Required empty public constructor
    }

    public static YueDanCommandFragment newInstance(){
        YueDanCommandFragment fragment = new YueDanCommandFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yue_dan_command, container, false);
    }

}
