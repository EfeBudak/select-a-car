package com.pasha.efebudak.selectacar;

/**
 * Created by efebudak on 04/05/2017.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);

    void showError();

    void setRefreshing(boolean active);
}
