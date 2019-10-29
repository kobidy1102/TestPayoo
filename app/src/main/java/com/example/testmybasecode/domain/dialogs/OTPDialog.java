package com.example.testmybasecode.domain.dialogs;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.testmybasecode.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.dialog_otp)
public class OTPDialog extends DialogFragment {

    private OTPListener otpListener;

    public static OTPDialog newInstance(OTPListener otpListener) {
        OTPDialog otpDialog = OTPDialog_.builder().build();
        otpDialog.setOTPListener(otpListener);
        return otpDialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    void init() {
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().setGravity(Gravity.CENTER);
        }
    }

    @NonNull
    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
//        if (getDialog().getWindow() != null) {
////            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
////        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.requestFeature(Window.FEATURE_NO_TITLE);
        }
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = 0.85f;
            // Assign window properties to fill the parent
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
        // Call super onResume after sizing
        super.onResume();
    }

    @Click(R.id.dialog_otp_tv_close)
    void onCloseClick() {
            dismiss();
    }

    @Click(R.id.dialog_otp_tv_ok)
    protected void onOkClick(){
        otpListener.onOkClick(this);
    }

    public interface OTPListener {
        void onOkClick(OTPDialog dialog);
    }

    private void setOTPListener(OTPListener otpListener) {
        this.otpListener = otpListener;
    }
}
