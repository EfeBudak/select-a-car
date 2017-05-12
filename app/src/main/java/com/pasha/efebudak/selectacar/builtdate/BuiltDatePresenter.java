package com.pasha.efebudak.selectacar.builtdate;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;

import com.pasha.efebudak.selectacar.data.source.BuiltDatesDataSource;
import com.pasha.efebudak.selectacar.util.schedulers.SchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by efebudak on 10/05/2017.
 */

class BuiltDatePresenter implements BuiltDateContract.Presenter {

    @NonNull
    private BuiltDateContract.View mView;
    @NonNull
    private SchedulerProvider mSchedulerProvider;
    @NonNull
    private BuiltDatesDataSource mBuiltDatesDataSource;
    @NonNull
    private CompositeDisposable mCompositeDisposable;
    private final String mManufacturerId;
    private final String mMainTypeId;

    static BuiltDatePresenter newInstance(
            @NonNull final String manufacturerId,
            @NonNull final String mainTypeId,
            @NonNull final BuiltDateContract.View view,
            @NonNull final SchedulerProvider schedulerProvider,
            @NonNull final BuiltDatesDataSource builtDatesDataSource) {
        return new BuiltDatePresenter(manufacturerId, mainTypeId, view, schedulerProvider, builtDatesDataSource);
    }

    private BuiltDatePresenter(
            @NonNull final String manufacturerId,
            @NonNull final String mainTypeId,
            @NonNull final BuiltDateContract.View view,
            @NonNull final SchedulerProvider schedulerProvider,
            @NonNull final BuiltDatesDataSource builtDatesDataSource) {
        mManufacturerId = manufacturerId;
        mMainTypeId = mainTypeId;
        mView = view;
        mSchedulerProvider = schedulerProvider;
        mBuiltDatesDataSource = builtDatesDataSource;
        mCompositeDisposable = new CompositeDisposable();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        mView.setRefreshing(true);
        getBuiltDates();
    }

    @Override
    public void unsubscribe() {
        mCompositeDisposable.clear();
    }

    private void getBuiltDates() {
        mCompositeDisposable.add(mBuiltDatesDataSource.getBuiltDates(mManufacturerId, mMainTypeId)
                .observeOn(mSchedulerProvider.ui())
                .subscribeOn(mSchedulerProvider.io())
                .subscribeWith(new DisposableSingleObserver<ArrayMap<String, String>>() {

                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull ArrayMap<String, String> stringStringArrayMap) {
                        mView.setRefreshing(false);
                        mView.showDateItems(stringStringArrayMap);
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        mView.showError();
                    }
                }));
    }
}
