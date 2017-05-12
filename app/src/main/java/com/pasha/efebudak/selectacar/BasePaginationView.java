package com.pasha.efebudak.selectacar;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

/**
 * Created by efebudak on 10/05/2017.
 */

public interface BasePaginationView<T> extends BaseView<T> {
    void addPageItems(@NonNull ArrayMap<String, String> stringMap);

    void refreshPageItems(@NonNull ArrayMap<String, String> stringMap);

    void stopPagination();
}
