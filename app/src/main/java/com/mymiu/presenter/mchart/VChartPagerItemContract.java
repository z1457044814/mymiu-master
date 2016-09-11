package com.mymiu.presenter.mchart;


import com.mymiu.model.mvdata.VChartPeriod;
import com.mymiu.model.mvdata.VideoBean;
import com.mymiu.presenter.BasePresenter;
import com.mymiu.presenter.BaseView;

import java.util.List;

public interface VChartPagerItemContract {
    interface Presenter extends BasePresenter {
        void getPeriod(String areaCode);
        void getDataByPeriod(String area, int dateCode);
    }
    interface View extends BaseView<Presenter> {
        void setAreaData(VChartPeriod vChartPeriod);
        void setVideoData(List<VideoBean> videosBeen);
        void setError(String msg);
    }
}
