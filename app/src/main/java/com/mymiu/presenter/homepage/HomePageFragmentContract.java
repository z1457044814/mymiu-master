package com.mymiu.presenter.homepage;


import com.mymiu.model.mvdata.VideoBean;
import com.mymiu.presenter.BasePresenter;
import com.mymiu.presenter.BaseView;

import java.util.ArrayList;


public interface HomePageFragmentContract {
    interface Presenter extends BasePresenter {

    }
    interface View extends BaseView<Presenter> {
        void setData(ArrayList<VideoBean> dataList);
        void setError(String msg);
    }
}
