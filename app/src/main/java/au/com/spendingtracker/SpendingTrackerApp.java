package au.com.spendingtracker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import javax.inject.Inject;

import au.com.spendingtracker.di.component.DaggerAppComponent;
import au.com.spendingtracker.di.module.AppModule;
import au.com.spendingtracker.di.module.NetModule;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class SpendingTrackerApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initDagger();
        context = getApplicationContext();
    }

    private void initDagger(){
        DaggerAppComponent.builder()
                .application(this)
                .appModule(new AppModule())
                .netModule(new NetModule())
                .build()
                .inject(this);
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
