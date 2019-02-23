package com.dev.cardekho.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.dev.cardekho.R;
import com.dev.cardekho.adapter.CarDekhoAdapter;
import com.dev.cardekho.design.CarDekhoTextView;
import com.dev.cardekho.interfaces.OnItemClickListener;
import com.dev.cardekho.model.TABLEDATA;
import com.dev.cardekho.utils.AppEvents;
import com.dev.cardekho.utils.Constants;
import com.dev.cardekho.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.parentLayout)
    CoordinatorLayout parentLayout;
    @BindView(R.id.titleContainer)
    CarDekhoTextView titleContainer;
    @BindView(R.id.collapsingToolBar)
    CollapsingToolbarLayout collapsingToolBar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.barChartFab)
    FloatingActionButton barChartFab;
    private LinearLayoutManager layoutManager;
    private CarDekhoAdapter adapter;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION = 200;


    private boolean mIsTheTitleVisible = false;
    private boolean mIsTheTitleContainerVisible = true;
    private UserViewModel viewModel;
    private TABLEDATA tabledata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        toolBar.setTitle("");
        appBar.addOnOffsetChangedListener(this);
        toolBar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        startAlphaAnimation(title, 0, View.INVISIBLE);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        viewModel.getUsers().observe(this, users -> {
            // update UI
            if (users != null) {
                tabledata = users.getTABLEDATA();
                setupRecyclerView();
            }
        });
    }

    private void setupRecyclerView() {
        if (tabledata != null && tabledata.getData() != null) {
            List<List<String>> items = tabledata.getData();
            if (items != null && items.size() > 0) {
                if (adapter == null) {
                    adapter = new CarDekhoAdapter(items, this, this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setNestedScrollingEnabled(true);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @OnClick(R.id.barChartFab)
    void openActivity() {
        int count = 0;
        List<String> salaryList = new ArrayList<>();
        List<String> empList = new ArrayList<>();
        if (tabledata != null) {
            List<List<String>> stringData = tabledata.getData();
            if (stringData != null) {
                if (stringData.size() > 10) {
                    for (List<String> stringList : stringData) {
                        if (count < 10) {
                            if (stringList != null) {
                                String asset = stringList.get(5);
                                String name = stringList.get(0);
                                if (asset != null) {
                                    empList.add(name);
                                    salaryList.add(asset);
                                }
                                count++;
                            }
                        }else break;
                    }
                    startActivity(new Intent(this, BarChartActivity.class).
                            putStringArrayListExtra(Constants.KEY_APP_DATA, (ArrayList<String>) salaryList).
                            putStringArrayListExtra(Constants.KEY_USER_DATA, (ArrayList<String>) empList));
                }
            }
        }
    }


    @OnClick(R.id.mapViewFab)
    void openMapActivity() {
        List<String> cityList = new ArrayList<>();
        if (tabledata != null) {
            List<List<String>> stringData = tabledata.getData();
            if (stringData != null) {
                if (stringData.size() > 10) {
                    for (List<String> stringList : stringData) {
                        if (stringList != null) {
                            String pinCode = stringList.get(4);
                            if (pinCode != null) {
                                cityList.add(pinCode);
                            }
                        }
                    }
                    startActivity(new Intent(this, MapViewActivity.class)
                            .putStringArrayListExtra(Constants.KEY_CITY_DATA, (ArrayList<String>) cityList));
                }
            }
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;
        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if (!mIsTheTitleVisible) {
                toolBar.setTitle(getString(R.string.app_name));
                toolBar.setTitleMargin(100, 0, 0, 0);
                toolBar.setTitleTextColor(getResources().getColor(android.R.color.white));
                startAlphaAnimation(title, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                toolBar.setTitle("");
                startAlphaAnimation(title, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (mIsTheTitleContainerVisible) {
                startAlphaAnimation(titleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }
        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(titleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation(View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }

    @Override
    public void onItemClick(ArrayList<String> item) {
        AppEvents appEvents = new AppEvents(item);
        Intent intent = new Intent(this, ItemDetailsActivity.class);
        intent.putExtra(Constants.KEY_APP_DATA, appEvents);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
