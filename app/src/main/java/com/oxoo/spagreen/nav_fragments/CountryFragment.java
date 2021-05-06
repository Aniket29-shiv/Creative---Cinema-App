package com.oxoo.spagreen.nav_fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.oxoo.spagreen.AppConfig;
import com.oxoo.spagreen.MainActivity;
import com.oxoo.spagreen.R;
import com.oxoo.spagreen.adapters.CountryAdapter;
import com.oxoo.spagreen.database.DatabaseHelper;
import com.oxoo.spagreen.models.CommonModels;
import com.oxoo.spagreen.models.home_content.AllCountry;
import com.oxoo.spagreen.network.RetrofitClient;
import com.oxoo.spagreen.network.apis.CountryApi;
import com.oxoo.spagreen.network.model.config.AdsConfig;
import com.oxoo.spagreen.utils.ApiResources;
import com.oxoo.spagreen.utils.ads.BannerAds;
import com.oxoo.spagreen.utils.Constants;
import com.oxoo.spagreen.utils.NetworkInst;
import com.oxoo.spagreen.utils.SpacingItemDecoration;
import com.oxoo.spagreen.utils.ToastMsg;
import com.oxoo.spagreen.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class CountryFragment extends Fragment {

    ShimmerFrameLayout shimmerFrameLayout;
    private ApiResources apiResources;
    private RecyclerView recyclerView;
    private List<CommonModels> list = new ArrayList<>();
    private CountryAdapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CoordinatorLayout coordinatorLayout;

    private TextView tvNoItem;
    private RelativeLayout adView;

    private MainActivity activity;

    private LinearLayout searchRootLayout;

    private static final int HIDE_THRESHOLD = 20;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true;

    private CardView searchBar;
    private ImageView menuIv, searchIv;
    private TextView pageTitle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.layout_country,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.country));

        adView=view.findViewById(R.id.adView);
        coordinatorLayout=view.findViewById(R.id.coordinator_lyt);
        swipeRefreshLayout=view.findViewById(R.id.swipe_layout);
        shimmerFrameLayout=view.findViewById(R.id.shimmer_view_container);
        tvNoItem=view.findViewById(R.id.tv_noitem);
        searchRootLayout=view.findViewById(R.id.search_root_layout);
        searchBar           = view.findViewById(R.id.search_bar);
        menuIv              = view.findViewById(R.id.bt_menu);
        pageTitle           = view.findViewById(R.id.page_title_tv);
        searchIv            = view.findViewById(R.id.search_iv);

        pageTitle.setText(getContext().getResources().getString(R.string.country));

        if (activity.isDark) {
            pageTitle.setTextColor(activity.getResources().getColor(R.color.white));
            searchBar.setCardBackgroundColor(activity.getResources().getColor(R.color.black_window_light));
            menuIv.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_menu));
            searchIv.setImageDrawable(activity.getResources().getDrawable(R.drawable.ic_search_white));
        }

        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new SpacingItemDecoration(3, Tools.dpToPx(getActivity(), 10), true));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mAdapter = new CountryAdapter(activity, list, "");
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                    animateSearchBar(true);
                    controlsVisible = false;
                    scrolledDistance = 0;
                } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                    animateSearchBar(false);
                    controlsVisible = true;
                    scrolledDistance = 0;
                }

                if((controlsVisible && dy>0) || (!controlsVisible && dy<0)) {
                    scrolledDistance += dy;
                }


            }
        });

        shimmerFrameLayout.startShimmer();


        if (new NetworkInst(getContext()).isNetworkAvailable()){
            getAllCountry();
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

                recyclerView.removeAllViews();
                list.clear();
                mAdapter.notifyDataSetChanged();

                if (new NetworkInst(getContext()).isNetworkAvailable()){
                    getAllCountry();
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

    @Override
    public void onStart() {
        super.onStart();

        menuIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.openDrawer();
            }
        });
        searchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.goToSearchActivity();
            }
        });

    }

    private void loadAd(){
        AdsConfig adsConfig = new DatabaseHelper(getContext()).getConfigurationData().getAdsConfig();
        if (adsConfig.getAdsEnable().equals("1")) {

            if (adsConfig.getMobileAdsNetwork().equalsIgnoreCase(Constants.ADMOB)) {
                BannerAds.ShowAdmobBannerAds(getActivity(), adView);

            } else if (adsConfig.getMobileAdsNetwork().equals(Constants.START_APP)) {
                BannerAds.showStartAppBanner(getActivity(), adView);

            } else if(adsConfig.getMobileAdsNetwork().equals(Constants.NETWORK_AUDIENCE)) {
                BannerAds.showFANBanner(getActivity(), adView);
            }
        }
    }


    private void getAllCountry(){
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        CountryApi api = retrofit.create(CountryApi.class);
        Call<List<AllCountry>> call = api.getAllCountry(AppConfig.API_KEY);
        call.enqueue(new Callback<List<AllCountry>>() {
            @Override
            public void onResponse(Call<List<AllCountry>> call, retrofit2.Response<List<AllCountry>> response) {
                if (response.code() == 200){
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    swipeRefreshLayout.setRefreshing(false);

                    if (response.body().size() == 0){
                        coordinatorLayout.setVisibility(View.VISIBLE);
                    }else {
                        coordinatorLayout.setVisibility(View.GONE);
                    }

                    for (int i = 0; i < response.body().size(); i++) {
                        AllCountry country = response.body().get(i);
                        CommonModels models = new CommonModels();
                        models.setId(country.getCountryId());
                        models.setTitle(country.getName());
                        models.setImageUrl(country.getImageUrl());
                        list.add(models);
                    }
                    mAdapter.notifyDataSetChanged();
                }else {
                    swipeRefreshLayout.setRefreshing(false);
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    new ToastMsg(getActivity()).toastIconError(getString(R.string.fetch_error));
                    coordinatorLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<AllCountry>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                new ToastMsg(getActivity()).toastIconError(getString(R.string.fetch_error));
                coordinatorLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    boolean isSearchBarHide = false;

    private void animateSearchBar(final boolean hide) {
        if (isSearchBarHide && hide || !isSearchBarHide && !hide) return;
        isSearchBarHide = hide;
        int moveY = hide ? -(2 * searchRootLayout.getHeight()) : 0;
        searchRootLayout.animate().translationY(moveY).setStartDelay(100).setDuration(300).start();
    }

}
