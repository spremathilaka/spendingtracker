package au.com.spendingtracker.di.module;

import com.google.gson.Gson;

import javax.inject.Singleton;

import au.com.spendingtracker.data.source.AccountRepository;
import au.com.spendingtracker.data.source.AccountRepositoryImpl;
import au.com.spendingtracker.data.source.remote.api.SpendingTrackerApi;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ViewModelModule.class,NetModule.class})
public class AppModule {

    @Provides
    @Singleton
    AccountRepository provideAccountRepository(Gson gson, SpendingTrackerApi spendingTrackerApi) {
        return new AccountRepositoryImpl(gson,spendingTrackerApi);
    }

}
