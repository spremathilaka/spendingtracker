package au.com.spendingtracker.ui.account;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import au.com.spendingtracker.R;
import au.com.spendingtracker.data.source.model.account.Summary;
import au.com.spendingtracker.data.source.model.account.TransactionRecord;
import au.com.spendingtracker.util.CommonUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class AccountSummarySection extends StatelessSection {

    private Summary accountSummary;
    private List<TransactionRecord> list;

    public AccountSummarySection(@NonNull Summary accountSummary) {

        super(SectionParameters.builder()
                .itemResourceId(R.layout.list_item_transaction_row)
                .headerResourceId(R.layout.list_section_header_account_summary)
                .build());
        this.accountSummary = accountSummary;
        this.list = new ArrayList<>();
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
        if (accountSummary != null) {
            headerHolder.tv_account_name.setText(accountSummary.getAccountName());
            headerHolder.tv_account_number.setText(accountSummary.getAccountNumber());
            headerHolder.tv_available_funds_amount.setText(CommonUtil.formatAmount(accountSummary.getAvailable()));
            headerHolder.tv_account_balance_amount.setText(CommonUtil.formatAmount(accountSummary.getBalance()));
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;

        @BindView(R.id.tv_transaction_description)
        TextView tv_transaction_description;

        @BindView(R.id.tv_transaction_amount)
        TextView tv_transaction_amount;

        ItemViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_account_number)
        TextView tv_account_number;

        @BindView(R.id.tv_account_name)
        TextView tv_account_name;

        @BindView(R.id.tv_available_funds_amount)
        TextView tv_available_funds_amount;

        @BindView(R.id.tv_account_balance_amount)
        TextView tv_account_balance_amount;


        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
