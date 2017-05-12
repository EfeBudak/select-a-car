package com.pasha.efebudak.selectacar.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Single;

/**
 * Created by efebudak on 10/05/2017.
 */

public class BuiltDatesRepository implements BuiltDatesDataSource {

    private static BuiltDatesRepository INSTANCE;

    @NonNull
    private BuiltDatesDataSource mBuiltDatesLocalDataSource;
    @NonNull
    private BuiltDatesDataSource mBuiltDatesRemoteDataSource;

    private Map<String, ArrayMap<String, String>> mCachedDates;

    private BuiltDatesRepository(
            @NonNull final BuiltDatesDataSource builtDatesLocalDataSource,
            @NonNull final BuiltDatesDataSource builtDatesRemoveDataSource) {
        mBuiltDatesLocalDataSource = builtDatesLocalDataSource;
        mBuiltDatesRemoteDataSource = builtDatesRemoveDataSource;
    }

    public static BuiltDatesRepository getInstance(
            @NonNull final BuiltDatesDataSource builtDatesLocalDataSource,
            @NonNull final BuiltDatesDataSource builtDatesRemoveDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new BuiltDatesRepository(builtDatesLocalDataSource, builtDatesRemoveDataSource);
        }

        return INSTANCE;
    }

    @Override
    public Single<ArrayMap<String, String>> getBuiltDates(final String manufacturerId, final String mainTypeId) {
        if (mCachedDates == null) {
            mCachedDates = new LinkedHashMap<>();
        }

        ArrayMap<String, String> dateList = getDatesById(manufacturerId + mainTypeId);
        if (dateList != null) {
            return Single.just(dateList);
        }

        return mBuiltDatesRemoteDataSource
                .getBuiltDates(manufacturerId, mainTypeId)
                .doOnSuccess(stringStringArrayMap -> mCachedDates.put(manufacturerId + mainTypeId, stringStringArrayMap));
    }

    @Nullable
    private ArrayMap<String, String> getDatesById(@NonNull final String dateListId) {
        if (mCachedDates == null || mCachedDates.isEmpty()) {
            return null;
        } else {
            return mCachedDates.get(dateListId);
        }
    }
}
