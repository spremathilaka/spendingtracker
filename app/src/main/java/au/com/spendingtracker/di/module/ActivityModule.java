package au.com.spendingtracker.di.module;

import au.com.spendingtracker.ui.account.AccountActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector()
    abstract AccountActivity contributeAccountActivity();
}
