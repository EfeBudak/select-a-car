package com.pasha.efebudak.selectacar.manufacturer;

import com.pasha.efebudak.selectacar.BasePaginationPresenter;
import com.pasha.efebudak.selectacar.BasePaginationView;

/**
 * Created by efebudak on 04/05/2017.
 */

interface ManufacturerContract {

    interface Presenter extends BasePaginationPresenter {
    }

    interface View extends BasePaginationView<Presenter> {
        boolean isActive();
    }
}
