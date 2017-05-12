package com.pasha.efebudak.selectacar.summary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.pasha.efebudak.selectacar.R;

/**
 * Created by efebudak on 11/05/2017.
 */

public class SummaryActivity extends AppCompatActivity {

    private static final String BUNDLE_MANUFACTURER_NAME = "bundleManufacturerName";
    private static final String BUNDLE_MAIN_TYPE_NAME = "bundleMainTypeName";
    private static final String BUNDLE_BUILT_DATE = "bundleBuiltDate";

    public static Intent newIntent(
            @NonNull final Context context,
            @NonNull final String manufacturerName,
            @NonNull final String mainTypeName,
            @NonNull final String builtDate) {
        final Intent intent = new Intent(context, SummaryActivity.class);
        final Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MANUFACTURER_NAME, manufacturerName);
        bundle.putString(BUNDLE_MAIN_TYPE_NAME, mainTypeName);
        bundle.putString(BUNDLE_BUILT_DATE, builtDate);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        final Bundle bundle = getIntent().getExtras();
        final String manufacturer = bundle.getString(BUNDLE_MANUFACTURER_NAME);
        final String mainType = bundle.getString(BUNDLE_MAIN_TYPE_NAME);
        final String builtType = bundle.getString(BUNDLE_BUILT_DATE);
        ((TextView) findViewById(R.id.text_view_manufacturer)).setText(getString(R.string.manufacturer_summary, manufacturer));
        ((TextView) findViewById(R.id.text_view_main_type)).setText(getString(R.string.main_type_summary, mainType));
        ((TextView) findViewById(R.id.text_view_built_date)).setText(getString(R.string.built_date_summary, builtType));
    }
}
