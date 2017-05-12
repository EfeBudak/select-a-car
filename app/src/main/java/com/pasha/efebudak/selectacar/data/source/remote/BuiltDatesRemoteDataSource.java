package com.pasha.efebudak.selectacar.data.source.remote;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.pasha.efebudak.selectacar.data.CarTypesService;
import com.pasha.efebudak.selectacar.data.GenericPage;
import com.pasha.efebudak.selectacar.data.source.BuiltDatesDataSource;

import io.reactivex.Single;

/**
 * Created by efebudak on 10/05/2017.
 */

public class BuiltDatesRemoteDataSource implements BuiltDatesDataSource {

    private static BuiltDatesRemoteDataSource INSTANCE;
    @NonNull
    final private CarTypesService mCarTypesService;
    @NonNull
    final private String mWaKey;

    public static BuiltDatesRemoteDataSource getInstance(
            @NonNull final CarTypesService carTypesService,
            @NonNull final String waKey) {
        if (INSTANCE == null) {
            INSTANCE = new BuiltDatesRemoteDataSource(carTypesService, waKey);
        }
        return INSTANCE;
    }

    private BuiltDatesRemoteDataSource(
            @NonNull final CarTypesService carTypesService,
            @NonNull final String waKey) {
        mCarTypesService = carTypesService;
        mWaKey = waKey;
    }

    @Override
    public Single<ArrayMap<String, String>> getBuiltDates(final String manufacturerId, final String mainTypeId) {
        return mCarTypesService.listBuiltDates(manufacturerId, mainTypeId, mWaKey)
                .map(GenericPage::getGenericMap);
    }
}
