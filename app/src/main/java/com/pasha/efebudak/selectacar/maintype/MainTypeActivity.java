package com.pasha.efebudak.selectacar.maintype;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pasha.efebudak.selectacar.BuildConfig;
import com.pasha.efebudak.selectacar.R;
import com.pasha.efebudak.selectacar.SelectACarApplication;
import com.pasha.efebudak.selectacar.builtdate.BuiltDateActivity;
import com.pasha.efebudak.selectacar.data.CarTypesService;
import com.pasha.efebudak.selectacar.data.source.MainTypeRepository;
import com.pasha.efebudak.selectacar.data.source.local.MainTypeLocalDataSource;
import com.pasha.efebudak.selectacar.data.source.remote.MainTypeRemoteDataSource;
import com.pasha.efebudak.selectacar.util.schedulers.SchedulerProvider;
import com.pasha.efebudak.selectacar.util.ui.ActivityUtils;

import javax.inject.Inject;

/**
 * Created by efebudak on 09/05/2017.
 */

public class MainTypeActivity extends AppCompatActivity {

    private static final String BUNDLE_CURRENT_PAGE = "bundleCurrentPage";
    private static final String BUNDLE_MANUFACTURER_ID = "bundleManufacturerId";
    private static final String BUNDLE_MANUFACTURER_NAME = "bundleManufacturerName";
    @Inject
    CarTypesService mCarTypesService;
    private String mManufacturerId;
    private String mManufacturerName;
    private MainTypePresenter mMainTypePresenter;

    public static Intent newIntent(
            @NonNull final Context context,
            @NonNull final String manufacturerId,
            @NonNull final String manufacturerName) {
        final Intent intent = new Intent(context, MainTypeActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MANUFACTURER_ID, manufacturerId);
        bundle.putString(BUNDLE_MANUFACTURER_NAME, manufacturerName);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SelectACarApplication) getApplication()).getNetComponent().inject(this);

        setContentView(R.layout.activity_main_type);
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mManufacturerId = bundle.getString(BUNDLE_MANUFACTURER_ID);
            mManufacturerName = bundle.getString(BUNDLE_MANUFACTURER_NAME);
        }

        MainTypeFragment mainTypeFragment
                = (MainTypeFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_type_content_container);
        if (mainTypeFragment == null) {
            mainTypeFragment = MainTypeFragment.newInstance(mManufacturerName);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), mainTypeFragment, R.id.activity_main_type_content_container);
        }

        mMainTypePresenter = MainTypePresenter.newInstance(
                mManufacturerId,
                mainTypeFragment,
                SchedulerProvider.getInstance(),
                MainTypeRepository.getInstance(
                        MainTypeLocalDataSource.getInstance(),
                        MainTypeRemoteDataSource.getInstance(mCarTypesService, BuildConfig.WA_KEY)));
        if (savedInstanceState != null) {
            mMainTypePresenter.restoreCurrentPage(savedInstanceState.getInt(BUNDLE_CURRENT_PAGE));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_CURRENT_PAGE, mMainTypePresenter.getCurrentPage());
        super.onSaveInstanceState(outState);
    }

    void onItemClicked(String mainTypeId, String mainTypeName) {
        startActivity(BuiltDateActivity.newIntent(this, mManufacturerId, mManufacturerName, mainTypeId, mainTypeName));
    }
}
