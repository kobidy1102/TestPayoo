package com.example.testmybasecode.service.payment;

import com.example.testmybasecode.service.model.PaymentResponse;

import retrofit2.http.GET;
import rx.Observable;

public interface PaymentService {
    @GET("sampleAPI")
    Observable<PaymentResponse> payment();
}
