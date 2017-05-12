package com.pasha.efebudak.selectacar;

import com.pasha.efebudak.selectacar.builtdate.BuiltDateActivity;
import com.pasha.efebudak.selectacar.maintype.MainTypeActivity;
import com.pasha.efebudak.selectacar.manufacturer.ManufacturerActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by efebudak on 11/05/2017.
 */

@Singleton
@Component(modules = NetworkModule.class)
public interface NetworkComponent {

    void inject(ManufacturerActivity activity);

    void inject(MainTypeActivity activity);

    void inject(BuiltDateActivity activity);
}