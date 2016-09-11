package com.mymiu.presenter.yuedan;


import com.mymiu.model.mvdata.YueDanBean;
import com.mymiu.presenter.BasePresenter;
import com.mymiu.presenter.BaseView;

import java.util.List;

public interface YueDanFragmentContract {
    interface Presenter extends BasePresenter {

    }
    interface View extends BaseView<Presenter> {
        void setData(List<YueDanBean.PlayListsBean> data);
        void setError(String msg);

        void showProgress(boolean flag);

    }
}
