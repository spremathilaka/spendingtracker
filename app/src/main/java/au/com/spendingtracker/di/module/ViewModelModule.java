package au.com.spendingtracker.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import au.com.spendingtracker.common.view_models.FactoryViewModel;
import au.com.spendingtracker.di.key.ViewModelKey;
import au.com.spendingtracker.ui.account.AccountViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountViewModel.class)
    abstract ViewModel bindAccountViewModel(AccountViewModel repoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}