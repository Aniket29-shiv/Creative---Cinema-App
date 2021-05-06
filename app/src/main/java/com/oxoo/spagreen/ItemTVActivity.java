package com.oxoo.spagreen;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.oxoo.spagreen.adapters.LiveTVAdapter;
import com.oxoo.spagreen.database.DatabaseHelper;
import com.oxoo.spagreen.models.CommonModels;
import com.oxoo.spagreen.network.RetrofitClient;
import com.oxoo.spagreen.network.apis.LiveTvApi;
import com.oxoo.spagreen.network.model.config.AdsConfig;
import com.oxoo.spagreen.network.model.Channel;
import com.oxoo.spagreen.utils.RtlUtils;
import com.oxoo.spagreen.utils.ads.BannerAds;
import com.oxoo.spagreen.utils.Constants;
import com.oxoo.spagreen.utils.NetworkInst;
import com.oxoo.spagreen.utils.SpacingItemDecoration;
import com.oxoo.spagreen.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class ItemTVActivity extends AppCompatActivity {

    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerView recyclerView;
    private LiveTVAdapter mAdapter;
    private List<CommonModels> list =new ArrayList<>();

    private boolean isLoading=false;
    private ProgressBar progressBar;
    private int pageCount=1;
    private CoordinatorLayout coordinatorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvNoItem;
    private RelativeLayout adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RtlUtils.setScreenDirection(this);
        SharedPreferences sharedPreferences = getSharedPreferences("push", MODE_PRIVATE);
        boolean isDark = sharedPreferences.getBoolean("dark", false);

        if (isDark) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_tv);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (!isDark) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.black_window_light));
        }
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //---analytics-----------
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "tv_activity");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "activity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        adView=findViewById(R.id.adView);
        progressBar=findViewById(R.id.item_progress_bar);
        shimmerFrameLayout=findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmer();
        coordinatorLayout=findViewById(R.id.coordinator_lyt);
        swipeRefreshLayout=findViewById(R.id.swipe_layout);
        tvNoItem=findViewById(R.id.tv_noitem);

        //----tv recycler view-----------------
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(this, 12), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new LiveTVAdapter(this, list);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1) && !isLoading) {

                    pageCount=pageCount+1;
                    isLoading = true;

                    progressBar.setVisibility(View.VISIBLE);

                    getData(pageCount);
                }
            }
        });


        if (new NetworkInst(this).isNetworkAvailable()){
            getData(pageCount);
        }else {
            tvNoItem.setText(getString(R.string.no_internet));
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            coordinatorLayout.setVisibility(View.VISIBLE);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                coordinatorLayout.setVisibility(View.GONE);
                pageCount=1;

                list.clear();
                recyclerView.removeAllViews();
                mAdapter.notifyDataSetChanged();

                if (new NetworkInst(ItemTVActivity.this).isNetworkAvailable()){
                    getData(pageCount);
                }else {
                    tvNoItem.setText(getString(R.string.no_internet));
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    coordinatorLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        loadAd();
    }

    private void loadAd(){
        AdsConfig adsConfig = new DatabaseHelper(ItemTVActivity.this).getConfigurationData().getAdsConfig();
        if (adsConfig.getAdsEnable().equals("1")) {

            if (adsConfig.getMobileAdsNetwork().equalsIgnoreCase(Constants.ADMOB)) {
                BannerAds.ShowAdmobBannerAds(ItemTVActivity.this, adView);

            } else if (adsConfig.getMobileAdsNetwork().equals(Constants.START_APP)) {
                BannerAds.showStartAppBanner(ItemTVActivity.this, adView);

            } else if(adsConfig.getMobileAdsNetwork().equals(Constants.NETWORK_AUDIENCE)) {
                BannerAds.showFANBanner(this, adView);
            }
        }
    }

    private void getData(int pageNum){
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        LiveTvApi api = retrofit.create(LiveTvApi.class);
        Call<List<Channel>> call = api.getFeaturedTV(AppConfig.API_KEY, pageNum);
        call.enqueue(new Callback<List<Channel>>() {
            @Override
            public void onResponse(Call<List<Channel>> call, retrofit2.Response<List<Channel>> response) {
                if (response.code() == 200){
                    isLoading=false;
                    progressBar.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    if (response.body().size() == 0 && pageCount==1){
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }else {
                        coordinatorLayout.setVisibility(View.GONE);
                    }

                    for (int i = 0; i < response.body().size(); i++){
                        Channel channel = response.body().get(i);
                        CommonModels models =new CommonModels();
                        models.setImageUrl(channel.getPosterUrl());
                        models.setTitle(channel.getTvName());
                        models.setVideoType("tv");
                        models.setId(channel.getLiveTvId());
                        list.add(models);
                    }
                    mAdapter.notifyDataSetChanged();
                }else {
                    isLoading=false;
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);
                    if (pageCount==1){
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Channel>> call, Throwable t) {
                isLoading=false;
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                if (pageCount==1){
                    coordinatorLayout.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
