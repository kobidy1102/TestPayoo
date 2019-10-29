package com.example.testmybasecode.domain.main.confirm;

import com.example.testmybasecode.service.model.PaymentResponse;
import com.hannesdorfmann.mosby3.mvp.MvpView;

public interface ConfirmView extends MvpView {
    void showLoading();
    void hideLoading();
    void paymentSuccess(PaymentResponse paymentResponse);
    void paymentFailed(Throwable throwable);
}
