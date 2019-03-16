
package au.com.spendingtracker.data.source.model.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Account {

    @SerializedName("account")
    @Expose
    private Summary summary;
    @SerializedName("transactions")
    @Expose
    private List<Transaction> transactions;
    @SerializedName("pending")
    @Expose
    private List<PendingTransaction> pendingTransaction;
    @SerializedName("atms")
    @Expose
    private List<Atm> atms;

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<PendingTransaction> getPendingTransaction() {
        return pendingTransaction;
    }

    public void setPendingTransaction(List<PendingTransaction> pendingTransaction) {
        this.pendingTransaction = pendingTransaction;
    }

    public List<Atm> getAtms() {
        return atms;
    }

    public void setAtms(List<Atm> atms) {
        this.atms = atms;
    }
}
