package com.anubhav_singh.infoedgeassignment;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ViewStub;
import android.widget.EditText;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.swipe_refresh_layout)
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.main_coordinator_layout)
    private CoordinatorLayout coordinatorLayout;

    @BindView(R.id.main_search_bar)
    private EditText etSearchBar;

    @BindView(R.id.tool_bar)
    private Toolbar toolbar;

    @BindView(R.id.main_shimmer_list)
    private ShimmerRecyclerView shimmerRecyclerView;

    @BindView(R.id.no_result_found_stub)
    private ViewStub noResultFoundViewStub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
