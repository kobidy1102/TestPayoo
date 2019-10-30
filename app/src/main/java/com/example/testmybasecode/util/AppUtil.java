package com.example.testmybasecode.util;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class AppUtil {

    @SuppressLint("ClickableViewAccessibility")
    public static void setEffectView(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        view.setScaleX(0.9f);
                        view.setScaleY(0.9f);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        view.setScaleX(1f);
                        view.setScaleY(1f);
                        view.invalidate();
                        break;
                }
                return false;
            });
        }
    }
}
