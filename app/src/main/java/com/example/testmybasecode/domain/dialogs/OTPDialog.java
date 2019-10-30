package com.example.testmybasecode.domain.dialogs;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testmybasecode.R;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Objects;

@EFragment(R.layout.dialog_otp)
public class OTPDialog extends DialogFragment {

    @ViewById(R.id.dialog_otp_tv_otp_desciption)
    protected TextView tvOtpDesciption;
    @ViewById(R.id.dialog_otp_edt_otp)
    protected EditText edtOtp;

    private OTPListener otpListener;
    private String phoneNumber;

    public static OTPDialog newInstance(String phoneNumber, OTPListener otpListener) {
        OTPDialog otpDialog = OTPDialog_.builder().build();
        otpDialog.setOTPListener(otpListener);
        otpDialog.phoneNumber=phoneNumber;
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
        tvOtpDesciption.setText(Objects.requireNonNull(getContext()).getResources().getString(R.string.otp_desciption,phoneNumber));
    }

    @Override
    public void onActivityCreated(Bundle arg0) {
        super.onActivityCreated(arg0);
        if (getDialog().getWindow() != null) {
            getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        }
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
        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = 0.85f;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
        super.onResume();
    }

    @Click(R.id.dialog_otp_tv_close)
    void onCloseClick() {
            dismiss();
    }

    @Click(R.id.dialog_otp_tv_ok)
    protected void onOkClick(){
        if(edtOtp.getText().toString().isEmpty()){
            Toast.makeText(getContext(), Objects.requireNonNull(getContext()).getResources().getString(R.string.otp_empty)
                    , Toast.LENGTH_SHORT).show();
            return;
        }
        otpListener.onOkClick(this);
    }

    public interface OTPListener {
        void onOkClick(OTPDialog dialog);
    }

    private void setOTPListener(OTPListener otpListener) {
        this.otpListener = otpListener;
    }
}
