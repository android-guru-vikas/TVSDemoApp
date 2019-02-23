package com.dev.cardekho.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.TextView;

import com.dev.cardekho.R;
import com.dev.cardekho.utils.AppEvents;
import com.dev.cardekho.utils.AppUtils;
import com.dev.cardekho.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemDetailsActivity extends BaseActivity {

    @BindView(R.id.captureImageButton)
    FloatingActionButton captureImageButton;
    @BindView(R.id.userNameTextView)
    TextView userNameTextView;
    @BindView(R.id.cityTextView)
    TextView cityTextView;
    @BindView(R.id.positionTextView)
    TextView positionTextView;
    @BindView(R.id.zipCodeTextView)
    TextView zipCodeTextView;
    @BindView(R.id.salaryTextView)
    TextView salaryTextView;
    @BindView(R.id.dateTextView)
    TextView dateTextView;
    private AppEvents appEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(getString(R.string.user_details));
        }
        Intent intent = getIntent();
        if (intent != null) {
            appEvents = intent.getParcelableExtra(Constants.KEY_APP_DATA);
            setupDataOnUi();
        }
    }

    private void setupDataOnUi() {
        ArrayList<String> data = appEvents.getAppEvents();
        userNameTextView.setText(AppUtils.getInstance().getValueFromData(data.get(0)).toString());
        positionTextView.setText(getString(R.string.position) +AppUtils.getInstance().getValueFromData(data.get(1)).toString());
        cityTextView.setText(getString(R.string.address) +AppUtils.getInstance().getValueFromData(data.get(2)).toString());
        zipCodeTextView.setText(AppUtils.getInstance().getValueFromData(data.get(3)).toString());
        dateTextView.setText(getString(R.string.work_since) +AppUtils.getInstance().getValueFromData(data.get(4)).toString());
        salaryTextView.setText(getString(R.string.assets) +AppUtils.getInstance().getValueFromData(data.get(5)).toString());
    }

    @OnClick(R.id.captureImageButton)
    void captureImage() {
        startActivity(new Intent(this, ImageViewActivity.class).putExtra(Constants.KEY_APP_DATA, appEvents));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
