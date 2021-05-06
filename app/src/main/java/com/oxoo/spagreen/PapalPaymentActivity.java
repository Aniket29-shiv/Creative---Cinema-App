package com.oxoo.spagreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PapalPaymentActivity extends AppCompatActivity {

    private String plantId;
    private String userId;
    private String payId;

    private CoordinatorLayout coordinatorLayout;
    private Button finishBt;
    private TextView statusTv, noItemTv;
    private RelativeLayout  mainLayout;
    private ProgressBar progressLayout;
    private String amount;
    private Toolbar mToolbar;
    private boolean isDark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("push", MODE_PRIVATE);
        isDark = sharedPreferences.getBoolean("dark", false);

        if (isDark) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }
        setContentView(R.layout.activity_papal_payment);
        Intent intent = getIntent();
        String state = intent.getStringExtra("state");
        initView();
        if (isDark) {
            mToolbar.setBackgroundColor(getResources().getColor(R.color.black_window_light));
            finishBt.setBackground(getResources().getDrawable(R.drawable.btn_rounded_dark));
        }

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Finishing Payment");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        statusTv.setText(state);
    }

    private void initView() {
        statusTv = findViewById(R.id.status_tv);
        finishBt = findViewById(R.id.finish_paymentBt);
        mainLayout = findViewById(R.id.main_layout);
        statusTv = findViewById(R.id.status_tv);
        mToolbar = findViewById(R.id.toolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();

        finishBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }


}
