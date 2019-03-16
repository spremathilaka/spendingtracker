package au.com.spendingtracker.data.source;

import au.com.spendingtracker.data.source.model.account.AccountData;
import io.reactivex.Single;


public interface AccountRepository {

    Single<AccountData> getUserAccountDetails();
}
