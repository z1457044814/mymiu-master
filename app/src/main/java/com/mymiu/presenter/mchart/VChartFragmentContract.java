package com.mymiu.presenter.mchart;


import com.mymiu.model.mvdata.AreaBean;
import com.mymiu.presenter.BasePresenter;
import com.mymiu.presenter.BaseView;

import java.util.ArrayList;

public class VChartFragmentContract {
    public interface Presenter extends BasePresenter {

    }
    public interface View extends BaseView<Presenter> {
        void setData(ArrayList<AreaBean> areaBeanArrayList);
        void setError(String msg);
    }
}
