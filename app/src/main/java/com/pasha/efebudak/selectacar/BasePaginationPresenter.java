package com.pasha.efebudak.selectacar;

/**
 * Created by efebudak on 10/05/2017.
 */

public interface BasePaginationPresenter extends BasePresenter {
    void onLoadNextPage();

    void onRefresh();
}
