package com.mymiu.http.callback;

import okhttp3.Response;


public abstract class StringCallBack extends CallBack<String> {
    @Override
    public String parseNetworkResponse(Response response) throws Exception {
        return response.body().string();
    }
}
