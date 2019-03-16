package au.com.spendingtracker.data.source.model.account;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class AccountData {

    private Summary accountSummary;

    private Map<Date, List<TransactionRecord>> transactions;


    public AccountData(Summary accountSummary, Map<Date, List<TransactionRecord>> transactions) {
        this.accountSummary = accountSummary;
        this.transactions = transactions;
    }

    public Summary getAccountSummary() {
        return accountSummary;
    }

    public void setAccountSummary(Summary accountSummary) {
        this.accountSummary = accountSummary;
    }

    public Map<Date, List<TransactionRecord>> getTransactions() {
        return transactions;
    }

    public void setTransactions(Map<Date, List<TransactionRecord>> transactions) {
        this.transactions = transactions;
    }
}
