package com.mymiu.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.MediaStore;
import android.widget.RemoteViews;


import com.mymiu.MainActivity;
import com.mymiu.MusicApp;
import com.mymiu.R;
import com.mymiu.model.MusicEntity;
import com.mymiu.model.PlayerAction;
import com.mymiu.model.PlayerBuff;

import java.util.List;

public class MPlayerService extends Service implements AudioManager.OnAudioFocusChangeListener {
    private MediaPlayer mediaPlayer;//媒体播放器对象
    private PlayerBinder playerBinder;
    private int current = 0;//播放歌曲在列表的位置
    private PlayerBuff state;
    private List<MusicEntity> musicList;
    private MusicApp musicApp;

    private Handler handler;
    private NotificationManager manager;
    private Notification.Builder builder;
    private Notification notification;

   private  AudioManager audioManager;
    public MPlayerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return playerBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer=new MediaPlayer();
        playerBinder=new PlayerBinder();

        musicApp=(MusicApp)getApplication();

        handler=new Handler();
        musicList=musicApp.getMusicEntityList();

        manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder=new Notification.Builder(this);
        //注册广播
        IntentFilter filter=new IntentFilter();
        filter.addAction(PlayerAction.INTENT_ACTION_LAST);
        filter.addAction(PlayerAction.INTENT_ACTION_NEXT);
        filter.addAction(PlayerAction.INTENT_ACTION_PAUSE);
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        filter.addAction("DELETE");
        registerReceiver(new ServiceBroadcastReceiver(), filter);
        //设置音乐播放结束时的监听
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (state == null) {
                    state = PlayerBuff.STATE_REPEAT;
                    playerBinder.next();
                } else if (state == PlayerBuff.STATE_REPEAT) {
                    playerBinder.next();
                } else if (state == PlayerBuff.STATE_SINGLE) {
                    mp.start();
                } else if (state == PlayerBuff.STATE_SHUFFL) {
                    int n = (int) (Math.random() * musicList.size());
                    if (n == current) {
                        n = (int) (Math.random() * musicList.size());
                    }
                    playerBinder.play(n, 0);
                }
            }
        });
        audioManager= (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);
        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // could not get audio focus.
           }
    }


    public void createNotification(){
        Intent intent=new Intent(this, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        RemoteViews remoteViews=new RemoteViews(this.getPackageName(), R.layout.notification);
        remoteViews.setImageViewResource(R.id.notification_icon, R.drawable.default_head);
        remoteViews.setTextViewText(R.id.notification_name, musicList.get(current).getName());
        remoteViews.setTextViewText(R.id.notification_singer, musicList.get(current).getSinger());
        remoteViews.setImageViewResource(R.id.notification_last, R.drawable.last);
        remoteViews.setImageViewResource(R.id.notification_pause,R.drawable.pause);
        remoteViews.setImageViewResource(R.id.notification_next,R.drawable.next);
        remoteViews.setImageViewResource(R.id.notification_delete, R.drawable.delete);

        //设置监听
        Intent pauseIntent=new Intent(PlayerAction.INTENT_ACTION_PAUSE);
        PendingIntent intent1=PendingIntent.getBroadcast(this,0,pauseIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_pause, intent1);

        Intent lastIntent=new Intent(PlayerAction.INTENT_ACTION_LAST);
        PendingIntent intent2=PendingIntent.getBroadcast(this, 0, lastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_last,intent2);

        Intent nextIntent=new Intent(PlayerAction.INTENT_ACTION_NEXT);
        PendingIntent intent3=PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_next, intent3);

        Intent deleteIntent=new Intent("DELETE");
        PendingIntent intent4=PendingIntent.getBroadcast(this, 0, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notification_delete, intent4);

        builder.setSmallIcon(R.drawable.default_head);
        notification=builder.build();
        notification.contentView=remoteViews;
        notification.bigContentView = remoteViews;
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        manager.notify(1001,notification);

    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
               if (!mediaPlayer.isPlaying()) mediaPlayer.start();
                mediaPlayer.setVolume(1.0f, 1.0f);
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                if (mediaPlayer.isPlaying()) mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (mediaPlayer.isPlaying()) mediaPlayer.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (mediaPlayer.isPlaying()) mediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }

    public  class  PlayerBinder extends Binder {
        //设置音乐实体和播放进度
        public  boolean play(int position,int currentTime){
            try {
                current=position;
                mediaPlayer.reset();// 把各项参数恢复到初始状态
                mediaPlayer.setDataSource(musicList.get(position).getUrl());
                mediaPlayer.prepare(); // 进行缓冲
               // Log.i("println", "max" + mediaPlayer.getDuration());
                //设置监听，当音乐准备好时开始播放
                mediaPlayer.setOnPreparedListener(new PreparedListener(currentTime));
                createNotification();
                handler.postDelayed(mRunable, 1000);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * 暂停音乐
         */
        public void pause() {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                handler.removeCallbacks(mRunable);
                mediaPlayer.pause();
            }
        }

        public void last(){
            if (musicList != null) {
                if (current == 0) {
                    current = musicList.size() - 1;
                } else {
                    current--;
                }
                playerBinder.play(current, 0);
            }
        }

        public void next(){
            current++;
            if (current > musicList.size() - 1) {
                current = 0;
            }
            playerBinder.play(current,0);
        }

        public void resume() {
            mediaPlayer.start();
            handler.postDelayed(mRunable, 1000);
        }


        public MediaPlayer getMediaPlayer(){
            return mediaPlayer;
        }

        public void setState(PlayerBuff buff){
            state=buff;

        }
    }
    /**
     *
     * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
     *
     */
    private final class PreparedListener implements MediaPlayer.OnPreparedListener {
        private int currentTime;

        public PreparedListener(int currentTime) {
            this.currentTime = currentTime;
        }

        @Override
        public void onPrepared(final MediaPlayer mp) {
            if (currentTime > 0) { // 如果音乐不是从头播放
                mp.seekTo(currentTime);
            }
            mp.start();
            //获取歌曲时间,和歌曲名发送广播
            Bundle bundle=new Bundle();
            bundle.putInt("max", mp.getDuration());
            bundle.putString("name", musicList.get(current).getName());
            final Intent intent=new Intent(PlayerAction.INTENT_ACTION_MAXTIME_NOWMUSIC);
            intent.putExtras(bundle);
            sendBroadcast(intent);
        }
    }
    Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent();
            intent.setAction(PlayerAction.INTENT_ACTION_NOWTIME);
            intent.putExtra("nowtime", mediaPlayer.getCurrentPosition());
            sendBroadcast(intent);
            handler.postDelayed(mRunable, 1000);
        }
    } ;
    public class ServiceBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(PlayerAction.INTENT_ACTION_NEXT)){
                playerBinder.next();
            }else  if (intent.getAction().equals(PlayerAction.INTENT_ACTION_LAST)){
                playerBinder.last();
            }else if (intent.getAction().equals(PlayerAction.INTENT_ACTION_PAUSE)){
                if (mediaPlayer.isPlaying()) {
                    playerBinder.pause();
                } else {
                    playerBinder.resume();
                }
            }else if(intent.getAction().equals("DELETE")){
                manager.cancel(1001);
            }else if(intent.getAction().equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY)){
                playerBinder.pause();
            }
        }
    }

    @Override
    public void onDestroy() {
//        Log.i("println", "onDestroy");
//        SharedPreferences preferences=getSharedPreferences("music", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=preferences.edit();
//        editor.putString("name", musicList.get(current).getName());
//        editor.putInt("max", mediaPlayer.getDuration());
//        editor.putInt("now", mediaPlayer.getCurrentPosition());
//        editor.putInt("current", current);
//        Log.i("println", "name"+musicList.get(current).getName()+"  max"+mediaPlayer.getDuration()+" now"+mediaPlayer.getCurrentPosition()+"  current"+current);
//        editor.commit();
        super.onDestroy();
    }
}
