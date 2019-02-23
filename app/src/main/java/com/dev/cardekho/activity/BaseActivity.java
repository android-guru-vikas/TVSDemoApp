package com.dev.cardekho.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dev.cardekho.R;
import com.dev.cardekho.networking.WebServiceExecutor;
import com.dev.cardekho.utils.AppLogs;
import com.dev.cardekho.utils.AppToast;
import com.dev.cardekho.utils.Constants;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public abstract class BaseActivity extends AppCompatActivity implements WebServiceExecutor.APIResponseListener {
    public Context pContext;
    public AppToast pAppToast;
    public AppLogs pAppLogs;
    public String pTAG = BaseActivity.class.getName();
    public WebServiceExecutor pWebServiceExecutor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initObjects();
    }

    private void initObjects() {
        pContext = this;
        pWebServiceExecutor = WebServiceExecutor.getInstance();
        pAppToast = AppToast.getInstance();
        pAppLogs = AppLogs.getInstance();
    }

    @Override
    public void onLoading(boolean isNetworkEnabled, boolean isLoading, Bundle bundle) {
        if (!isFinishing()) {
            if (!isNetworkEnabled) {
                pAppToast.showToast(pContext, "Network not found. Please check your network settings.");
            } /*else {
                if (isLoading) {
                    showLoading();
                } else {
                    hideLoading();
                }
            }*/
        }
    }

    @Override
    public void onNetworkFailure(Call call, Response response, Throwable throwable, Bundle bundle) {
        showErrorAndLogs(response, throwable);
    }

    @Override
    public void onNetworkSuccess(Call call, Response response, Bundle bundle) {

    }

    private void showErrorAndLogs(Response response, Throwable throwable) {
        if (response != null) {
            //onNetworkSuccess called but response==null or response.body==null
            if (response.isSuccessful()) {
                showErrorToast(pContext, response.code(), response.message());
            } else {
                showErrorToast(pContext, response.code(), response.message());
            }
        } else {
            //onNetworkFailure/onNetworkError called
            apiErrorToast(pContext, throwable);
        }
    }

    protected void showErrorToast(Context context, int code, String message) {
        if (!isFinishing()) {
            if (context != null) {
                pAppLogs.e("ERROR", " ETAModel code " + code + " " + message);
                if (code == 400) {
                    pAppToast.showToast(context, getString(R.string.bad_Request) + "\n" + code);
                } else if (code == 401) {
                    pAppToast.showToast(context, getString(R.string.unauthorized_user) + "\n" + code);
                } else if (code == 404) {
                    pAppToast.showToast(context, getString(R.string.something_went_wrong_on_server) + "\n" + code);
                } else if (code >= 500) {
                    pAppToast.showToast(context, getString(R.string.something_went_wrong_on_server) + "\n" + code);
                } else {
                    pAppToast.showToast(context, getString(R.string.something_went_wrong) + "\n" + code);
                }
            }
        }
    }

    protected void apiErrorToast(final Context context, final Throwable t) {
        if (!isFinishing()) {
            if (t instanceof UnknownHostException) {
                pAppToast.showToast(context, getString(R.string.UNKNOWN_HOST));
            } else if (t instanceof ConnectException) {
                pAppToast.showToast(context, getString(R.string.UNKNOWN_HOST));
            } else if (t instanceof SocketTimeoutException) {
                pAppToast.showToast(context, getString(R.string.SOCKET_TIME_OUT));
            } else if (t instanceof SocketException) {
                pAppToast.showToast(context, getString(R.string.UNABLE_TO_CONNECT));
            } else if (t instanceof JsonSyntaxException) {
                pAppToast.showToast(context, getString(R.string.JSON_SYNTAX));
            } else {
                pAppToast.showToast(context, t.getMessage());
            }
        }
    }
}
