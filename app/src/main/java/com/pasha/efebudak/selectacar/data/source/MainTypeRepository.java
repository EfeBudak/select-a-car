package com.pasha.efebudak.selectacar.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

import com.pasha.efebudak.selectacar.data.GenericPage;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Single;

/**
 * Created by efebudak on 09/05/2017.
 */

public class MainTypeRepository implements MainTypeDataSource {

    private static MainTypeRepository INSTANCE;
    @VisibleForTesting
    @Nullable
    Map<String, GenericPage> mCachedMainTypePages;
    private MainTypeDataSource mMainTypeLocalDataSource;
    private MainTypeDataSource mMainTypeRemoteDataSource;

    private MainTypeRepository(
            MainTypeDataSource mainTypeLocalDataSource,
            MainTypeDataSource mainTypeRemoteDataSource) {
        this.mMainTypeLocalDataSource = mainTypeLocalDataSource;
        this.mMainTypeRemoteDataSource = mainTypeRemoteDataSource;
    }

    public static MainTypeRepository getInstance(
            @NonNull final MainTypeDataSource mainTypeLocalDataSource,
            @NonNull final MainTypeDataSource mainTypeRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new MainTypeRepository(mainTypeLocalDataSource, mainTypeRemoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public Single<GenericPage> getMainTypePage(@NonNull final String manufacturerId, int page, int pageSize, boolean refreshRequired) {
        if (mCachedMainTypePages == null) {
            mCachedMainTypePages = new LinkedHashMap<>();
        }
        GenericPage genericPage = getGenericPageWithId(manufacturerId);
        final GenericPage finalGenericPage;
        if (genericPage == null) {
            finalGenericPage = new GenericPage();
        } else if (genericPage.getPage() >= page && genericPage.getPageSize() == pageSize && !refreshRequired) {
            return Single.just(genericPage);
        } else {
            finalGenericPage = genericPage;
        }
        return mMainTypeRemoteDataSource.getMainTypePage(manufacturerId, page, pageSize, refreshRequired)
                .map(manufacturerPage -> {
                    if (refreshRequired) {
                        finalGenericPage.copyObject(manufacturerPage);
                    } else {
                        finalGenericPage.setPage(manufacturerPage.getPage());
                        finalGenericPage.setPageSize(manufacturerPage.getPageSize());
                        finalGenericPage.setTotalPageCount(manufacturerPage.getTotalPageCount());
                        finalGenericPage.addGenericMap(manufacturerPage.getGenericMap());
                    }
                    mCachedMainTypePages.put(manufacturerId, finalGenericPage);
                    return finalGenericPage;
                });
    }

    @Nullable
    private GenericPage getGenericPageWithId(@NonNull final String manufacturerId) {
        if (mCachedMainTypePages == null || mCachedMainTypePages.isEmpty()) {
            return null;
        } else {
            return mCachedMainTypePages.get(manufacturerId);
        }
    }
}
