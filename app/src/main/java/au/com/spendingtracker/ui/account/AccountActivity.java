package au.com.spendingtracker.ui.account;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import au.com.spendingtracker.R;
import au.com.spendingtracker.data.source.model.account.AccountData;
import au.com.spendingtracker.data.source.model.account.TransactionRecord;
import au.com.spendingtracker.data.source.model.common.ApiResponse;
import au.com.spendingtracker.ui.base.BaseAppCompatActivity;
import au.com.spendingtracker.util.NetworkUtil;
import butterknife.BindView;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class AccountActivity extends BaseAppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    AccountViewModel viewModel;

    @BindView(R.id.transaction_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar_cyclic)
    ProgressBar progressDialog;

    private SectionedRecyclerViewAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        this.configureDagger();
        setTitle(getString(R.string.title_account_details));
        setupUI();
        this.configureViewModel();
    }

    private void setupUI() {

        sectionAdapter = new SectionedRecyclerViewAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sectionAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
        recyclerView.addItemDecoration(divider);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    private void configureViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AccountViewModel.class);
        if (!NetworkUtil.checkInternetConnection(this)) {
            Toast.makeText(this,getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
        }else {
            viewModel.init();
            if (viewModel.getApiResponse() != null) {
                viewModel.getApiResponse().observe(this, apiResponse -> consumeResponse(apiResponse));
            }
        }
    }

    // -----------------
    // UPDATE UI
    // -----------------

    private void consumeResponse(ApiResponse apiResponse) {

        switch (apiResponse.getStatus()) {

            case LOADING:
                progressDialog.setVisibility(View.VISIBLE);
                break;

            case SUCCESS:
                progressDialog.setVisibility(View.GONE);
                renderSuccessResponse((AccountData) apiResponse.getData());
                break;

            case ERROR:
                progressDialog.setVisibility(View.GONE);
                Toast.makeText(AccountActivity.this, getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    /*
     * method to handle success response
     * */
    private void renderSuccessResponse(@Nullable AccountData account) {

        if (account != null) {
            recyclerView.setVisibility(View.VISIBLE);
            sectionAdapter.addSection(new AccountSummarySection(account.getAccountSummary()));

            if (account.getTransactions() != null && !account.getTransactions().isEmpty()) {
                for (Map.Entry<Date, List<TransactionRecord>> entry : account.getTransactions().entrySet()) {
                    sectionAdapter.addSection(new TransactionSection(this, sectionAdapter, entry.getKey(), entry.getValue()));
                }

                recyclerView.swapAdapter(sectionAdapter, false);
            }


        } else {
            Toast.makeText(AccountActivity.this, getResources().getString(R.string.errorString), Toast.LENGTH_SHORT).show();
        }
    }

    private void configureDagger() {
        AndroidInjection.inject(this);
    }
}
