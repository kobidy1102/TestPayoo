package com.example.testmybasecode;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import com.example.testmybasecode.factory.ApplicationComponent;
import com.example.testmybasecode.factory.ApplicationModule;
import com.example.testmybasecode.factory.DaggerApplicationComponent;

import org.androidannotations.annotations.EApplication;


@SuppressLint("Registered")
@EApplication
public class MainApplication extends Application {
    protected ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        setApplicationComponent(DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build());
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

}
