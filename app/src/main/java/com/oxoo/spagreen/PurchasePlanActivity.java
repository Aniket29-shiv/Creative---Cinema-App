package com.oxoo.spagreen;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.oxoo.spagreen.adapters.PackageAdapter;
import com.oxoo.spagreen.bottomshit.PaymentBottomShitDialog;
import com.oxoo.spagreen.database.DatabaseHelper;
import com.oxoo.spagreen.network.RetrofitClient;
import com.oxoo.spagreen.network.apis.PackageApi;
import com.oxoo.spagreen.network.apis.PaymentApi;
import com.oxoo.spagreen.network.apis.SubscriptionApi;
import com.oxoo.spagreen.network.model.ActiveStatus;
import com.oxoo.spagreen.network.model.AllPackage;
import com.oxoo.spagreen.network.model.Package;
import com.oxoo.spagreen.network.model.config.PaymentConfig;
import com.oxoo.spagreen.utils.PreferenceUtils;
import com.oxoo.spagreen.utils.ApiResources;
import com.oxoo.spagreen.utils.RtlUtils;
import com.oxoo.spagreen.utils.ToastMsg;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PurchasePlanActivity extends AppCompatActivity implements PackageAdapter.OnItemClickListener, PaymentBottomShitDialog.OnBottomShitClickListener {

    private static final String TAG = PurchasePlanActivity.class.getSimpleName();
    private static final int PAYPAL_REQUEST_CODE = 100;
    private TextView noTv;
    private ProgressBar progressBar;
    private ImageView closeIv;
    private RecyclerView packageRv;
    private List<Package> packages = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();
    private String currency = "";
    private String exchangeRate;
    private boolean isDark;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(ApiResources.PAYPAL_CLIENT_ID);
    private Package packageItem;
    private PaymentBottomShitDialog paymentBottomShitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RtlUtils.setScreenDirection(this);
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPreferences = getSharedPreferences("push", MODE_PRIVATE);
        isDark = sharedPreferences.getBoolean("dark", false);

        if (isDark) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }

        setContentView(R.layout.activity_purchase_plan);

        initView();

        // ---------- start paypal service ----------
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        // getting currency symbol
        PaymentConfig config = new DatabaseHelper(PurchasePlanActivity.this).getConfigurationData().getPaymentConfig();
        currency = config.getCurrencySymbol();
        exchangeRate = config.getExchangeRate();
        packageRv.setHasFixedSize(true);
        packageRv.setLayoutManager(new LinearLayoutManager(this));

        getPurchasePlanInfo();
    }

    private void getPurchasePlanInfo() {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        final PackageApi packageApi = retrofit.create(PackageApi.class);
        Call<AllPackage> call = packageApi.getAllPackage(AppConfig.API_KEY);
        call.enqueue(new Callback<AllPackage>() {
            @Override
            public void onResponse(Call<AllPackage> call, Response<AllPackage> response) {
                AllPackage allPackage = response.body();
                packages = allPackage.getPackage();
                if (allPackage.getPackage().size() > 0) {
                    noTv.setVisibility(View.GONE);
                    PackageAdapter adapter = new PackageAdapter(PurchasePlanActivity.this, allPackage.getPackage(), currency);
                    adapter.setItemClickListener(PurchasePlanActivity.this);
                    packageRv.setAdapter(adapter);
                } else {
                    noTv.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AllPackage> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        completePayment(paymentDetails);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    new ToastMsg(this).toastIconError("Cancel");
                }
            }

        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            new ToastMsg(this).toastIconError("Invalid");
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void completePayment(String paymentDetails) {
        try {
            JSONObject jsonObject = new JSONObject(paymentDetails);
            sendDataToServer(jsonObject.getJSONObject("response"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendDataToServer(JSONObject response) {
        try {
            String payId = response.getString("id");
            final String state = response.getString("state");
            final String userId = PreferenceUtils.getUserId(PurchasePlanActivity.this);

            Retrofit retrofit = RetrofitClient.getRetrofitInstance();
            PaymentApi paymentApi = retrofit.create(PaymentApi.class);
            Call<ResponseBody> call = paymentApi.savePayment(AppConfig.API_KEY, packageItem.getPlanId(), userId, packageItem.getPrice(),
                    payId, "Paypal");

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {

                        updateActiveStatus(userId);

                    } else {
                        new ToastMsg(PurchasePlanActivity.this).toastIconError(getString(R.string.something_went_wrong));
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    new ToastMsg(PurchasePlanActivity.this).toastIconError(getString(R.string.something_went_wrong));
                    t.printStackTrace();
                    Log.e("PAYMENT", "error: " + t.getLocalizedMessage());
                }

            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void updateActiveStatus(String userId) {
        Retrofit retrofit = RetrofitClient.getRetrofitInstance();
        SubscriptionApi subscriptionApi = retrofit.create(SubscriptionApi.class);
        Call<ActiveStatus> call = subscriptionApi.getActiveStatus(AppConfig.API_KEY, userId);
        call.enqueue(new Callback<ActiveStatus>() {
            @Override
            public void onResponse(Call<ActiveStatus> call, Response<ActiveStatus> response) {
                if (response.code() == 200) {
                    ActiveStatus activiStatus = response.body();
                    saveActiveStatus(activiStatus);
                } else {
                    new ToastMsg(PurchasePlanActivity.this).toastIconError("Payment info not save to the own server. something went wrong.");
                }
            }

            @Override
            public void onFailure(Call<ActiveStatus> call, Throwable t) {
                new ToastMsg(PurchasePlanActivity.this).toastIconError(t.getMessage());
                t.printStackTrace();
            }
        });

    }

    private void saveActiveStatus(ActiveStatus activeStatus) {
        DatabaseHelper db = new DatabaseHelper(PurchasePlanActivity.this);
        if (db.getActiveStatusCount() > 1) {
            db.deleteAllActiveStatusData();
        }
        if (db.getActiveStatusCount() == 0) {
            db.insertActiveStatusData(activeStatus);
        } else {
            db.updateActiveStatus(activeStatus, 1);
        }
        new ToastMsg(PurchasePlanActivity.this).toastIconSuccess(getResources().getString(R.string.payment_success));

        /*Intent intent = new Intent(PurchasePlanActivity.this, PapalPaymentActivity.class);
        intent.putExtra("state", state);
        intent.putExtra("amount", packageItem.getPrice());
        startActivity(intent);
*/

        finish();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void processPaypalPayment(Package packageItem) {
        String[] paypalAcceptedList = getResources().getStringArray(R.array.paypal_currency_list);
        if (Arrays.asList(paypalAcceptedList).contains(ApiResources.CURRENCY)) {
            PayPalPayment payPalPayment = new PayPalPayment((new BigDecimal(String.valueOf(packageItem.getPrice()))),
                    ApiResources.CURRENCY,
                    "Payment for Package",
                    PayPalPayment.PAYMENT_INTENT_SALE);

            Log.e("Payment", "currency: " + ApiResources.CURRENCY);
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
            startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        } else {
            PaymentConfig paymentConfig = new DatabaseHelper(PurchasePlanActivity.this).getConfigurationData().getPaymentConfig();
            double exchangeRate = Double.parseDouble(paymentConfig.getExchangeRate());
            double price = Double.parseDouble(packageItem.getPrice());
            double priceInUSD = (double) price / exchangeRate;
            PayPalPayment payPalPayment = new PayPalPayment((new BigDecimal(String.valueOf(priceInUSD))),
                    "USD",
                    "Payment for Package",
                    PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
            startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        }
    }

    private void initView() {

        noTv = findViewById(R.id.no_tv);
        progressBar = findViewById(R.id.progress_bar);
        packageRv = findViewById(R.id.pacakge_rv);
        closeIv = findViewById(R.id.close_iv);
    }

    @Override
    public void onItemClick(Package pac) {
        packageItem = pac;

        paymentBottomShitDialog = new PaymentBottomShitDialog();
        paymentBottomShitDialog.show(getSupportFragmentManager(), "PaymentBottomShitDialog");
    }

    @Override
    public void onBottomShitClick(String paymentMethodName) {
        if (paymentMethodName.equals(PaymentBottomShitDialog.PAYPAL)) {
            processPaypalPayment(packageItem);

        } else if (paymentMethodName.equals(PaymentBottomShitDialog.STRIP)) {
            Intent intent = new Intent(PurchasePlanActivity.this, StripePaymentActivity.class);
            intent.putExtra("package", packageItem);
            intent.putExtra("currency", currency);
            startActivity(intent);

        } else if (paymentMethodName.equalsIgnoreCase(PaymentBottomShitDialog.RAZOR_PAY)) {
            Intent intent = new Intent(PurchasePlanActivity.this, RazorPayActivity.class);
            intent.putExtra("package", packageItem);
            intent.putExtra("currency", currency);
            startActivity(intent);
        } else if (paymentMethodName.equalsIgnoreCase(PaymentBottomShitDialog.OFFLINE_PAY)) {
            //show an alert dialog
            showOfflinePaymentDialog();
        }
    }

    private void showOfflinePaymentDialog() {
        DatabaseHelper helper = new DatabaseHelper(this);
        PaymentConfig paymentConfig = helper.getConfigurationData().getPaymentConfig();
        new MaterialAlertDialogBuilder(this)
                .setTitle(paymentConfig.getOfflinePaymentTitle())
                .setMessage(paymentConfig.getOfflinePaymentInstruction())
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }


}

