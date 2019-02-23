package com.dev.cardekho.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dev.cardekho.R;
import com.dev.cardekho.design.CarDekhoTextView;
import com.dev.cardekho.interfaces.OnItemClickListener;
import com.dev.cardekho.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarDekhoAdapter extends RecyclerView.Adapter<CarDekhoAdapter.ViewHolder> {

    private static final String TAG = "CarDekhoAdapter";
    private final List<List<String>> mItemList;
    private final Context context;
    private OnItemClickListener listener;

    public CarDekhoAdapter(List<List<String>> itemList, Context context, OnItemClickListener listener) {
        mItemList = itemList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycler_view_layout, parent, false);
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final List<String> item = mItemList.get(position);
        if (item != null) {
            holder.bind(item, listener);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iconImageView)
        ImageView iconImageView;
        @BindView(R.id.userNameTextView)
        CarDekhoTextView userNameTextView;
        @BindView(R.id.positionTextView)
        CarDekhoTextView positionTextView;
        @BindView(R.id.parentLayout)
        RelativeLayout parentLayout;

        ViewHolder(View itemView, int ViewType) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void bind(List<String> item, OnItemClickListener listener) {
            userNameTextView.setText(AppUtils.getInstance().getValueFromData(item.get(0)).toString());
            positionTextView.setText(AppUtils.getInstance().getValueFromData(item.get(1)).toString());
            parentLayout.setOnClickListener(v -> listener.onItemClick((ArrayList<String>) item));
        }
    }
}
