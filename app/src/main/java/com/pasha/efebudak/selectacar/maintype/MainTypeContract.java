package com.pasha.efebudak.selectacar.maintype;

import com.pasha.efebudak.selectacar.BasePaginationPresenter;
import com.pasha.efebudak.selectacar.BasePaginationView;

/**
 * Created by efebudak on 09/05/2017.
 */

interface MainTypeContract {

    interface Presenter extends BasePaginationPresenter {
    }

    interface View extends BasePaginationView<Presenter> {
    }
}
