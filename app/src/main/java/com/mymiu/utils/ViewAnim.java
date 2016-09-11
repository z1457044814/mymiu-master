package com.mymiu.utils;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.mymiu.R;

import java.util.Timer;
import java.util.TimerTask;


public class ViewAnim {
    public static void createAnim(final View view) {
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                ImageView imageView = (ImageView) view;
                if (msg.what == 0) {
                    imageView.setImageResource(R.drawable.logo);
                    YoYo.with(Techniques.BounceInDown).duration(2500).delay(0).playOn(imageView);
                }
                if (msg.what >1) {
                    YoYo.with(Techniques.Tada).duration(1000).delay(0).playOn(imageView);
                }
            }
        };
        class AnimThread implements Runnable {
            int index = 0;
            boolean flag = true;

            @Override
            public void run() {
                new Timer().schedule(new TimerTask()//启动一个定时器
                {
                    public void run() {
                        handler.sendEmptyMessage(index++);
                    }
                }, 0, 5000);//每隔5秒启动定时器
            }
        }
        new Thread(new AnimThread()).start();
    }
}
