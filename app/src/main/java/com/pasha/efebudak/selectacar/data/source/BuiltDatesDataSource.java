package com.pasha.efebudak.selectacar.data.source;

import android.support.v4.util.ArrayMap;

import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by efebudak on 10/05/2017.
 */

public interface BuiltDatesDataSource {
    Single<ArrayMap<String, String>> getBuiltDates(String manufacturerId, String mainTypeId);
}
