package com.example.testmybasecode.domain.main.confirm;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.testmybasecode.MainApplication;
import com.example.testmybasecode.R;
import com.example.testmybasecode.domain.dialogs.OTPDialog;
import com.example.testmybasecode.service.model.PaymentResponse;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import javax.inject.Inject;

@SuppressLint("Registered")
@EActivity(R.layout.activity_confirm)
public class ConfirmActivity extends MvpActivity<ConfirmView,ConfirmPresenter> implements ConfirmView {
    @App
    protected MainApplication application;
    @Inject
    protected ConfirmPresenter presenter;
    @NonNull
    @Override
    public ConfirmPresenter createPresenter() {
        return presenter;
    }
    @AfterInject
    protected void afterInject() {
         application.getApplicationComponent().inject(this);
    }

    @Click(R.id.activity_confirm_bt_pay)
    protected void onPayClick(){
        OTPDialog otpDialog= OTPDialog.newInstance(dialog -> {
            presenter.payment();
            dialog.dismiss();
        });
        otpDialog.show(getSupportFragmentManager(),"");
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void paymentSuccess(PaymentResponse paymentResponse) {
        Toast.makeText(application, "success:"+paymentResponse.getData(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void paymentFailed(Throwable throwable) {
        Toast.makeText(application, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
        throwable.printStackTrace();
    }
}
