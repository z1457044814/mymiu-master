package com.mymiu;

import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.mymiu.model.MusicDataProvider;
import com.mymiu.model.MusicEntity;
import com.mymiu.service.MPlayerService;
import com.mymiu.utils.PinyinComparator;

import java.util.Collections;
import java.util.List;


public class MusicApp extends Application {

    private MPlayerService.PlayerBinder playerBinder;
    private List<MusicEntity> musicEntityList; //歌曲列表

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            playerBinder = (MPlayerService.PlayerBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MusicApp.this, "onServiceDisconnected name=" + name, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        startMainService();
        bindMainService();
        musicEntityList = new MusicDataProvider(getBaseContext()).getMusicData();

    }

    private void startMainService() {
        Intent it = new Intent(this, MPlayerService.class);
        startService(it);
    }

    public void stopMainService() {
        Intent it = new Intent(this, MPlayerService.class);
        stopService(it);
    }

    private void bindMainService() {
        Intent it = new Intent(this, MPlayerService.class);
        this.bindService(it, connection, Service.BIND_AUTO_CREATE);
    }

    public void unbindMainService() {
        this.unbindService(connection);
    }

    public MPlayerService.PlayerBinder getPlayerBinder() {
        return playerBinder;
    }

    public List<MusicEntity> getMusicEntityList(){
        //对音乐资源进行排序
        if(musicEntityList!=null){
            Collections.sort(musicEntityList, new PinyinComparator());
        };
        return musicEntityList;
    }

}
