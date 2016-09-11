package com.mymiu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import com.mylhyl.crlayout.SwipeRefreshWebView;
import com.mymiu.utils.ComWebViewClient;

public class WebActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SwipeRefreshWebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        toolbar=(Toolbar)findViewById(R.id.web_toolbar);
        webView=(SwipeRefreshWebView)findViewById(R.id.webview);
        toolbar.setNavigationIcon(R.drawable.back_vector);
        toolbar.setTitle("MyMiu");
        toolbar.setTitleTextColor(getResources().getColor(R.color.home_bar_text_push));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        webView.autoRefresh(R.color.colorDefault);
        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");
        WebView scrollView = webView.getScrollView();
        scrollView.setWebViewClient(new ComWebViewClient(scrollView));
        scrollView.loadUrl(url);
    }
}
