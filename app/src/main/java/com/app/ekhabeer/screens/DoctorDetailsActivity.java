package com.app.ekhabeer.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.ekhabeer.R;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.BaseActivity;
import com.app.ekhabeer.utils.Constants;
import com.app.ekhabeer.utils.Model;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoctorDetailsActivity extends BaseActivity implements Controller.APICallbackListener {

    RelativeLayout rl_book_appointment;
    String type,consultant_id;
    LinearLayout ll_info_one,ll_info_two;
    TextView tv_toolbar_title,tv_doc_title,tv_doc_spec,tv_doc_address,tv_doc_bio,tv_doc_timings,tv_doc_charges,tv_doc_price_one,
    tv_doc_price_three,tv_doc_price_two,tv_doc_price_four;
    Controller mController;
    HashMap<String, String> hashMapMain;
    JSONArray timeList;
    ArrayList<Model> data;
    String three_mins,six_mins,one_month,three_month;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        initView();
    }

    public void initView(){
        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();
        data=new ArrayList<>();
//        timeList = new ArrayList<>();

        rl_book_appointment = (RelativeLayout) findViewById(R.id.rl_book_appointment);
        ll_info_one = (LinearLayout) findViewById(R.id.ll_info_one);
        ll_info_two = (LinearLayout) findViewById(R.id.ll_info_two);
        tv_doc_timings = (TextView) findViewById(R.id.tv_doc_timings);
        tv_doc_charges = (TextView) findViewById(R.id.tv_doc_charges);
        tv_doc_price_one = (TextView) findViewById(R.id.tv_doc_price_one);
        tv_doc_price_three = (TextView) findViewById(R.id.tv_doc_price_three);
        tv_doc_price_two = (TextView) findViewById(R.id.tv_doc_price_two);
        tv_doc_price_four = (TextView) findViewById(R.id.tv_doc_price_four);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        tv_doc_title = (TextView) findViewById(R.id.tv_doc_title);
        tv_doc_spec = (TextView) findViewById(R.id.tv_doc_spec);
        tv_doc_address = (TextView) findViewById(R.id.tv_doc_address);
        tv_doc_bio = (TextView) findViewById(R.id.tv_doc_bio);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_toolbar_title.setText(R.string.doc_details);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        consultant_id = intent.getStringExtra("consultant_id");
        GetDocInfo();
        switch(type){

            case "Medical":
                ll_info_one.setBackgroundColor(getResources().getColor(R.color.colorHomeOptionOne));
                ll_info_two.setBackgroundColor(getResources().getColor(R.color.colorHomeOptionOne));
                break;
            case "Psycology":
                ll_info_one.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                ll_info_two.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                break;
            case "Diet and Fitness":
                ll_info_one.setBackgroundColor(getResources().getColor(R.color.colorHomeOptionThree));
                ll_info_two.setBackgroundColor(getResources().getColor(R.color.colorHomeOptionThree));
                break;
            case "Family and Kids":
                ll_info_one.setBackgroundColor(getResources().getColor(R.color.colorHomeOptionFour));
                ll_info_two.setBackgroundColor(getResources().getColor(R.color.colorHomeOptionFour));
                break;
            case "Bussiness and investements":
                ll_info_one.setBackgroundColor(getResources().getColor(R.color.colorHomeOptionFive));
                ll_info_two.setBackgroundColor(getResources().getColor(R.color.colorHomeOptionFive));
                break;
            case "Law & Legislation":
                ll_info_one.setBackgroundColor(getResources().getColor(R.color.colorHomeOptionSix));
                ll_info_two.setBackgroundColor(getResources().getColor(R.color.colorHomeOptionSix));
                break;
        }

        rl_book_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(DoctorDetailsActivity.this,AppPaymentActivity.class);
                Intent intent = new Intent(DoctorDetailsActivity.this,BookingActivity.class);
                intent.putExtra("three_mins",three_mins);
                intent.putExtra("six_mins",six_mins);
                intent.putExtra("one_month",one_month);
                intent.putExtra("three_month",three_month);
                startActivity(intent);
            }
        });
    }

    public void GetDocInfo(){

        hashMapMain.put(Constants.GET_CONSULT_LIST_TIME , "");
        hashMapMain.put(Constants.CONSULTANT_ID , consultant_id);
        Log.e("checkDocFlow",">>" + hashMapMain);
        mController.GetDocInfo(hashMapMain,DoctorDetailsActivity.this);

    }

    @Override
    public void onFetchStart() {

        showLoader(DoctorDetailsActivity.this);
    }

    @Override
    public void onFetchProgress(JSONObject pojo) {
        Log.e("checkFinalDoc","onFetchProgress>>" + pojo);

    }

    @Override
    public void onFetchProgress(ArrayList<HashMap<String, String>> pojoList, JSONArray jsonArrays) {
        try {
            Model model = new Model();
//            Log.e("checkFinalDoc","onFetchProgress>2>" + pojoList.get(0).get("item_data"));
            Log.e("checkDocFlow", "main>onFetchProgress>1>: " + pojoList);
            Log.e("checkDocFlow", "main>onFetchProgress>2>: " + jsonArrays);
//            ArrayList<String> list = new ArrayList<String>();
//            JSONArray jsonArray = jsonArrays;
//            if (jsonArray != null) {
//                int len = jsonArray.length();
//                for (int i=0;i<len;i++){
//                    list.add(jsonArray.get(i).toString());
//                }
//            }

            JSONArray jArray = null;
            try {
                jArray = jsonArrays;
                // Extract data from json and store into ArrayList as class objects
                for(int i=0;i<jArray.length();i++){
                    JSONObject json_data = jArray.getJSONObject(i);
                    model = new Model();
                    model.setDays(json_data.getString(Constants.DAYS));
                    model.setFromtime(json_data.getString(Constants.FROM_TIME));
                    model.setTotime(json_data.getString(Constants.TO_TIME));
                    model.setFee(json_data.getString(Constants.FEE));
                    data.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("checkDocFlow", "main>onFetchProgress>3>: " + data);

            tv_doc_timings.setText(model.getFromtime() + " - " + model.getTotime());
            tv_doc_charges.setText("$" + pojoList.get(0).get(Constants.CONSULTATION));
            tv_doc_price_one.setText("$" + pojoList.get(0).get(Constants.THIRTY_EST_COST));
            tv_doc_price_three.setText("$" + pojoList.get(0).get(Constants.SIXTY_EST_COST));
            tv_doc_price_two.setText("$" + pojoList.get(0).get(Constants.ONE_EST_COST));
            tv_doc_price_four.setText("$" + pojoList.get(0).get(Constants.THREE_EST_COST));
            tv_doc_address.setText(pojoList.get(0).get(Constants.ADDRESS));
            tv_doc_bio.setText(pojoList.get(0).get(Constants.BIO));
            tv_doc_spec.setText(pojoList.get(0).get(Constants.SPECIAL));
            tv_doc_title.setText(pojoList.get(0).get(Constants.FNAME));
            three_mins = pojoList.get(0).get(Constants.THIRTY_EST_COST);
            six_mins = pojoList.get(0).get(Constants.SIXTY_EST_COST);
            one_month = pojoList.get(0).get(Constants.ONE_EST_COST);
            three_month = pojoList.get(0).get(Constants.THREE_EST_COST);
//            JSONArray ttimeList = model.getDataArray();
//            JSONArray ttimeList = (JSONArray) pojoList.get(0).get("item_dataa");
//            Log.e("checkFinalDoc","onFetchProgress>2>" + ttimeList);
        }catch (Exception e){
            e.printStackTrace();
        }
//        for (int i = 0;i<pojoList.get(0).get(Constants.ITEM_DATA).length();i++){
//            timeList.add(pojoList.get(0))
//        }
    }

    @Override
    public void onFetchComplete() {

        dismissLoader();
    }

    @Override
    public void onFetchFailed() {

        dismissLoader();
    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data) {

    }

    @Override
    public void onSuccessORError(String status, String message, JSONObject data, boolean isTimeMatched) {

    }
}