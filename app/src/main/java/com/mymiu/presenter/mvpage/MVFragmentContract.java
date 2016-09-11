package com.mymiu.presenter.mvpage;



import com.mymiu.model.mvdata.AreaBean;
import com.mymiu.presenter.BasePresenter;
import com.mymiu.presenter.BaseView;

import java.util.ArrayList;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/6/7
 * YinYueTai
 */
public interface MVFragmentContract {
    interface Presenter extends BasePresenter {

    }
    interface View extends BaseView<Presenter> {
        void setData(ArrayList<AreaBean> areaBeanArrayList);
        void setError(String msg);
    }
}
