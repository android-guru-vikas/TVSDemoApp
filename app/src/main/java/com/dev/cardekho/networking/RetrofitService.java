
package com.dev.cardekho.networking;

import com.dev.cardekho.application.CarDekhoApp;
import com.dev.cardekho.utils.AppUtils;
import com.dev.cardekho.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {
    private static RetrofitService instance;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit, retrofitForLogin;
    private OkHttpClient okHttpClientCustom;
    private Retrofit retrofitCustom;
    private Interceptor authInterceptor;
    public static long READ_TIME_OUT = 30000;
    public static long CONNECTION_TIME_OUT = 30000;
    private HttpLoggingInterceptor interceptor;

    private RetrofitService() {

    }

    public static RetrofitService getInstance() {
        if (instance == null) {
            synchronized (RetrofitService.class) {
                instance = new RetrofitService();
            }
        }
        return instance;
    }

    public Retrofit builder() {
        return getRetrofit(getOkHttpClient());
    }

    private Retrofit getRetrofit(OkHttpClient okHttpClient) {
        if (retrofit == null) {
            synchronized (RetrofitService.class) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.APP_BASE_URL)
                        .client(okHttpClient)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (RetrofitService.class) {
                okHttpClient = new OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .addInterceptor(OFFLINE_CACHE_CONTROL_INTERCEPTOR)
                        .addInterceptor(getLoggingInterceptor())
                        .readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
                        .connectTimeout(CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
                        .build();
            }
        }
        return okHttpClient;
    }

    private static final Interceptor OFFLINE_CACHE_CONTROL_INTERCEPTOR = chain -> {
        Request request = chain.request();
        if (AppUtils.isNetworkEnabled(CarDekhoApp.getAppContext())) {
            request = request.newBuilder()
                    .header("Cache-Control", "public, max-age=" + 5)
                    .build();
        } else {
            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7);
        }
        return chain.proceed(request);
    };


    private HttpLoggingInterceptor getLoggingInterceptor() {
        if (interceptor == null) {
            synchronized (RetrofitService.class) {
                interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
            }
        }
        return interceptor;
    }
}