package com.example.testmybasecode.domain.main.confirm;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testmybasecode.MainApplication;
import com.example.testmybasecode.R;
import com.example.testmybasecode.domain.dialogs.OTPDialog;
import com.example.testmybasecode.service.model.PaymentResponse;
import com.example.testmybasecode.util.AppUtil;
import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import java.text.DecimalFormat;
import java.util.Objects;
import javax.inject.Inject;

@SuppressLint("Registered")
@EActivity(R.layout.activity_confirm)
public class ConfirmActivity extends MvpActivity<ConfirmView,ConfirmPresenter> implements ConfirmView {
    private String TAG = ConfirmActivity.class.getSimpleName();

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

    @Extra
    protected int value;
    @Extra
    protected String phoneNumber;
    @Extra
    protected String paymentMethod;

    @ViewById(R.id.activity_confirm_tv_phone_number)
    protected TextView tvPhoneNumber;
    @ViewById(R.id.activity_confirm_tv_value)
    protected TextView tvValue;
    @ViewById(R.id.activity_confirm_tv_payment_method)
    protected TextView tvPaymentMethod;
    @ViewById(R.id.activity_confirm_tv_total_amount)
    protected TextView tvTotalAmount;
    @ViewById(R.id.activity_confirm_bt_pay)
    protected Button btPay;

    private ProgressDialog progress;


    @AfterViews
    protected void initViews(){
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        AppUtil.setEffectView(btPay);
        String valueFormat=new DecimalFormat("###,###").format(value);
        tvPhoneNumber.setText(phoneNumber);
        tvValue.setText(valueFormat);
        tvPaymentMethod.setText(paymentMethod);
        tvTotalAmount.setText(valueFormat);

    }

    @Click(R.id.activity_confirm_bt_pay)
    protected void onPayClick(){
        OTPDialog otpDialog= OTPDialog.newInstance(phoneNumber,dialog -> {
            presenter.payment();

            if(dialog!=null) {
                dialog.dismiss();
            }
        });
        otpDialog.show(getSupportFragmentManager(),TAG);
    }



    @Override
    public void showLoading() {
        showLoadingDialog();
    }

    @Override
    public void hideLoading() {
        dismissLoadingDialog();
    }

    @Override
    public void paymentSuccess(PaymentResponse paymentResponse) {
        Toast.makeText(application, "Success", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"paymentSuccess: "+paymentResponse.getData());
    }

    @Override
    public void paymentFailed(Throwable throwable) {
        Toast.makeText(application, "Failed", Toast.LENGTH_SHORT).show();
        throwable.printStackTrace();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right_two, R.anim.slide_out_right);
    }


    private void showLoadingDialog() {
        if (progress == null) {
            progress = new ProgressDialog(this);
            progress.setMessage("Loading...");
        }
        progress.show();
    }

    private void dismissLoadingDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
        }
    }
}
