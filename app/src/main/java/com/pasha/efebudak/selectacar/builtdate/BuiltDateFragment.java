package com.pasha.efebudak.selectacar.builtdate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pasha.efebudak.selectacar.util.ui.SimpleTextAdapter;
import com.pasha.efebudak.selectacar.R;

/**
 * Created by efebudak on 10/05/2017.
 */

public class BuiltDateFragment extends Fragment implements BuiltDateContract.View, SimpleTextAdapter.Listener {

    private static final String BUNDLE_MANUFACTURER_NAME = "bundleManufacturerName";
    private static final String BUNDLE_MAIN_TYPE_NAME = "bundleMainTypeName";
    private BuiltDateContract.Presenter mPresenter;
    private SimpleTextAdapter mAdapter;
    private ContentLoadingProgressBar mContentLoadingProgressBar;

    private String mManufacturerName;
    private String mMainTypeName;

    public static BuiltDateFragment newInstance(String manufacturerName, String mainTypeName) {
        final BuiltDateFragment fragment = new BuiltDateFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MANUFACTURER_NAME, manufacturerName);
        bundle.putString(BUNDLE_MAIN_TYPE_NAME, mainTypeName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final Bundle bundle = getArguments();
        if (bundle != null) {
            mManufacturerName = bundle.getString(BUNDLE_MANUFACTURER_NAME);
            mMainTypeName = bundle.getString(BUNDLE_MAIN_TYPE_NAME);
        }
        final View root = inflater.inflate(R.layout.fragment_built_date, container, false);
        mContentLoadingProgressBar = (ContentLoadingProgressBar) root.findViewById(R.id.fragment_built_date_content_loading_progress_bar);
        final TextView textViewManufacturerName = (TextView) root.findViewById(R.id.fragment_built_date_text_view_selected_values);
        textViewManufacturerName.setText(mManufacturerName + " " + mMainTypeName);
        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.fragment_built_date_recycler_view);
        mAdapter = new SimpleTextAdapter(new ArrayMap<>(), this);
        recyclerView.setAdapter(mAdapter);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        mPresenter.unsubscribe();
        super.onPause();
    }

    @Override
    public void setPresenter(BuiltDateContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setRefreshing(boolean active) {
        if (active) {
            mContentLoadingProgressBar.show();
        } else {
            mContentLoadingProgressBar.hide();
        }
    }

    @Override
    public void showDateItems(ArrayMap<String, String> dateItems) {
        mAdapter.updateList(dateItems);
    }

    @Override
    public void onItemClicked(@NonNull String key, @NonNull String value) {
        ((BuiltDateActivity) getActivity()).onItemClicked(value);
    }
}
