package com.pasha.efebudak.selectacar.util.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by efebudak on 10/05/2017.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
