package com.pasha.efebudak.selectacar.data.source;

import com.pasha.efebudak.selectacar.data.GenericPage;

import io.reactivex.Single;

/**
 * Created by efebudak on 05/05/2017.
 */

public interface ManufacturerDataSource {
    Single<GenericPage> getManufacturerPage(int page, int pageSize, boolean refreshRequired);
}
