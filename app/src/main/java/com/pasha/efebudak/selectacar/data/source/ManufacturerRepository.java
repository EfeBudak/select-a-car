package com.pasha.efebudak.selectacar.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.pasha.efebudak.selectacar.data.GenericPage;

import io.reactivex.Single;

/**
 * Created by efebudak on 05/05/2017.
 */

public class ManufacturerRepository implements ManufacturerDataSource {

    private static ManufacturerRepository INSTANCE;
    @VisibleForTesting
    @Nullable
    GenericPage mManufacturerPage;
    private ManufacturerDataSource mManufacturerLocalDataSource;
    private ManufacturerDataSource mManufacturerRemoteDataSource;

    private ManufacturerRepository(
            ManufacturerDataSource mManufacturerLocalDataSource,
            ManufacturerDataSource mManufacturerRemoteDataSource) {
        this.mManufacturerLocalDataSource = mManufacturerLocalDataSource;
        this.mManufacturerRemoteDataSource = mManufacturerRemoteDataSource;
    }

    public static ManufacturerRepository getInstance(
            @NonNull final ManufacturerDataSource manufacturerLocalDataSource,
            @NonNull final ManufacturerDataSource manufacturerRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ManufacturerRepository(manufacturerLocalDataSource, manufacturerRemoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Single<GenericPage> getManufacturerPage(int page, int pageSize, boolean refreshRequired) {

        if (mManufacturerPage == null) {
            mManufacturerPage = new GenericPage();
        } else if (mManufacturerPage.getPage() >= page
                && mManufacturerPage.getPageSize() == pageSize
                && !refreshRequired) {
            return Single.just(mManufacturerPage);
        }

        return mManufacturerRemoteDataSource.getManufacturerPage(page, pageSize, refreshRequired)
                .map(manufacturerPage -> {
                    if (refreshRequired) {
                        mManufacturerPage.copyObject(manufacturerPage);
                    } else {
                        mManufacturerPage.setPage(manufacturerPage.getPage());
                        mManufacturerPage.setPageSize(manufacturerPage.getPageSize());
                        mManufacturerPage.setTotalPageCount(manufacturerPage.getTotalPageCount());
                        mManufacturerPage.addGenericMap(manufacturerPage.getGenericMap());
                    }
                    return mManufacturerPage;
                });
    }
}
