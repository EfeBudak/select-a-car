package com.pasha.efebudak.selectacar.data;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by efebudak on 08/05/2017.
 */

public interface CarTypesService {

    @GET("car-types/manufacturer")
    Single<GenericPage> listManufacturers(@Query("page") int page,
                                          @Query("pageSize") int pageSize,
                                          @Query("wa_key") String waKey);

    @GET("car-types/main-types")
    Single<GenericPage> listMainTypes(@Query("manufacturer") String manufacturerId,
                                      @Query("page") int page,
                                      @Query("pageSize") int pageSize,
                                      @Query("wa_key") String waKey);

    @GET("car-types/built-dates")
    Single<GenericPage> listBuiltDates(@Query("manufacturer") String manufacturerId,
                                       @Query("main-type") String mainType,
                                       @Query("wa_key") String waKey);
}
