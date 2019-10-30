package com.example.testmybasecode.factory;


import android.app.Application;
import com.example.testmybasecode.ApiUrls;
import com.example.testmybasecode.service.payment.PaymentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApplicationModule {

    private Application application;
    public ApplicationModule(Application application) {
        this.application = application;
    }
    public Application getApplication() {
        return application;
    }

    @Provides
    Retrofit provideRetrofit() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(ApiUrls.SERVER_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


    @Provides
    PaymentService paymentService(Retrofit retrofit){
        return  retrofit.create(PaymentService.class);
    }


}
