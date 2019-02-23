package com.dev.cardekho.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;

import com.dev.cardekho.R;
import com.dev.cardekho.design.CarDekhoTextView;
import com.dev.cardekho.utils.AppEvents;
import com.dev.cardekho.utils.AppUtils;
import com.dev.cardekho.utils.Constants;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageViewActivity extends BaseActivity {
    @BindView(R.id.imageView)
    /* CarDekho*/ ImageView imageView;
    @BindView(R.id.userNameTextView)
    CarDekhoTextView userNameTextView;
    @BindView(R.id.timeTextView)
    CarDekhoTextView timeTextView;

    Uri imageUri;
    private AppEvents appEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null){
            appEvents = intent.getParcelableExtra(Constants.KEY_APP_DATA);
        }
        captureImage();
    }

    private void captureImage() {
        Intent intent = null;
        File file = AppUtils.createAndGetImageFile(null);
        if (file == null)
            pAppToast.showToast(pContext, "File do not create for image store");
        else {
            intent = new Intent("android.media.action.IMAGE_CAPTURE");
            imageUri = FileProvider.getUriForFile(pContext,
                    pContext.getApplicationContext()
                            .getPackageName() + ".provider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, Constants.CAPTURE_IMAGE_CAMERA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.CAPTURE_IMAGE_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        imageView.setImageURI(imageUri);
                        userNameTextView.setText(appEvents.getAppEvents().get(0));
                        timeTextView.setText(AppUtils.getCurrentTimeStamp());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }else{
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
