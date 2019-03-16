package au.com.spendingtracker.data.source.model.account;


import java.util.Date;

import au.com.spendingtracker.util.TransactionType;



public class TransactionRecord {

    private String id;

    private Date effectiveDate;

    private String description;

    private Double amount;

    private Atm atmDetails;

    private TransactionType transactionType;

    private TransactionRecord(String id, Date effectiveDate, String description, Double amount, Atm atmDetails, TransactionType transactionType) {
        this.id = id;
        this.effectiveDate = effectiveDate;
        this.description = description;
        this.amount = amount;
        this.atmDetails = atmDetails;
        this.transactionType = transactionType;
    }

    public String getId() {
        return id;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public Atm getAtmDetails() {
        return atmDetails;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public static  class TransactionRecordBuilder {
        private String id;
        private Date effectiveDate;
        private String description;
        private Double amount;
        private Atm atmDetails;
        private TransactionType transactionType;

        public TransactionRecordBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public TransactionRecordBuilder setEffectiveDate(Date effectiveDate) {
            this.effectiveDate = effectiveDate;
            return this;
        }

        public TransactionRecordBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public TransactionRecordBuilder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public TransactionRecordBuilder setAtmDetails(Atm atmDetails) {
            this.atmDetails = atmDetails;
            return this;
        }

        public TransactionRecordBuilder setTransactionType(TransactionType transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public TransactionRecord createTransactionRecord() {
            return new TransactionRecord(id, effectiveDate, description, amount, atmDetails, transactionType);
        }
    }
}
