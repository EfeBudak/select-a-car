package com.pasha.efebudak.selectacar.manufacturer;

import android.support.v4.util.ArrayMap;

import com.pasha.efebudak.selectacar.data.GenericPage;
import com.pasha.efebudak.selectacar.data.source.ManufacturerRepository;
import com.pasha.efebudak.selectacar.util.Constants;
import com.pasha.efebudak.selectacar.util.schedulers.BaseSchedulerProvider;
import com.pasha.efebudak.selectacar.util.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by efebudak on 11/05/2017.
 */

public class ManufacturerPresenterTest {

    private static GenericPage GENERIC_PAGE;
    @Mock
    private ManufacturerContract.View mManufacturerView;

    @Mock
    private ManufacturerRepository mManufacturerRepository;

    private BaseSchedulerProvider mSchedulerProvider;

    private ManufacturerPresenter mManufacturerPresenter;

    @Before
    public void setupSpendingsPresenter() {
        MockitoAnnotations.initMocks(this);
        mSchedulerProvider = new ImmediateSchedulerProvider();
        mManufacturerPresenter = ManufacturerPresenter.newInstance(mManufacturerView, mSchedulerProvider, mManufacturerRepository);

        when(mManufacturerView.isActive()).thenReturn(true);
        GENERIC_PAGE = new GenericPage();
        final ArrayMap<String, String> mockManufacturers = new ArrayMap<>();
        mockManufacturers.put("101", "BMW");
        mockManufacturers.put("102", "OPEL");
        mockManufacturers.put("103", "Mercedes");
        mockManufacturers.put("104", "Volvo");
        GENERIC_PAGE.addGenericMap(mockManufacturers);
    }

    @Test
    public void refreshManufacturersFromRepositoryAndLoadIntoView() {

        when(mManufacturerRepository.getManufacturerPage(0, Constants.PAGE_SIZE, true)).thenReturn(Single.just(GENERIC_PAGE));
        mManufacturerPresenter.onRefresh();

        verify(mManufacturerView).setRefreshing(true);
        verify(mManufacturerView).refreshPageItems(GENERIC_PAGE.getGenericMap());
        verify(mManufacturerView).setRefreshing(true);
    }
}
