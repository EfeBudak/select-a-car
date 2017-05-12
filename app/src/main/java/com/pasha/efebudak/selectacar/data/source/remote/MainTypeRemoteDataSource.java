package com.pasha.efebudak.selectacar.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.pasha.efebudak.selectacar.data.CarTypesService;
import com.pasha.efebudak.selectacar.data.GenericPage;
import com.pasha.efebudak.selectacar.data.source.MainTypeDataSource;

import io.reactivex.Single;

/**
 * Created by efebudak on 09/05/2017.
 */

public class MainTypeRemoteDataSource implements MainTypeDataSource {

    @Nullable
    private static MainTypeRemoteDataSource INSTANCE;
    @NonNull
    final private CarTypesService mCarTypesService;
    @NonNull
    final private String mWaKey;

    private MainTypeRemoteDataSource(
            @NonNull final CarTypesService carTypesService,
            @NonNull final String waKey) {
        mCarTypesService = carTypesService;
        mWaKey = waKey;
    }

    @NonNull
    public static MainTypeRemoteDataSource getInstance(
            @NonNull final CarTypesService carTypesService,
            @NonNull final String waKey) {
        if (INSTANCE == null) {
            INSTANCE = new MainTypeRemoteDataSource(carTypesService, waKey);
        }
        return INSTANCE;
    }

    @Override
    public Single<GenericPage> getMainTypePage(@NonNull final String manufacturerId, int page, int pageSize, boolean refreshRequired) {
        return mCarTypesService.listMainTypes(manufacturerId, page, pageSize, mWaKey);
    }
}
