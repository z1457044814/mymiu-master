package com.mymiu;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome);
        final Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what==1){
                    Intent intent =new Intent();
                    intent.setClass(WelcomeActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(0,0);
                }
            }
        };

        class welcomeThread implements Runnable{
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(1,3000);
            }
        }
        new Thread(new welcomeThread()).start();
    }

    @Override
    public void onBackPressed() {
    }
}
