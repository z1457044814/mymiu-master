package com.mymiu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class NavSubActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        toolbar=(Toolbar)findViewById(R.id.subactivity_toolbar);
        toolbar.setNavigationIcon(R.drawable.back_vector);
        toolbar.setTitleTextColor(getResources().getColor(R.color.home_bar_text_push));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
       String tag= bundle.getString("tag");
        setTag(tag);
    }

    void setTag(String arg1){
        if (arg1.equals("user")){

            toolbar.setTitle("个人中心");
        }else if(arg1.equals("message")){

            toolbar.setTitle("消息中心");
        }else if(arg1.equals("download")){

            toolbar.setTitle("下载管理");
        }else if(arg1.equals("favorite")){

            toolbar.setTitle("我的收藏");
        }else if(arg1.equals("set")){

            toolbar.setTitle("设置");
        }
    }
}
