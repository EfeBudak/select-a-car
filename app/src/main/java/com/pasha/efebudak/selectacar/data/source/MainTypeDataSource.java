package com.pasha.efebudak.selectacar.data.source;

import com.pasha.efebudak.selectacar.data.GenericPage;

import io.reactivex.Single;

/**
 * Created by efebudak on 09/05/2017.
 */

public interface MainTypeDataSource {
    Single<GenericPage> getMainTypePage(final String manufacturerId, int page, int pageSize, boolean refreshRequired);
}
