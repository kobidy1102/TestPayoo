package com.example.testmybasecode.factory;

import com.example.testmybasecode.domain.main.confirm.ConfirmActivity;
import com.example.testmybasecode.service.payment.PaymentService;

import dagger.Component;

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

     PaymentService paymentService();

     void inject(ConfirmActivity confirmActivity);
}
