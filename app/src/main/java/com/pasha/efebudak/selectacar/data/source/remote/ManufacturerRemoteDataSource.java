package com.pasha.efebudak.selectacar.data.source.remote;

import android.support.annotation.Nullable;

import com.pasha.efebudak.selectacar.data.CarTypesService;
import com.pasha.efebudak.selectacar.data.GenericPage;
import com.pasha.efebudak.selectacar.data.source.ManufacturerDataSource;

import io.reactivex.Single;

/**
 * Created by efebudak on 05/05/2017.
 */

public class ManufacturerRemoteDataSource implements ManufacturerDataSource {

    @Nullable
    private static ManufacturerRemoteDataSource INSTANCE;
    private CarTypesService mCarTypesService;
    private String mWaKey;

    private ManufacturerRemoteDataSource(CarTypesService carTypesService, String waKey) {
        mCarTypesService = carTypesService;
        mWaKey = waKey;
    }

    public static ManufacturerRemoteDataSource getInstance(CarTypesService carTypesService, String waKey) {
        if (INSTANCE == null) {
            INSTANCE = new ManufacturerRemoteDataSource(carTypesService, waKey);
        }
        return INSTANCE;
    }

    @Override
    public Single<GenericPage> getManufacturerPage(int page, int pageSize, boolean refreshRequired) {
        return mCarTypesService.listManufacturers(page, pageSize, mWaKey);
    }
}
