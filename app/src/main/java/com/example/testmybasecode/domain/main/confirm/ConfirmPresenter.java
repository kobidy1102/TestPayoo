package com.example.testmybasecode.domain.main.confirm;

import com.example.testmybasecode.service.payment.PaymentService;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ConfirmPresenter extends MvpBasePresenter<ConfirmView> {
    @Inject
    protected PaymentService paymentService;

    @Inject
    public ConfirmPresenter() {
    }


    public void payment(){
        ifViewAttached(ConfirmView::showLoading);
        paymentService.payment()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate(() -> {
                    ifViewAttached(ConfirmView::hideLoading);
                })
                .subscribe(paymentResponse -> {
                    ifViewAttached(view -> {
                        view.paymentSuccess(paymentResponse);
                    });
                }, throwable -> {
                    ifViewAttached(view -> {
                        view.paymentFailed(throwable);
                    });
                });
    }
}
