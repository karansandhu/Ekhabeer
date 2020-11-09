package com.app.ekhabeer.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ekhabeer.R;
import com.app.ekhabeer.adapters.ConsultationsAdapter;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ConsultationsActivity extends AppCompatActivity implements  Controller.APICallbackListener{

    TextView tv_consul_complete,tv_consul_cancel,tv_consul_sech;
    RecyclerView recycler_view_consults;
    Controller mController;
    HashMap<String, String> hashMapMain;
    ImageView iv_back;
    TextView tv_toolbar_title;
    SharedPreferences prefs;
    ConsultationsAdapter consultationsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultations);

        initView();
    }

    public void initView(){
        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();
        prefs = this.getSharedPreferences(
                "Ekhabeer", Context.MODE_PRIVATE);

        tv_consul_complete = (TextView) findViewById(R.id.tv_consul_complete);
        tv_consul_cancel = (TextView) findViewById(R.id.tv_consul_cancel);
        tv_consul_sech = (TextView) findViewById(R.id.tv_consul_sech);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        recycler_view_consults = (RecyclerView) findViewById(R.id.recycler_view_consults);
        recycler_view_consults.setHasFixedSize(true);
        recycler_view_consults.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view_consults.setAdapter(consultationsAdapter);
        tv_toolbar_title.setText(R.string.consultations);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        GetConsultationsAPI("scheduled");
        tv_consul_sech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_consul_sech.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_consul_cancel.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_consul_complete.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_consul_sech.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                tv_consul_cancel.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_consul_complete.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                GetConsultationsAPI("scheduled");
            }
        });
        tv_consul_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_consul_sech.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_consul_cancel.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_consul_complete.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_consul_sech.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_consul_cancel.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                tv_consul_complete.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                GetConsultationsAPI("cancelled");

            }
        });
        tv_consul_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_consul_sech.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_consul_cancel.setTextColor(getResources().getColor(R.color.colorWhite));
                tv_consul_complete.setTextColor(getResources().getColor(R.color.colorBlack));
                tv_consul_sech.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_consul_cancel.setBackgroundColor(getResources().getColor(R.color.colorRedText));
                tv_consul_complete.setBackgroundColor(getResources().getColor(R.color.colorGreenPsy));
                GetConsultationsAPI("completed");

            }
        });
    }

    public void GetConsultationsAPI(String status_type){

        hashMapMain.put(Constants.APPOINTMENT_LIST_USER , "");
        hashMapMain.put(Constants.STATUS_TYPE , status_type);
//        hashMapMain.put(Constants.USER_ID , prefs.getString("user_id",""));
        hashMapMain.put(Constants.USER_ID , "1");
        Log.e("checkUserProfileAPI",">>" + hashMapMain);
        mController.GetConsultaionsForUsers(hashMapMain,ConsultationsActivity.this);

    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(JSONObject pojo) {

    }

    @Override
    public void onFetchProgress(ArrayList<HashMap<String, String>> pojoList, JSONArray jsonArrays) {
        Log.e("checkFninal","1>>" + pojoList);
        if (Constants.model.getLastApiHit().equals(Constants.APPOINTMENT_LIST_USER)){
            Log.e("checkFninal","2>>" + pojoList);

            consultationsAdapter = new ConsultationsAdapter(pojoList,this);
            recycler_view_consults.setAdapter(consultationsAdapter);
        }
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