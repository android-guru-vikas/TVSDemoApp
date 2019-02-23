package com.dev.cardekho.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.dev.cardekho.R;
import com.dev.cardekho.utils.Constants;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BarChartActivity extends BaseActivity {

    @BindView(R.id.chartLayout)
    BarChart chartLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(getString(R.string.graph_view));
        }

        Intent intent = getIntent();
        if (intent != null) {
//            try {
                List<String> assestsList = intent.getStringArrayListExtra(Constants.KEY_APP_DATA);
                List<String> nameList = intent.getStringArrayListExtra(Constants.KEY_USER_DATA);
                setupDataOnUi(nameList, assestsList);
//            } catch (Exception e) {
//                pAppToast.showToast(pContext, getString(R.string.something_went_wrong));
//            }
        }
    }

    private void setupDataOnUi(List<String> nameList, List<String> assestList) {
        BarData data = new BarData(nameList, getDataSet(assestList));
        chartLayout.setData(data);
        chartLayout.getXAxis().setDrawLabels(false);
        chartLayout.getXAxis().setSpaceBetweenLabels(10);
        chartLayout.animateXY(2000, 2000);
        chartLayout.invalidate();
    }

    private ArrayList<BarDataSet> getDataSet(List<String> assestList) {
        ArrayList<BarDataSet> dataSets = null;
        ArrayList<BarEntry> dataEntries = new ArrayList<>();
        for (int i = 0; i < assestList.size(); i++) {
            dataEntries.add(new BarEntry(Float.parseFloat(assestList.get(i).replace("$", "").replace(",", "")), i));
        }

        BarDataSet barDataSet = new BarDataSet(dataEntries, getString(R.string.salary_view));
        barDataSet.setColor(Color.rgb(0, 155, 0));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet);
        return dataSets;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
