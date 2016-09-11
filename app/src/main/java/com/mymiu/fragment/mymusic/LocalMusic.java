package com.mymiu.fragment.mymusic;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mymiu.MusicApp;
import com.mymiu.R;
import com.mymiu.adapter.ContactAdapter;
import com.mymiu.model.MusicEntity;
import com.mymiu.service.MPlayerService;
import com.mymiu.utils.PinyinComparator;

import java.util.Collections;
import java.util.List;

import cc.solart.wave.WaveSideBarView;


public class LocalMusic extends Fragment {


    private RecyclerView recyclerView;
    private WaveSideBarView sideBarView;
    private List<MusicEntity> data;
    private ContactAdapter adapter;
    private MPlayerService.PlayerBinder playerBinder;

    public LocalMusic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_local_music, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.local_list);
        sideBarView = (WaveSideBarView) view.findViewById(R.id.side_bar);
        MusicApp app = (MusicApp) getActivity().getApplication();
        data = app.getMusicEntityList();
        playerBinder=app.getPlayerBinder();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ContactAdapter(data);
        recyclerView.setAdapter(adapter);
        sideBarView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                int pos = adapter.getPositionForSelection(letter.charAt(0));

                if (pos != -1) {
                    recyclerView.scrollToPosition(pos);
                    LinearLayoutManager mLayoutManager =
                            (LinearLayoutManager) recyclerView.getLayoutManager();
                    mLayoutManager.scrollToPositionWithOffset(pos, 0);
                }
            }
        });
        adapter.setOnItemClickListener(new ContactAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position, int itemCount) {

                playerBinder.play(position,0);

            }
        });
        adapter.setOnItemLongClickListener(new ContactAdapter.OnRecyclerViewItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position, int itemCount) {
                Toast.makeText(getContext(), position + "+" + itemCount + "长按", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }

}
