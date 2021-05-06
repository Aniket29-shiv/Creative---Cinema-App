package com.oxoo.spagreen.nav_fragments;

import android.content.SharedPreferences;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;

import com.oxoo.spagreen.MainActivity;
import com.oxoo.spagreen.R;
import com.oxoo.spagreen.fragments.HomeFragment;
import com.oxoo.spagreen.fragments.LiveTvFragment;
import com.oxoo.spagreen.fragments.MoviesFragment;
import com.oxoo.spagreen.fragments.TvSeriesFragment;
import com.volcaniccoder.bottomify.BottomifyNavigationView;
import com.volcaniccoder.bottomify.OnNavigationItemChangeListener;

import org.jetbrains.annotations.NotNull;

import static android.content.Context.MODE_PRIVATE;

public class MainHomeFragment extends Fragment {
    private MainActivity activity;
    private BottomifyNavigationView bottomifyNavigationViewDark, bottomifyNavigationViewLight;
    LinearLayout searchRootLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (MainActivity) getActivity();
        return inflater.inflate(R.layout.fragment_main_home, container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomifyNavigationViewDark = view.findViewById(R.id.bottomify_nav);
        bottomifyNavigationViewLight = view.findViewById(R.id.bottomify_nav_light);
        searchRootLayout = view.findViewById(R.id.search_root_layout);

        SharedPreferences sharedPreferences = activity.getSharedPreferences("push", MODE_PRIVATE);
        boolean isDark = sharedPreferences.getBoolean("dark", false);

        if (isDark) {
            //bottomifyNavigationView
            bottomifyNavigationViewDark.setVisibility(View.VISIBLE);
            bottomifyNavigationViewDark.setBackgroundColor(getResources().getColor(R.color.black_window_light));
        } else {
            //bottomifyNavigationView light
            bottomifyNavigationViewLight.setVisibility(View.VISIBLE);
            bottomifyNavigationViewLight.setBackgroundColor(getResources().getColor(R.color.white));
        }

        //bottomifyNavigationView
        bottomifyNavigationViewDark.setActiveNavigationIndex(0);
        bottomifyNavigationViewDark.setOnNavigationItemChangedListener(new OnNavigationItemChangeListener() {
            @Override
            public void onNavigationItemChanged(@NotNull BottomifyNavigationView.NavigationItem navigationItem) {
                switch (navigationItem.getPosition()) {
                    case 0:
                        loadFragment(new HomeFragment());
                        break;
                    case 1:
                        loadFragment(new MoviesFragment());
                        break;
                    case 2:
                        loadFragment(new LiveTvFragment());
                        break;
                    case 3:
                        loadFragment(new TvSeriesFragment());
                        break;
                    case 4:
                        loadFragment(new FavoriteFragment());
                        break;

                }
            }
        });

        //bottomify light
        bottomifyNavigationViewLight.setActiveNavigationIndex(0);
        bottomifyNavigationViewLight.setOnNavigationItemChangedListener(new OnNavigationItemChangeListener() {
            @Override
            public void onNavigationItemChanged(@NotNull BottomifyNavigationView.NavigationItem navigationItem) {
                switch (navigationItem.getPosition()){
                    case 0:
                        loadFragment(new HomeFragment());
                        break;
                    case 1:
                        loadFragment(new MoviesFragment());
                        break;
                    case 2:
                        loadFragment(new LiveTvFragment());
                        break;
                    case 3:
                        loadFragment(new TvSeriesFragment());
                        break;
                    case 4:
                        loadFragment(new FavoriteFragment());
                        break;

                }
            }
        });


        loadFragment(new HomeFragment());

    }

    //----load fragment----------------------
    private boolean loadFragment(Fragment fragment){
        if (fragment!=null){
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();

            return true;
        }
        return false;

    }


}