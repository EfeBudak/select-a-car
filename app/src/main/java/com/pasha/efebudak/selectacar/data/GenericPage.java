package com.pasha.efebudak.selectacar.data;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by efebudak on 05/05/2017.
 */

public class GenericPage extends PaginationObject {

    @SerializedName("wkda")
    private ArrayMap<String, String> genericMap;

    public GenericPage() {
        genericMap = new ArrayMap<>();
    }

    public GenericPage(int page, int pageSize, int totalPageCount, ArrayMap<String, String> stringArrayMap) {
        super(page, pageSize, totalPageCount);
        this.genericMap = stringArrayMap;
    }

    public ArrayMap<String, String> getGenericMap() {
        return genericMap;
    }

    public void addGenericMap(@NonNull final Map<String, String> manufacturerList) {
        this.genericMap.putAll(manufacturerList);
    }

    public void copyObject(@NonNull final GenericPage genericPage) {
        super.copyObject(genericPage);
        genericMap = genericPage.getGenericMap();
    }
}
