package com.pasha.efebudak.selectacar.manufacturer;

import android.support.annotation.NonNull;

import com.pasha.efebudak.selectacar.data.GenericPage;
import com.pasha.efebudak.selectacar.data.source.ManufacturerDataSource;
import com.pasha.efebudak.selectacar.util.Constants;
import com.pasha.efebudak.selectacar.util.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by efebudak on 04/05/2017.
 */

public class ManufacturerPresenter implements ManufacturerContract.Presenter {

    @NonNull
    private ManufacturerContract.View mView;
    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;
    @NonNull
    private ManufacturerDataSource mManufacturerDataSource;
    @NonNull
    private CompositeDisposable mCompositeDisposable;

    private int mCurrentPage;

    public ManufacturerPresenter(
            @NonNull final ManufacturerContract.View view,
            @NonNull final BaseSchedulerProvider baseSchedulerProvider,
            @NonNull final ManufacturerDataSource manufacturerDataSource) {
        mView = view;
        mSchedulerProvider = baseSchedulerProvider;
        mManufacturerDataSource = manufacturerDataSource;
        mCompositeDisposable = new CompositeDisposable();
        mView.setPresenter(this);
        mCurrentPage = 0;
    }

    public static ManufacturerPresenter newInstance(
            @NonNull final ManufacturerContract.View view,
            @NonNull final BaseSchedulerProvider baseSchedulerProvider,
            @NonNull final ManufacturerDataSource manufacturerDataSource) {
        return new ManufacturerPresenter(view, baseSchedulerProvider, manufacturerDataSource);
    }

    @Override
    public void subscribe() {
        mView.setRefreshing(true);
        getManufacturerPage(false);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onLoadNextPage() {
        mCurrentPage++;
        mView.setRefreshing(true);
        getManufacturerPage(false);
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 0;
        mView.setRefreshing(true);
        getManufacturerPage(true);
    }

    int getCurrentPage() {
        return mCurrentPage;
    }

    void restoreCurrentPage(final int currentPage) {
        mCurrentPage = currentPage;
    }

    private void getManufacturerPage(final boolean refreshRequired) {
        mCompositeDisposable.add(mManufacturerDataSource.getManufacturerPage(mCurrentPage, Constants.PAGE_SIZE, refreshRequired)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribeWith(new DisposableSingleObserver<GenericPage>() {

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull GenericPage manufacturerPage) {
                        if (manufacturerPage.getTotalPageCount() <= mCurrentPage) {
                            mView.stopPagination();
                        }
                        if (refreshRequired) {
                            mView.refreshPageItems(manufacturerPage.getGenericMap());
                            mView.setRefreshing(false);
                        } else {
                            mView.addPageItems(manufacturerPage.getGenericMap());
                            mView.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        mView.showError();
                        mView.setRefreshing(false);
                    }
                }));
    }
}
