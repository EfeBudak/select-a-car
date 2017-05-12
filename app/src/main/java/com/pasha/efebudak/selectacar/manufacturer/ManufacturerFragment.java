package com.pasha.efebudak.selectacar.manufacturer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pasha.efebudak.selectacar.R;
import com.pasha.efebudak.selectacar.util.Constants;
import com.pasha.efebudak.selectacar.util.ui.SimpleTextAdapter;

/**
 * Created by efebudak on 04/05/2017.
 */

public class ManufacturerFragment extends Fragment implements
        ManufacturerContract.View,
        SimpleTextAdapter.Listener {

    private static final String BUNDLE_END_OF_LIST = "bundleEndOfList";

    private ManufacturerContract.Presenter mPresenter;
    private SimpleTextAdapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private boolean mEndOfList = false;

    public static ManufacturerFragment newInstance() {
        return new ManufacturerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            mEndOfList = savedInstanceState.getBoolean(BUNDLE_END_OF_LIST);
        }
        final View root = inflater.inflate(R.layout.fragment_manufacturer, container, false);
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.fragment_manufacturer_swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorSwipeRed),
                ContextCompat.getColor(getActivity(), R.color.colorSwipeBlue),
                ContextCompat.getColor(getActivity(), R.color.colorSwipePurple),
                ContextCompat.getColor(getActivity(), R.color.colorSwipeGreen));
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPresenter.onRefresh());
        final RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.fragment_manufacturer_recycler_view);
        mAdapter = new SimpleTextAdapter(new ArrayMap<>(), this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (!mSwipeRefreshLayout.isRefreshing() && !mEndOfList) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= Constants.PAGE_SIZE) {
                        mPresenter.onLoadNextPage();
                    }
                }
            }
        });

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(BUNDLE_END_OF_LIST, mEndOfList);
    }

    @Override
    public void setPresenter(ManufacturerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void addPageItems(@NonNull ArrayMap<String, String> stringMap) {
        mAdapter.updateList(stringMap);
    }

    @Override
    public void refreshPageItems(@NonNull ArrayMap<String, String> stringMap) {
        mEndOfList = false;
        mAdapter.updateList(stringMap);
    }

    @Override
    public void setRefreshing(boolean active) {
        mSwipeRefreshLayout.setRefreshing(active);
    }

    @Override
    public void stopPagination() {
        mEndOfList = true;
    }

    @Override
    public void showError() {
        Toast.makeText(getContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(@NonNull String key, @NonNull String value) {
        ((ManufacturerActivity) getActivity()).onItemClicked(key, value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
