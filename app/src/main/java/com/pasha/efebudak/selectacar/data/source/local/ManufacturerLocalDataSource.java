package com.pasha.efebudak.selectacar.data.source.local;

import android.support.v4.util.ArrayMap;

import com.pasha.efebudak.selectacar.data.GenericPage;
import com.pasha.efebudak.selectacar.data.source.ManufacturerDataSource;

import io.reactivex.Single;

/**
 * Created by efebudak on 05/05/2017.
 */

public class ManufacturerLocalDataSource implements ManufacturerDataSource {

    private static ManufacturerLocalDataSource INSTANCE;

    public static ManufacturerLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ManufacturerLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Single<GenericPage> getManufacturerPage(int page, int pageSize, boolean refreshRequired) {
        final ArrayMap<String, String> manufacturers = new ArrayMap<>();
        manufacturers.put("111", "audi");
        manufacturers.put("112", "bmv");
        manufacturers.put("113", "mercedes");
        manufacturers.put("114", "opel");
        GenericPage manufacturerPage = new GenericPage(0, 0, 0, manufacturers);
        return Single.just(manufacturerPage);
    }
}
