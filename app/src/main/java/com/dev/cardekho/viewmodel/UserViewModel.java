package com.dev.cardekho.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.dev.cardekho.interfaces.GetDataInterface;
import com.dev.cardekho.model.GetDataModel;
import com.dev.cardekho.model.TABLEDATA;
import com.dev.cardekho.networking.RetrofitService;
import com.dev.cardekho.utils.AppUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private MutableLiveData<GetDataModel> users;

    public LiveData<GetDataModel> getUsers() {
        if (users == null) {
            users = new MutableLiveData<GetDataModel>();
            loadUsers();
        }
        return users;
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
        GetDataInterface dataInterface = RetrofitService.getInstance().builder().create(GetDataInterface.class);
        Call<Object> call = dataInterface.getEmployees(AppUtils.getUserAuth());
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                //finally we are setting the list to our MutableLiveData
                if (response.body() != null) {
                    try {
                        Gson gson = new Gson();
                        GetDataModel model = gson.fromJson(response.body().toString(), GetDataModel.class);
                        users.setValue(model);
                    } catch (Exception e) {
                        AppUtils.getInstance().showToast("");
                    }
                } else {
                    AppUtils.getInstance().showToast("");
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
//                AppUtils.getInstance().showToast(t.getMessage());
            }
        });
    }
}
