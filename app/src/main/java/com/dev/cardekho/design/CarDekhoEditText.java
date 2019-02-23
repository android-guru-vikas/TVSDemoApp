package com.dev.cardekho.design;

import android.content.Context;
import android.util.AttributeSet;

public class CarDekhoEditText extends android.support.v7.widget.AppCompatEditText {

    private EditTextImeBackListener mOnImeBack;

    public CarDekhoEditText(Context context) {
        super(context);
    }

    public CarDekhoEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CarDekhoEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnEditTextImeBackListener(EditTextImeBackListener listener) {
        mOnImeBack = listener;
    }

    public interface EditTextImeBackListener {
        public void onImeBack(CarDekhoEditText ctrl, String text);
    }
}

