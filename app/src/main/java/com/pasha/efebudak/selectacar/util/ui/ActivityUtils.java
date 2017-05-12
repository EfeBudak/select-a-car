package com.pasha.efebudak.selectacar.util.ui;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by efebudak on 10/05/2017.
 */

public final class ActivityUtils {

    private ActivityUtils() {
    }

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int containerViewId) {
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(containerViewId, fragment);
        transaction.commit();
    }
}
