package com.pasha.efebudak.selectacar;

import android.app.Application;

/**
 * Created by efebudak on 11/05/2017.
 */

public class SelectACarApplication extends Application {

    private NetworkComponent mNetworkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetworkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule())
                .build();
    }

    public NetworkComponent getNetComponent() {
        return mNetworkComponent;
    }
}
