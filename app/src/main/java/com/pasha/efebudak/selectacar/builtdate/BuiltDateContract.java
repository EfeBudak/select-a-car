package com.pasha.efebudak.selectacar.builtdate;

import android.support.v4.util.ArrayMap;

import com.pasha.efebudak.selectacar.BasePresenter;
import com.pasha.efebudak.selectacar.BaseView;

/**
 * Created by efebudak on 10/05/2017.
 */

public interface BuiltDateContract {

    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {
        void showDateItems(ArrayMap<String, String> dateItems);
    }
}
