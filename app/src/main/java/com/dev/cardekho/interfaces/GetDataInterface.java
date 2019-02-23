package com.dev.cardekho.interfaces;

import com.dev.cardekho.model.UserAuthModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GetDataInterface {
    @POST("reporting/vrm/api/test_new/int/gettabledata.php/")
    Call<Object> getEmployees(@Body UserAuthModel userAuthModel);
}