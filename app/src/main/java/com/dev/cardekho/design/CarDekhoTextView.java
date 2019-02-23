package com.dev.cardekho.design;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;

import com.dev.cardekho.R;
import com.dev.cardekho.application.CarDekhoApp;

import java.net.URLDecoder;

public class CarDekhoTextView extends android.support.v7.widget.AppCompatTextView {

    public CarDekhoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFontFaceType(context, attrs);

    }

    private void setFontFaceType(Context context, AttributeSet attrs) {
        String f_family;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.myCustomTextView);
        if (a == null) {
            setTypeface(CarDekhoApp.getInstance().droidTypeFace, Typeface.NORMAL);
            return;
        }
        f_family = a.getString(R.styleable.myCustomTextView_f_family);
        if (f_family == null) {
            setTypeface(CarDekhoApp.getInstance().droidTypeFace, Typeface.NORMAL);
            return;
        }
    }

    public void setText(String text) {
        try {
            text = URLDecoder.decode(text, "UTF-8");
            text = Html.fromHtml(text).toString();
            super.setText(text);
        } catch (Exception e) {

        }
    }

}
