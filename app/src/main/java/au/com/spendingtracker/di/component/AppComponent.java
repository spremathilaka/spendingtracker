package au.com.spendingtracker.di.component;

import javax.inject.Singleton;

import au.com.spendingtracker.SpendingTrackerApp;
import au.com.spendingtracker.di.module.ActivityModule;
import au.com.spendingtracker.di.module.AppModule;
import au.com.spendingtracker.di.module.NetModule;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules={AndroidSupportInjectionModule.class, ActivityModule.class,NetModule.class, AppModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(SpendingTrackerApp application);

        @BindsInstance
        Builder appModule(AppModule appModule);

        @BindsInstance
        Builder netModule(NetModule netModule);

        AppComponent build();
    }

    void inject(SpendingTrackerApp app);
}