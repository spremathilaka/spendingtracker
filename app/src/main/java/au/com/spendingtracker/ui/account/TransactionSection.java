package au.com.spendingtracker.ui.account;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import au.com.spendingtracker.R;
import au.com.spendingtracker.data.source.model.account.Atm;
import au.com.spendingtracker.data.source.model.account.TransactionRecord;
import au.com.spendingtracker.ui.findus.AtmDetailsDTO;
import au.com.spendingtracker.ui.findus.FindUsActivity;
import au.com.spendingtracker.util.CommonUtil;
import au.com.spendingtracker.util.TimeAgo;
import au.com.spendingtracker.util.TransactionType;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

import static au.com.spendingtracker.util.Keys.PARAM_KEY_ATM_LOCATION_DATA;

public class TransactionSection extends StatelessSection {
    private final Date transaction_date;
    private final List<TransactionRecord> list;
    private final SectionedRecyclerViewAdapter sectionAdapter;
    private final Context context;

    public TransactionSection(Context context, SectionedRecyclerViewAdapter adapter, Date date, List<TransactionRecord> list) {

        super(SectionParameters.builder()
                .itemResourceId(R.layout.list_item_transaction_row)
                .headerResourceId(R.layout.list_section_header_transaction)
                .build());
        this.sectionAdapter = adapter;
        this.context = context;
        this.transaction_date = date;
        this.list = list;
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
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;
        final TransactionRecord transactionRecord = list.get(position);

        itemHolder.iv_atm_location.setVisibility(transactionRecord.getAtmDetails() == null ? View.INVISIBLE : View.VISIBLE);

        setTransactionDescription(itemHolder.tv_transaction_description, transactionRecord);
        itemHolder.tv_transaction_amount.setText(CommonUtil.formatAmount(transactionRecord.getAmount()));

        itemHolder.rootView.setOnClickListener(v -> {
            TransactionRecord transactionData = list.get(sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition()));
            if (transactionData != null && transactionData.getAtmDetails() != null) {

                Intent findUsIntent = new Intent(context, FindUsActivity.class);
                findUsIntent.putExtra(PARAM_KEY_ATM_LOCATION_DATA,
                        buildATMDetailsDTO(transactionData.getAtmDetails()));
                context.startActivity(findUsIntent);
            }

        });
    }

    private AtmDetailsDTO buildATMDetailsDTO(@NonNull Atm atmData) {
        return new AtmDetailsDTO.AtmDetailsDTOBuilder()
                .setAddress(atmData.getAddress())
                .setId(atmData.getId())
                .setName(atmData.getName())
                .setLat(atmData.getLocation().getLat())
                .setLng(atmData.getLocation().getLng())
                .createAtmDetailsDTO();
    }

    private void setTransactionDescription(TextView textview, TransactionRecord transactionRecord) {
        if (transactionRecord.getTransactionType() == TransactionType.CLEARED) {
            textview.setText(transactionRecord.getDescription());
        } else {
            SpannableStringBuilder str = new SpannableStringBuilder(TransactionType.PENDING.name() + ": " + transactionRecord.getDescription());
            str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, TransactionType.PENDING.name().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textview.setText(str);
        }
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        setTransactionDate(headerHolder.tv_transaction_date, transaction_date);
        setTimeAgo(headerHolder.tv_relative_date, transaction_date);
    }

    private void setTimeAgo(TextView textView, Date date) {
        textView.setText(DateUtils.getRelativeDateTimeString(context, transaction_date.getTime(), DateUtils.DAY_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, 0));
        textView.setText(TimeAgo.getTimeAgo(date.getTime()));
    }

    private void setTransactionDate(TextView textView, Date date) {
        textView.setText(CommonUtil.formatDate(date));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        View rootView;
        @BindView(R.id.tv_transaction_description)
        TextView tv_transaction_description;

        @BindView(R.id.tv_transaction_amount)
        TextView tv_transaction_amount;

        @BindView(R.id.iv_atm_location)
        ImageView iv_atm_location;


        ItemViewHolder(View view) {
            super(view);
            rootView = view;
            ButterKnife.bind(this, view);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_transaction_date)
        TextView tv_transaction_date;

        @BindView(R.id.tv_relative_date)
        TextView tv_relative_date;


        HeaderViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
