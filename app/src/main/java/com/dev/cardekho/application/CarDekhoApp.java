package com.dev.cardekho.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.dev.cardekho.utils.Constants;

public class CarDekhoApp extends Application {

    private static final String TAG = "CarDekhoApp";
    private static CarDekhoApp applicationInstance = null;
    public int deviceWidth;
    public int deviceHeight;
    public Typeface droidTypeFace;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationInstance = this;
        CarDekhoApp.getInstance().getDisplayMetrix();
        droidTypeFace = Typeface.createFromAsset(getAssets(), Constants.KEY_FONT_TYPE);
    }

    public static CarDekhoApp getInstance() {
        return applicationInstance;
    }


    public static Context getAppContext() {
        return applicationInstance.getApplicationContext();
    }

    public void getDisplayMetrix() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        deviceWidth = metrics.widthPixels;
        deviceHeight = metrics.heightPixels;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
