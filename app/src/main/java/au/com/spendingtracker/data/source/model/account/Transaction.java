
package au.com.spendingtracker.data.source.model.account;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class Transaction implements Comparable<Transaction> {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("effectiveDate")
    @Expose
    private Date effectiveDate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("amount")
    @Expose
    private Double amount;
    @SerializedName("atmId")
    @Expose
    private String atmId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAtmId() {
        return atmId;
    }

    public void setAtmId(String atmId) {
        this.atmId = atmId;
    }

    @Override
    public int compareTo(@NonNull Transaction transaction) {
        return this.effectiveDate.compareTo(transaction.getEffectiveDate());
    }
}
