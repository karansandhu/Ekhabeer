package com.app.ekhabeer.screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ekhabeer.R;
import com.app.ekhabeer.adapters.GridViewAdapter;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.Config;
import com.app.ekhabeer.utils.Constants;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.philliphsu.bottomsheetpickers.time.BottomSheetTimePickerDialog;
import com.philliphsu.bottomsheetpickers.time.grid.GridTimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity implements BottomSheetTimePickerDialog.OnTimeSetListener, Controller.APICallbackListener {

    GridView gridView;
    Serializable intent_list;
    Controller mController;
    HashMap<String, String> hashMapMain;
    ArrayList<String> list;
    String final_date,amount;
    String three_mins,six_mins,one_month,three_month;
    ImageView iv_back;
    SharedPreferences prefs;
    TextView tv_toolbar_title,tv_date,tv_time,tv_30_min,tv_60_min,tv_1_month,tv_3_month,tv_final_amount;
    RelativeLayout rl_date,rl_time,rl_pay_appointment,rl_package_30,rl_package_60,rl_package_1,rl_package_3;


    private static final int PAYPAL_REQUEST_CODE = 7777;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);
    String payment_amount = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        initView();
    }

    public void initView(){

        prefs = this.getSharedPreferences(
                "Ekhabeer", Context.MODE_PRIVATE);
        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();

        list = new ArrayList<>();
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_final_amount = (TextView) findViewById(R.id.tv_final_amount);
        tv_30_min = (TextView) findViewById(R.id.tv_30_min);
        tv_60_min = (TextView) findViewById(R.id.tv_60_min);
        tv_1_month = (TextView) findViewById(R.id.tv_1_month);
        tv_3_month = (TextView) findViewById(R.id.tv_3_month);
        rl_package_30 = (RelativeLayout) findViewById(R.id.rl_package_30);
        rl_package_60 = (RelativeLayout) findViewById(R.id.rl_package_60);
        rl_package_1 = (RelativeLayout) findViewById(R.id.rl_package_1);
        rl_package_3 = (RelativeLayout) findViewById(R.id.rl_package_3);
        rl_date = (RelativeLayout) findViewById(R.id.rl_date);
        rl_time = (RelativeLayout) findViewById(R.id.rl_time);
        rl_pay_appointment = (RelativeLayout) findViewById(R.id.rl_pay_appointment);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_toolbar_title.setText(R.string.booking_act);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        intent_list = getIntent().getSerializableExtra("item_data");
        three_mins = getIntent().getStringExtra("three_mins");
        six_mins = getIntent().getStringExtra("six_mins");
        one_month = getIntent().getStringExtra("one_month");
        three_month = getIntent().getStringExtra("three_month");
        Log.e("checkIntentList",">" + intent_list);
//        PutData();
        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setColumnWidth(60);
        gridView.setNumColumns(3);
        GridViewAdapter gridViewAdapter = new GridViewAdapter(BookingActivity.this,list);
        gridView.setAdapter(gridViewAdapter);
        gridViewAdapter.notifyDataSetChanged();

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String month = myCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

                tv_date.setText(month + " " + dayOfMonth + "," + " " + year);
//                final_date =
                Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                calendar.getTimeInMillis();
                monthOfYear = monthOfYear + 1;
                String monthOfYearwithzero = String.valueOf(monthOfYear);
                String dayOfTheMonthwithzero = String.valueOf(dayOfMonth);
                if (String.valueOf(monthOfYear).length() == 1) {
                    monthOfYearwithzero = "0" + monthOfYear;
                }

                if(String.valueOf(dayOfMonth).length() == 1)
                {
                    dayOfTheMonthwithzero = "0" + dayOfMonth;
                }
                final_date = year + "-" + monthOfYearwithzero + "-" + dayOfTheMonthwithzero;
                Log.e("checkBookingAct", "onDateSet: " + final_date);

            }
        };

        rl_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog(BookingActivity.this, date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        rl_pay_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookingActivity.this,AppPaymentActivity.class);
                intent.putExtra("amount",amount);
                startActivity(intent);
            }
        });

        rl_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                GridTimePickerDialog grid = new GridTimePickerDialog.Builder(
                        BookingActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        DateFormat.is24HourFormat(BookingActivity.this))
                        /* ... Set additional options ... */
                        .build();
                grid.show(getSupportFragmentManager(), "TAG");
            }
        });

        rl_package_30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_30_min.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_60_min.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_1_month.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_3_month.setTextColor(getResources().getColor(R.color.colorBlack));
                rl_package_30.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                rl_package_60.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                rl_package_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                rl_package_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                amount = three_mins;
                tv_final_amount.setText(amount);
            }
        });

        rl_package_60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_30_min.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_60_min.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_1_month.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_3_month.setTextColor(getResources().getColor(R.color.colorBlack));
                rl_package_30.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                rl_package_60.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                rl_package_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                rl_package_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                amount = six_mins;
                tv_final_amount.setText(amount);
            }
        });

        rl_package_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_30_min.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_60_min.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_1_month.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_3_month.setTextColor(getResources().getColor(R.color.colorBlack));
                rl_package_30.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                rl_package_60.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                rl_package_1.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                rl_package_3.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                amount = one_month;
                tv_final_amount.setText(amount);
            }
        });

        rl_package_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_30_min.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_60_min.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_1_month.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_3_month.setTextColor(getResources().getColor(R.color.colorWhite));
                rl_package_30.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                rl_package_60.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                rl_package_1.setBackgroundDrawable(getResources().getDrawable(R.drawable.rl_grey_bg));
                rl_package_3.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                amount = three_month;
                tv_final_amount.setText(amount);
            }
        });

        //start paypal service
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(intent);

        rl_pay_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                processPayment();
                BookAppointment();
            }
        });
    }
    public void BookAppointment(){
        hashMapMain.put(Constants.BOOK_APPOINTMENT , "");
        hashMapMain.put(Constants.USER_ID , prefs.getString("user_id",""));
        hashMapMain.put(Constants.CONSULTANT_ID , "20");
        hashMapMain.put(Constants.APPOINT_ID , "");
        Log.e("checkInboxFlow",">>" + hashMapMain);
        mController.BookApointment(hashMapMain,BookingActivity.this);
    }


    private void processPayment() {
        payment_amount = tv_final_amount.getText().toString();
        if (!payment_amount.equals("")) {
            PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(payment_amount)), "USD",
                    "Purchase Goods", PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
            startActivityForResult(intent, PAYPAL_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, PaymentDetailsActivity.class)
                                .putExtra("Payment Details", paymentDetails)
                                .putExtra("Amount", payment_amount));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED)
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID)
            Toast.makeText(this, "Invalid", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


    @Override
    public void onTimeSet(ViewGroup viewGroup, int i, int i1) {
        Calendar cal = new java.util.GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, i);
        cal.set(Calendar.MINUTE, i1);
        tv_time.setText(DateFormat.getTimeFormat(this).format(cal.getTime()));
        Log.e("checkBookingAct", "onTimeSet: "+cal.getTime());
    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(JSONObject pojo) {

    }

    @Override
    public void onFetchProgress(ArrayList<HashMap<String, String>> pojoList, JSONArray jsonArrays) {

    }

    @Override
    public void onFetchComplete() {

    }

    @Override
    public void onFetchFailed() {

    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data) {

    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data, boolean isTimeMatched) {

    }
}