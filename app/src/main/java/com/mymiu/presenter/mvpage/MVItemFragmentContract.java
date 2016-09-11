package com.mymiu.presenter.mvpage;



import com.mymiu.model.mvdata.VideoBean;
import com.mymiu.presenter.BasePresenter;
import com.mymiu.presenter.BaseView;

import java.util.List;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/6/7
 * YinYueTai
 */
public interface MVItemFragmentContract {
    interface Presenter extends BasePresenter {
        void getData(int offset, final int size, String areaCode);
    }
    interface View extends BaseView<Presenter> {
        void setData(List<VideoBean> videosList);
        void setError(String msg);
    }
}
