package com.pasha.efebudak.selectacar.manufacturer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.pasha.efebudak.selectacar.BuildConfig;
import com.pasha.efebudak.selectacar.R;
import com.pasha.efebudak.selectacar.SelectACarApplication;
import com.pasha.efebudak.selectacar.data.CarTypesService;
import com.pasha.efebudak.selectacar.data.source.ManufacturerRepository;
import com.pasha.efebudak.selectacar.data.source.local.ManufacturerLocalDataSource;
import com.pasha.efebudak.selectacar.data.source.remote.ManufacturerRemoteDataSource;
import com.pasha.efebudak.selectacar.maintype.MainTypeActivity;
import com.pasha.efebudak.selectacar.util.schedulers.SchedulerProvider;
import com.pasha.efebudak.selectacar.util.ui.ActivityUtils;

import javax.inject.Inject;

public class ManufacturerActivity extends AppCompatActivity {

    private static final String BUNDLE_CURRENT_PAGE = "bundleCurrentPage";
    @Inject
    CarTypesService mCarTypesService;
    private ManufacturerPresenter mManufacturerPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((SelectACarApplication) getApplication()).getNetComponent().inject(this);
        setContentView(R.layout.activity_manufacturer);

        ManufacturerFragment manufacturerFragment
                = (ManufacturerFragment) getSupportFragmentManager().findFragmentById(R.id.activity_manufacturer_content_container);
        if (manufacturerFragment == null) {
            manufacturerFragment = ManufacturerFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), manufacturerFragment, R.id.activity_manufacturer_content_container);
        }

        mManufacturerPresenter = ManufacturerPresenter.newInstance(
                manufacturerFragment,
                SchedulerProvider.getInstance(),
                ManufacturerRepository.getInstance(
                        ManufacturerLocalDataSource.getInstance(),
                        ManufacturerRemoteDataSource.getInstance(mCarTypesService, BuildConfig.WA_KEY)));
        if (savedInstanceState != null) {
            mManufacturerPresenter.restoreCurrentPage(savedInstanceState.getInt(BUNDLE_CURRENT_PAGE));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_CURRENT_PAGE, mManufacturerPresenter.getCurrentPage());
        super.onSaveInstanceState(outState);
    }

    void onItemClicked(String manufacturerId, String manufacturerName) {
        startActivity(MainTypeActivity.newIntent(this, manufacturerId, manufacturerName));
    }
}
