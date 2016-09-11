package com.mymiu.presenter.yuedan;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mymiu.http.OkHttpManager;
import com.mymiu.http.callback.StringCallBack;
import com.mymiu.model.URLProviderUtil;
import com.mymiu.model.mvdata.YueDanBean;

import okhttp3.Call;


public class YueDanFragmentPresenter implements YueDanFragmentContract.Presenter{
    private YueDanFragmentContract.View yueDanView;

    public YueDanFragmentPresenter(YueDanFragmentContract.View yueDanView) {
        this.yueDanView = yueDanView;
        this.yueDanView.setPresenter(this);
    }

    @Override
    public void getData(int offset, int size) {
        OkHttpManager.getOkHttpManager().asyncGet(URLProviderUtil.getMainPageYueDanUrl(offset, size), yueDanView, new StringCallBack() {
            @Override
            public void onError(Call call, Exception e) {
                yueDanView.setError(e.getLocalizedMessage());
            }

            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        YueDanBean yueDanBean = new Gson().fromJson(response, YueDanBean.class);
                        yueDanView.setData(yueDanBean.getPlayLists());
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                        yueDanView.setError(e.getMessage());
                    }
                }else {
                    yueDanView.setError("get data failed");
                }
            }
        });
    }
}
