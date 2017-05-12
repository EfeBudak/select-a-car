package com.pasha.efebudak.selectacar.maintype;

import android.support.annotation.NonNull;

import com.pasha.efebudak.selectacar.data.GenericPage;
import com.pasha.efebudak.selectacar.data.source.MainTypeDataSource;
import com.pasha.efebudak.selectacar.util.Constants;
import com.pasha.efebudak.selectacar.util.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by efebudak on 09/05/2017.
 */

public class MainTypePresenter implements MainTypeContract.Presenter {

    @NonNull
    private MainTypeContract.View mView;
    @NonNull
    private SchedulerProvider mSchedulerProvider;
    @NonNull
    private MainTypeDataSource mMainTypeDataSource;
    @NonNull
    private CompositeDisposable mCompositeDisposable;

    private String mManufacturerId;
    private int mCurrentPage;

    public MainTypePresenter(
            @NonNull final String manufacturerId,
            @NonNull final MainTypeContract.View view,
            @NonNull final SchedulerProvider schedulerProvider,
            @NonNull final MainTypeDataSource mainTypeDataSource) {
        mManufacturerId = manufacturerId;
        mView = view;
        mSchedulerProvider = schedulerProvider;
        mMainTypeDataSource = mainTypeDataSource;
        mCompositeDisposable = new CompositeDisposable();
        mView.setPresenter(this);
        mCurrentPage = 0;
    }

    public static MainTypePresenter newInstance(
            @NonNull final String manufacturerId,
            @NonNull final MainTypeContract.View view,
            @NonNull final SchedulerProvider schedulerProvider,
            @NonNull final MainTypeDataSource mainTypeDataSource) {
        return new MainTypePresenter(manufacturerId, view, schedulerProvider, mainTypeDataSource);
    }

    @Override
    public void subscribe() {
        mView.setRefreshing(true);
        getMainTypesPage(false);
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    @Override
    public void onLoadNextPage() {
        mCurrentPage++;
        mView.setRefreshing(true);
        getMainTypesPage(false);
    }

    @Override
    public void onRefresh() {
        mCurrentPage = 0;
        mView.setRefreshing(true);
        getMainTypesPage(true);
    }

    int getCurrentPage() {
        return mCurrentPage;
    }

    void restoreCurrentPage(final int currentPage) {
        mCurrentPage = currentPage;
    }

    private void getMainTypesPage(final boolean refreshRequired) {
        mCompositeDisposable.add(mMainTypeDataSource.getMainTypePage(mManufacturerId, mCurrentPage, Constants.PAGE_SIZE, refreshRequired)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribeWith(new DisposableSingleObserver<GenericPage>() {

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull GenericPage mainTypePage) {
                        if (mainTypePage.getTotalPageCount() <= mCurrentPage) {
                            mView.stopPagination();
                        }
                        if (refreshRequired) {
                            mView.refreshPageItems(mainTypePage.getGenericMap());
                        } else {
                            mView.addPageItems(mainTypePage.getGenericMap());
                        }
                        mView.setRefreshing(false);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        mView.showError();
                        mView.setRefreshing(false);
                    }
                }));
    }
}
