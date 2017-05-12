package com.pasha.efebudak.selectacar.builtdate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.pasha.efebudak.selectacar.BuildConfig;
import com.pasha.efebudak.selectacar.R;
import com.pasha.efebudak.selectacar.SelectACarApplication;
import com.pasha.efebudak.selectacar.data.CarTypesService;
import com.pasha.efebudak.selectacar.data.source.BuiltDatesRepository;
import com.pasha.efebudak.selectacar.data.source.local.BuiltDatesLocalDataSource;
import com.pasha.efebudak.selectacar.data.source.remote.BuiltDatesRemoteDataSource;
import com.pasha.efebudak.selectacar.summary.SummaryActivity;
import com.pasha.efebudak.selectacar.util.schedulers.SchedulerProvider;
import com.pasha.efebudak.selectacar.util.ui.ActivityUtils;

import javax.inject.Inject;

/**
 * Created by efebudak on 10/05/2017.
 */

public class BuiltDateActivity extends AppCompatActivity {

    private static final String BUNDLE_MANUFACTURER_ID = "bundleManufacturerId";
    private static final String BUNDLE_MANUFACTURER_NAME = "bundleManufacturerName";
    private static final String BUNDLE_MAIN_TYPE_ID = "bundleMainTypeId";
    private static final String BUNDLE_MAIN_TYPE_NAME = "bundleMainTypeName";
    @Inject
    CarTypesService mCarTypesService;
    private String mManufacturerId;
    private String mManufacturerName;
    private String mMainTypeId;
    private String mMainTypeName;
    private BuiltDatePresenter mPresenter;

    public static Intent newIntent(
            @NonNull final Context context,
            @NonNull final String manufacturerId,
            @NonNull final String manufacturerName,
            @NonNull final String mainTypeId,
            @NonNull final String mainTypeName) {
        final Intent intent = new Intent(context, BuiltDateActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MANUFACTURER_ID, manufacturerId);
        bundle.putString(BUNDLE_MANUFACTURER_NAME, manufacturerName);
        bundle.putString(BUNDLE_MAIN_TYPE_ID, mainTypeId);
        bundle.putString(BUNDLE_MAIN_TYPE_NAME, mainTypeName);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SelectACarApplication) getApplication()).getNetComponent().inject(this);

        setContentView(R.layout.activity_built_date);
        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mManufacturerId = bundle.getString(BUNDLE_MANUFACTURER_ID);
            mManufacturerName = bundle.getString(BUNDLE_MANUFACTURER_NAME);
            mMainTypeId = bundle.getString(BUNDLE_MAIN_TYPE_ID);
            mMainTypeName = bundle.getString(BUNDLE_MAIN_TYPE_NAME);
        }

        BuiltDateFragment builtDateFragment
                = (BuiltDateFragment) getSupportFragmentManager().findFragmentById(R.id.activity_built_type_content_container);
        if (builtDateFragment == null) {
            builtDateFragment = BuiltDateFragment.newInstance(mManufacturerName, mMainTypeName);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), builtDateFragment, R.id.activity_built_type_content_container);
        }

        mPresenter = BuiltDatePresenter.newInstance(
                mManufacturerId,
                mMainTypeId,
                builtDateFragment,
                SchedulerProvider.getInstance(),
                BuiltDatesRepository.getInstance(
                        BuiltDatesLocalDataSource.getInstance(),
                        BuiltDatesRemoteDataSource.getInstance(mCarTypesService, BuildConfig.WA_KEY)));

    }

    void onItemClicked(String builtDateName) {
        startActivity(SummaryActivity.newIntent(this, mManufacturerName, mMainTypeName, builtDateName));
    }
}
