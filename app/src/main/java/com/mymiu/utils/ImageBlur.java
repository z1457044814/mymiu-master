package com.mymiu.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.enrique.stackblur.StackBlurManager;

import java.io.IOException;
import java.io.InputStream;

/****
 * 异步处理图片加载  模糊
 *
 *
 *
 */
public class ImageBlur {

    private Drawable drawable;
    public  void imageFactory(final Context context, final String strName,final int arg,final View view){

        @SuppressLint("HandlerLeak")
            final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    drawable=(Drawable)msg.obj;
                    view.setBackground(drawable);
                }

            }
        };

        class MyThread implements Runnable{

            @Override
            public void run() {

                try {
                    AssetManager assetManager = context.getAssets();
                    Drawable drawable=null;
                    InputStream istr;
                    final StackBlurManager stackBlurManager;
                    Bitmap bitmap = null;
                    Message message;
                    istr = assetManager.open(strName);
                    //获取位图
                    //对图片进行模糊处理
                    stackBlurManager=new StackBlurManager(BitmapFactory.decodeStream(istr));
                    //设置模糊值
                    bitmap= stackBlurManager.process(arg);
                    drawable = new BitmapDrawable(bitmap);
                    message=Message.obtain();
                    message.obj=drawable;
                    message.what=1;
                    handler.sendMessage(message);
                    Log.i("println","entity");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("println","null");
                }
            }
        }
        new Thread(new MyThread()).start();
    }

}
