package com.oxoo.spagreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.oxoo.spagreen.utils.Constants;
import com.oxoo.spagreen.utils.NetworkInst;
import com.oxoo.spagreen.utils.RtlUtils;
import com.oxoo.spagreen.utils.ToastMsg;
import com.oxoo.spagreen.widget.RangeSeekBar;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private boolean isDark;
    private RangeSeekBar range_seek_bar;
    private TextView year_min, year_max, range_tv;
    private Button search_btn, clear_btn, btn_flex_1, btn_flex_2, btn_flex_3;
    private EditText search_edit_text;
    private LinearLayout rangeLayout;
    private RelativeLayout tvCategoryLayout, genreLayout, countryLayout;
    private EditText genreSpinner, countrySpinner, tvCategorySpinner;
    private boolean [] selectedType = new boolean[3];
    private int selectedGenreId = 0;
    private int selectedTvCategoryId = 0;
    private int selectedCountryId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RtlUtils.setScreenDirection(this);
        SharedPreferences sharedPreferences = getSharedPreferences("push", MODE_PRIVATE);
        isDark = sharedPreferences.getBoolean("dark", false);

        if (isDark) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);

        initComponent();

        if (!isDark) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            search_btn.setBackgroundResource(R.drawable.btn_rect_primary);
            search_btn.setTextColor(getResources().getColor(R.color.white));
            genreSpinner.setBackground(getResources().getDrawable(R.drawable.edit_text_round_bg_overlay_light));
            tvCategorySpinner.setBackground(getResources().getDrawable(R.drawable.edit_text_round_bg_overlay_light));
            countrySpinner.setBackground(getResources().getDrawable(R.drawable.edit_text_round_bg_overlay_light));
            //flex btn
            btn_flex_1.setBackground(getResources().getDrawable(R.drawable.btn_rounded_primary_outline_flex));
            btn_flex_2.setBackground(getResources().getDrawable(R.drawable.btn_rounded_primary_outline_flex));
            btn_flex_3.setBackground(getResources().getDrawable(R.drawable.btn_rounded_primary_outline_flex));
        }else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.black_window_light));
            search_btn.setBackgroundResource(R.drawable.btn_rect_grey_outline);
            search_btn.setTextColor(getResources().getColor(R.color.white));
            clear_btn.setTextColor(getResources().getColor(R.color.white));
            range_seek_bar.setBarHighlightColor(getResources().getColor(R.color.grey_60));
            range_seek_bar.setRightThumbColor(getResources().getColor(R.color.grey_60));
            range_seek_bar.setRightThumbHighlightColor(getResources().getColor(R.color.grey_90));
            range_seek_bar.setLeftThumbColor(getResources().getColor(R.color.grey_60));
            range_seek_bar.setLeftThumbHighlightColor(getResources().getColor(R.color.grey_90));
            //spinner
            genreSpinner.setBackground(getResources().getDrawable(R.drawable.edit_text_round_bg_overlay_dark));
            tvCategorySpinner.setBackground(getResources().getDrawable(R.drawable.edit_text_round_bg_overlay_dark));
            countrySpinner.setBackground(getResources().getDrawable(R.drawable.edit_text_round_bg_overlay_dark));
            //flex btn
            btn_flex_1.setBackground(getResources().getDrawable(R.drawable.btn_rounded_grey_outline_flex));
            btn_flex_2.setBackground(getResources().getDrawable(R.drawable.btn_rounded_grey_outline_flex));
            btn_flex_3.setBackground(getResources().getDrawable(R.drawable.btn_rounded_grey_outline_flex));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //---analytics-----------
        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "profile_activity");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "activity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


    }

    private void initComponent() {
        search_btn = findViewById(R.id.search_btn);
        clear_btn = findViewById(R.id.clear_btn);
        genreLayout = findViewById(R.id.genre_layout);
        genreSpinner = findViewById(R.id.genre_spinner);
        tvCategoryLayout = findViewById(R.id.tv_category_layout);
        tvCategorySpinner = findViewById(R.id.tv_category_spinner);
        countryLayout = findViewById(R.id.country_layout);
        countrySpinner = findViewById(R.id.country_spinner);
        btn_flex_1 = findViewById(R.id.btn_flex_1);
        btn_flex_2 = findViewById(R.id.btn_flex_2);
        btn_flex_3 = findViewById(R.id.btn_flex_3);
        btn_flex_1.setSelected(true);
        btn_flex_2.setSelected(true);
        btn_flex_3.setSelected(true);
        selectedType[0] = true;
        selectedType[1] = true;
        selectedType[2] = true;
        //populate genre list
        List<String> genreList = new ArrayList<>();
        if (Constants.genreList != null){
            genreList.add(0, "All Genres");
            for (int i = 0; i < Constants.genreList.size(); i++) {
                genreList.add((i + 1), Constants.genreList.get(i).getName());
            }
        }
        final String[] genreArray = new String[genreList.size()];
        for (int i = 0; i < genreList.size(); i++) {
            genreArray[i] = genreList.get(i);
        }

        genreSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                builder.setTitle("Select Genre");
                builder.setSingleChoiceItems(genreArray, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((TextView) v).setText(genreArray[i]);
                        if (i != 0)
                            selectedGenreId = Integer.parseInt(Constants.genreList.get(i - 1).getGenreId());
                        else
                            selectedGenreId = 0;
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

        //setup tv category spinner
        List<String> tvCategoryList = new ArrayList<>();
        if (Constants.tvCategoryList != null){
            tvCategoryList.add(0, "All Categories");
            for (int i = 0; i < Constants.tvCategoryList.size(); i++) {
                tvCategoryList.add((i + 1), Constants.tvCategoryList.get(i).getLiveTvCategory());
            }
        }
        final String[] tvCategoryArray = new String[tvCategoryList.size()];
        for (int i = 0; i < tvCategoryList.size(); i++) {
            tvCategoryArray[i] = tvCategoryList.get(i);
        }

        tvCategorySpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                builder.setTitle("Select Tv Category");
                builder.setSingleChoiceItems(tvCategoryArray, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((TextView) v).setText(tvCategoryArray[i]);
                        if (i != 0)
                            selectedTvCategoryId = Integer.parseInt(Constants.tvCategoryList.get(i - 1).getLiveTvCategoryId());
                        else
                            selectedTvCategoryId = 0;
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });


        //setup country spinner
        final List<String> countryList = new ArrayList<>();
        if (Constants.countryList != null) {
            countryList.add(0, "All Countries");
            for (int i = 0; i < Constants.countryList.size(); i++) {
                countryList.add((i + 1), Constants.countryList.get(i).getName());
            }
        }

        final String[] countryArray = new String[(countryList.size())];
        for (int i = 0; i < countryList.size(); i++) {
            countryArray[i] = countryList.get(i);
        }
        countrySpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this);
                builder.setTitle("Select Country");
                builder.setSingleChoiceItems(countryArray, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((TextView) v).setText(countryArray[i]);
                        if (i != 0)
                            selectedCountryId = Integer.parseInt(Constants.countryList.get(i - 1).getCountryId());
                        else
                            selectedCountryId = 0;
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
        });

        range_tv = findViewById(R.id.rangeTV);
        rangeLayout = findViewById(R.id.range_picker_layout);
        range_seek_bar = findViewById(R.id.range_seek_bar);
        year_min = findViewById(R.id.year_min);
        year_max = findViewById(R.id.year_max);
        search_edit_text = findViewById(R.id.search_text);
        //set min year and max year
        range_seek_bar.setMaxValue(Float.parseFloat(getString(R.string.year_range_end)));
        range_seek_bar.setMinValue(Float.parseFloat(getString(R.string.year_range_start)));
        // set listener
        range_seek_bar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                year_min.setText(String.valueOf(minValue));
                year_max.setText(String.valueOf(maxValue));
            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void search() {
        String searchType = "";
        String search_text = search_edit_text.getText().toString();
        if (selectedType[0]) searchType+="movie";
        if (selectedType[1]) searchType+="tvseries";
        if (selectedType[2]) searchType += "live";
        int range_from =  Integer.parseInt(year_min.getText().toString());
        int range_to =  Integer.parseInt(year_max.getText().toString());

        if (TextUtils.isEmpty(search_text) && TextUtils.isEmpty(searchType)) {
            new ToastMsg(SearchActivity.this).toastIconError(getResources()
                    .getString(R.string.searcHError_message));
            return;
        }else if (!new NetworkInst(SearchActivity.this).isNetworkAvailable()) {
            new ToastMsg(SearchActivity.this).toastIconError(getResources()
                    .getString(R.string.no_internet));
            return;
        }

        Intent searchIntent = new Intent(SearchActivity.this, SearchResultActivity.class);
        searchIntent.putExtra("q", search_text);
        searchIntent.putExtra("type", searchType);
        searchIntent.putExtra("range_to", range_to);
        searchIntent.putExtra("range_from", range_from);
        searchIntent.putExtra("tv_category_id", selectedTvCategoryId);
        searchIntent.putExtra("genre_id", selectedGenreId);
        searchIntent.putExtra("country_id", selectedCountryId);
        startActivity(searchIntent);
    }

    public void btToggleClick(View view) {
        if (view instanceof Button) {
            Button b = (Button) view;
            if (b.isSelected()) {
                b.setTextColor(getResources().getColor(R.color.grey_40));
                if (b.getText().equals(getResources().getString(R.string.movie))) {
                    selectedType[0] = false;
                    if (!selectedType[1]){
                        rangeLayout.setVisibility(View.GONE);
                        range_tv.setVisibility(View.GONE);
                        genreLayout.setVisibility(View.GONE);
                        genreSpinner.setVisibility(View.GONE);
                        countryLayout.setVisibility(View.GONE);
                        countrySpinner.setVisibility(View.GONE);
                    }
                } else if (b.getText().equals(getResources().getString(R.string.tv_series))) {
                    selectedType[1] = false;
                    if (!selectedType[0] ){
                        rangeLayout.setVisibility(View.GONE);
                        range_tv.setVisibility(View.GONE);
                        genreLayout.setVisibility(View.GONE);
                        genreSpinner.setVisibility(View.GONE);
                        countryLayout.setVisibility(View.GONE);
                        countrySpinner.setVisibility(View.GONE);
                    }
                } else if (b.getText().equals(getResources().getString(R.string.live_tv))) {
                    selectedType[2] = false;
                    tvCategoryLayout.setVisibility(View.GONE);
                    tvCategorySpinner.setVisibility(View.GONE);
                }

            } else {
                b.setTextColor(getResources().getColor(R.color.white));
                if (b.getText().equals(getResources().getString(R.string.movie))) {
                    selectedType[0] = true;
                    rangeLayout.setVisibility(View.VISIBLE);
                    range_tv.setVisibility(View.VISIBLE);
                    genreLayout.setVisibility(View.VISIBLE);
                    genreSpinner.setVisibility(View.VISIBLE);
                    countryLayout.setVisibility(View.VISIBLE);
                    countrySpinner.setVisibility(View.VISIBLE);
                } else if (b.getText().equals(getResources().getString(R.string.tv_series))) {
                    selectedType[1] = true;
                    rangeLayout.setVisibility(View.VISIBLE);
                    range_tv.setVisibility(View.VISIBLE);
                    genreLayout.setVisibility(View.VISIBLE);
                    genreSpinner.setVisibility(View.VISIBLE);
                    countryLayout.setVisibility(View.VISIBLE);
                    countrySpinner.setVisibility(View.VISIBLE);
                } else if (b.getText().equals(getResources().getString(R.string.live_tv))) {
                    selectedType[2] = true;
                    tvCategoryLayout.setVisibility(View.VISIBLE);
                    tvCategorySpinner.setVisibility(View.VISIBLE);
                }
            }
            b.setSelected(!b.isSelected());
        }
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
