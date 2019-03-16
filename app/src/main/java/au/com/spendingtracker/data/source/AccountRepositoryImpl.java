package au.com.spendingtracker.data.source;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import au.com.spendingtracker.data.source.model.account.Account;
import au.com.spendingtracker.data.source.model.account.AccountData;
import au.com.spendingtracker.data.source.model.account.Atm;
import au.com.spendingtracker.data.source.model.account.PendingTransaction;
import au.com.spendingtracker.data.source.model.account.Transaction;
import au.com.spendingtracker.data.source.model.account.TransactionRecord;
import au.com.spendingtracker.data.source.remote.api.SpendingTrackerApi;
import au.com.spendingtracker.util.TransactionType;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

public class AccountRepositoryImpl implements AccountRepository {

    private final String TAG = AccountRepositoryImpl.class.getName();

    private Gson gson;

    private SpendingTrackerApi spendingTrackerApi;

    @Inject
    public AccountRepositoryImpl(@NonNull Gson gson, @NonNull SpendingTrackerApi spendingTrackerApi) {
        this.spendingTrackerApi = spendingTrackerApi;
        this.gson = gson;
    }

    @Override
    public Single<AccountData> getUserAccountDetails() {
        return spendingTrackerApi.downloadFileData(SpendingTrackerApi.REMOTE_DATA_URL)
                .flatMap((Function<ResponseBody, SingleSource<AccountData>>) responseBody -> {
                    if (responseBody != null) {
                        try {
                            Reader reader = new InputStreamReader(responseBody.byteStream());
                            Account accountObj = gson.fromJson(reader, Account.class);
                            Log.d(TAG, "Downloaded file data" + accountObj.getSummary().getAccountName());

                            List<TransactionRecord> transactionRecords = new ArrayList<>();
                            if (accountObj != null) {
                                if (accountObj.getPendingTransaction() != null && !accountObj.getPendingTransaction().isEmpty()) {
                                    for (PendingTransaction pendingTransaction : accountObj.getPendingTransaction()) {
                                        transactionRecords.add(new TransactionRecord.TransactionRecordBuilder()
                                                .setId(pendingTransaction.getId())
                                                .setDescription(pendingTransaction.getDescription())
                                                .setEffectiveDate(pendingTransaction.getEffectiveDate())
                                                .setAmount(pendingTransaction.getAmount())
                                                .setTransactionType(TransactionType.PENDING)
                                                .createTransactionRecord());
                                    }
                                }

                                if (accountObj.getTransactions() != null && !accountObj.getTransactions().isEmpty()) {
                                    for (Transaction clearedTransaction : accountObj.getTransactions()) {
                                        transactionRecords.add(new TransactionRecord.TransactionRecordBuilder()
                                                .setId(clearedTransaction.getId())
                                                .setDescription(clearedTransaction.getDescription())
                                                .setEffectiveDate(clearedTransaction.getEffectiveDate())
                                                .setAmount(clearedTransaction.getAmount())
                                                .setTransactionType(TransactionType.CLEARED)
                                                .setAtmDetails(findATMDetails(accountObj.getAtms(), clearedTransaction.getAtmId()))
                                                .createTransactionRecord());
                                    }
                                }
                            }

                            AccountData accountData = new AccountData(accountObj.getSummary(), groupTransactionByDate(transactionRecords));
                            return Single.just(accountData);
                        } catch (Exception e) {
                            Log.e(TAG, "error", e);
                        }
                    }
                    return Single.error(new Throwable("NO DATA FOUND"));
                });
    }


    private Map<Date, List<TransactionRecord>> groupTransactionByDate(@NonNull List<TransactionRecord> transactionList){
        Map<Date, List<TransactionRecord>> groupByTransactions = new TreeMap<>(Collections.reverseOrder()) ;
        if (transactionList != null && !transactionList.isEmpty()) {
            for (TransactionRecord transaction : transactionList) {
                Date key  = transaction.getEffectiveDate();
                if(groupByTransactions.containsKey(key)){
                    List<TransactionRecord> list = groupByTransactions.get(key);
                    list.add(transaction);
                }else{
                    List<TransactionRecord> list = new ArrayList<>();
                    list.add(transaction);
                    groupByTransactions.put(key, list);
                }

            }
        }
        return groupByTransactions;
    }


    private Atm findATMDetails(List<Atm> atmList, @NonNull String atmId) {
        if (atmList != null && !atmList.isEmpty()) {
            for (Atm atm : atmList) {
                if (atm.getId().equals(atmId)) {
                    return atm;
                }
            }
        }
        return null;
    }
}
