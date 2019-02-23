package com.dev.cardekho.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.dev.cardekho.R;
import com.dev.cardekho.application.CarDekhoApp;
import com.dev.cardekho.model.UserAuthModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class AppUtils {
    private static AppUtils instance;

    private AppUtils() {

    }

    public static AppUtils getInstance() {
        if (instance == null) {
            synchronized (AppUtils.class) {
                if (instance == null) {
                    instance = new AppUtils();
                }
            }
        }
        return instance;
    }

    public Object getValueFromData(Object data) {
        return (data == null) ? "" : capitalizeString(data.toString());
    }

    private String capitalizeString(String s) {
        if (s != null && s.length() > 0) {
            return s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
        } else
            return s;
    }

    public static boolean isNetworkEnabled(Context pContext) {
        if (pContext != null) {
            try {
                ConnectivityManager conMngr = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = conMngr != null ? conMngr.getActiveNetworkInfo() : null;
                return activeNetwork != null && activeNetwork.isConnected();
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static UserAuthModel getUserAuth() {
        UserAuthModel model = new UserAuthModel();
        model.setUsername("test");
        model.setPassword("123456");
        return model;
    }

    public void showToast(String message) {
        if (AppUtils.isNotEmptyNotNull(message)) {
            Toast.makeText(CarDekhoApp.getAppContext(), message, Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(CarDekhoApp.getAppContext(), CarDekhoApp.getAppContext().getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
    }

    private static boolean isNotEmptyNotNull(String text) {
        return text != null && !text.isEmpty();
    }

    public static File createAndGetImageFile(String fileName) {
        // String fileName = "";
        File imageFile = null;
        try {
            String root = getStorageFile().toString();
            File myDir = new File(root + Constants.CARDEKHO_DIRECTORY_NAME + Constants.CARDEKHO_MEDIA_FILE);
            if (!myDir.exists())
                myDir.mkdirs();
            if (fileName == null)
                fileName = "Image" + System.currentTimeMillis() + ".jpg";
            else {
                fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            }
            imageFile = new File(myDir, fileName);
            imageFile.createNewFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
        return imageFile;
    }


    public static File getStorageFile() {
        File directory = null;
        //if there is no SD card, create new directory objects to make directory on device
        if (Environment.getExternalStorageState() == null) {
            //create new file directory object
            directory = new File(Environment.getDataDirectory().toString());
            if (!directory.exists()) {
                directory.mkdir();
            }
            // if phone DOES have sd card
        } else if (Environment.getExternalStorageState() != null) {
            // search for directory on SD card
            directory = new File(Environment.getExternalStorageDirectory().toString());
            if (!directory.exists()) {
                directory.mkdir();
            }
        }// end of SD card checking
        return directory;
    }

    public static void resizeCaptureImage(final Uri mImageUri, final File photo, final Context context) {
        context.getContentResolver().notifyChange(mImageUri, null);
        ContentResolver cr = context.getContentResolver();
        Bitmap bitmap;
        try {
            bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, mImageUri);
            compressBitmapInFile(bitmap, photo);
        } catch (Exception e) {

        }
    }

    private static void compressBitmapInFile(final Bitmap bitmap, File photo) {
        try {
            if (photo != null && photo.exists()) {
                photo.delete();
            }
            FileOutputStream fos = new FileOutputStream(photo);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 40, fos);
        } catch (Exception e) {
        }
    }

    public static String getCurrentTimeStamp() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
        Date date = new Date();
        System.out.println("Current Time Stamp: " + dateFormat.format(date));
        return dateFormat.format(date);
    }
}