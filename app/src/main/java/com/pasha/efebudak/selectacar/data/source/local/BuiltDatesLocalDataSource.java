package com.pasha.efebudak.selectacar.data.source.local;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.pasha.efebudak.selectacar.data.source.BuiltDatesDataSource;

import io.reactivex.Single;

/**
 * Created by efebudak on 10/05/2017.
 */

public class BuiltDatesLocalDataSource implements BuiltDatesDataSource {

    private static BuiltDatesLocalDataSource INSTANCE;

    private BuiltDatesLocalDataSource() {
    }

    @NonNull
    public static BuiltDatesLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BuiltDatesLocalDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Single<ArrayMap<String, String>> getBuiltDates(String manufacturerId, String mainTypeId) {
        return null;
    }
}
