package com.oxoo.spagreen;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.oxoo.spagreen.database.DatabaseHelper;
import com.oxoo.spagreen.network.RetrofitClient;
import com.oxoo.spagreen.network.apis.PaymentApi;
import com.oxoo.spagreen.network.apis.SubscriptionApi;
import com.oxoo.spagreen.network.model.ActiveStatus;
import com.oxoo.spagreen.network.model.Package;
import com.oxoo.spagreen.network.model.config.PaymentConfig;
import com.oxoo.spagreen.network.model.User;
import com.oxoo.spagreen.utils.ApiResources;
import com.oxoo.spagreen.utils.PreferenceUtils;
import com.oxoo.spagreen.utils.ToastMsg;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RazorPayActivity extends AppCompatActivity implements PaymentResultListener{
    private static final String TAG = "RazorPayActivity";
    private Package aPackage;
    private DatabaseHelper databaseHelper;
    private ProgressBar progressBar;
    private String amountPaidInRupee = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razor_pay);
        progressBar = findViewById(R.id.progress_bar);
        aPackage = (Package) getIntent().getSerializableExtra("package");
        databaseHelper = new DatabaseHelper(this);
        //Checkout.preload(getApplicationContext());
        startPayment();
    }

    public void startPayment() {
        PaymentConfig config = databaseHelper.getConfigurationData().getPaymentConfig();
        User user = databaseHelper.getUserData();

        final Activity activity = this;
        Checkout checkout = new Checkout();
        checkout.setKeyID(config.getRazorpayKeyId());
        checkout.setImage(R.drawable.logo);


        JSONObject options = new JSONObject();
        try {
            options.put("name",  getString(R.string.app_name));
            options.put("description", aPackage.getName());
            //options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("currency", config.getCurrency());
            options.put("currency", "INR");
            options.put("amount", currencyConvert(config.getCurrency(), aPackage.getPrice(), ApiResources.RAZORPAY_EXCHANGE_RATE));

            JSONObject prefill = new JSONObject();
            prefill.put("email", user.getEmail());
            //prefill.put("contact", "0251015");
            options.put("prefill", prefill);

            checkout.open(activity, options);

            Log.e(TAG, config.getCurrency());
            Log.e(TAG, currencyConvert(config.getCurrency(), aPackage.getPrice(), ApiResources.RAZORPAY_EXCHANGE_RATE));
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        saveChargeData(s);
    }

    @Override
    public void onPaymentError(int i, String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        Log.e(TAG, "Error: " + message);
        finish();
    }

    public void saveChargeData(String token) {
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        PaymentApi paymentApi = retrofit.create(PaymentApi.class);
        Call<ResponseBody> call = paymentApi.savePayment(AppConfig.API_KEY, aPackage.getPlanId(),
                databaseHelper.getUserData().getUserId(),
                amountPaidInRupee,
                token, "RazorPay");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    updateActiveStatus();

                } else {
                    new ToastMsg(RazorPayActivity.this).toastIconError(getString(R.string.something_went_wrong));
                    finish();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                new ToastMsg(RazorPayActivity.this).toastIconError(getString(R.string.something_went_wrong));
                finish();
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    private void updateActiveStatus() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        SubscriptionApi subscriptionApi = retrofit.create(SubscriptionApi.class);

        Call<ActiveStatus> call = subscriptionApi.getActiveStatus(AppConfig.API_KEY, PreferenceUtils.getUserId(RazorPayActivity.this));
        call.enqueue(new Callback<ActiveStatus>() {
            @Override
            public void onResponse(Call<ActiveStatus> call, Response<ActiveStatus> response) {
                if (response.code() == 200) {
                    ActiveStatus activeStatus = response.body();
                    DatabaseHelper db = new DatabaseHelper(getApplicationContext());
                    db.deleteAllActiveStatusData();
                    db.insertActiveStatusData(activeStatus);
                    new ToastMsg(RazorPayActivity.this).toastIconSuccess(getResources().getString(R.string.payment_success));
                    finish();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ActiveStatus> call, Throwable t) {
                t.printStackTrace();
                new ToastMsg(RazorPayActivity.this).toastIconError(getString(R.string.something_went_wrong));
                finish();
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private String currencyConvert(String currency, String value, String exchangeRate) {
        //convert currency to rupee
        String convertedValue = "";
        if (!currency.equalsIgnoreCase("INR")) {
            double temp = Double.parseDouble(value) * Double.parseDouble(exchangeRate);
            convertedValue = String.valueOf(temp * 100); //convert to rupee
        } else {
            double temp = Double.parseDouble(value);
            convertedValue = String.valueOf(temp * 100);
        }
        amountPaidInRupee = convertedValue;
        return convertedValue;
    }

}
