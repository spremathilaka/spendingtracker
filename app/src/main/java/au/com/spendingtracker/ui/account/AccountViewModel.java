package au.com.spendingtracker.ui.account;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import au.com.spendingtracker.data.source.AccountRepository;
import au.com.spendingtracker.data.source.model.account.AccountData;
import au.com.spendingtracker.data.source.model.common.ApiResponse;
import au.com.spendingtracker.util.SingleLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static au.com.spendingtracker.data.source.model.common.Status.SUCCESS;

public class AccountViewModel extends ViewModel {

    private SingleLiveData<ApiResponse<AccountData>> apiResponse = new SingleLiveData<>();

    private AccountRepository accountRepository;

    private final CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    public AccountViewModel(@NonNull AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void init() {
        disposables.add(accountRepository.getUserAccountDetails()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> apiResponse.setValue(ApiResponse.loading()))
                .subscribe(data -> {
                            ApiResponse responseData = new ApiResponse(SUCCESS, data, null);
                            apiResponse.setValue(responseData);
                        },
                        throwable -> {
                            apiResponse.setValue(ApiResponse.error((Throwable) throwable));
                        }));
    }

    public LiveData<ApiResponse<AccountData>> getApiResponse() {
        return this.apiResponse;
    }

    @Override
    protected void onCleared() {
        if (disposables != null) {
            disposables.clear();
        }
    }
}
