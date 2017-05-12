package com.pasha.efebudak.selectacar.data.source.local;

import com.pasha.efebudak.selectacar.data.GenericPage;
import com.pasha.efebudak.selectacar.data.source.MainTypeDataSource;

import io.reactivex.Single;

/**
 * Created by efebudak on 10/05/2017.
 */

public class MainTypeLocalDataSource implements MainTypeDataSource {

    private static MainTypeLocalDataSource INSTANCE;

    public static MainTypeLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainTypeLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Single<GenericPage> getMainTypePage(String manufacturerId, int page, int pageSize, boolean refreshRequired) {
        return null;
    }
}
