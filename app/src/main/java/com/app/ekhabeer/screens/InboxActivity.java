package com.app.ekhabeer.screens;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ekhabeer.R;
import com.app.ekhabeer.adapters.DoctorAdapter;
import com.app.ekhabeer.adapters.InboxAdapter;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.BaseActivity;
import com.app.ekhabeer.utils.Constants;
import com.app.ekhabeer.utils.Model;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InboxActivity extends BaseActivity implements Controller.APICallbackListener {

    RecyclerView recycler_view_inbox;
    Controller mController;
    HashMap<String, String> hashMapMain;
    InboxAdapter inboxAdapter;
    String user_type,user_id;
    ImageView iv_back;
    TextView tv_toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        initView();
    }

    public void initView(){

        SharedPreferences prefs = this.getSharedPreferences(
                "Ekhabeer", Context.MODE_PRIVATE);
        user_type = prefs.getString("user_type","");
        user_id = prefs.getString("user_id","");
        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();
        recycler_view_inbox = (RecyclerView) findViewById(R.id.recycler_view_inbox);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        if (user_type.equals("user")){
            GetDataForUser();
        }else{
            GetDataForConsul();
        }
        tv_toolbar_title.setText(R.string.menu_inbox);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//        GetInbox();
        recycler_view_inbox.setHasFixedSize(true);
        recycler_view_inbox.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view_inbox.setAdapter(inboxAdapter);
    }

    public void GetDataForUser(){
        hashMapMain.put(Constants.GET_MESSAGES_USERS , "");
        hashMapMain.put(Constants.USER_ID , user_id);
//        hashMapMain.put(Constants.CONSULTANT_ID , "2");
        Log.e("checkInboxFlow",">>" + hashMapMain);
        mController.GetListForUser(hashMapMain,InboxActivity.this);
    }

    public void GetDataForConsul(){
        hashMapMain.put(Constants.GET_MESSAGES_CONSUL , "");
        hashMapMain.put(Constants.CONSULTANT_ID , user_id);
        Log.e("checkInboxFlow",">>" + hashMapMain);
        mController.GetListForConsul(hashMapMain,InboxActivity.this);
    }

//    public void GetInbox(){
//        SharedPreferences prefs = this.getSharedPreferences(
//                "Ekhabeer", Context.MODE_PRIVATE);
//        hashMapMain.put(Constants.GET_MESSAGES_LIST , "");
//        hashMapMain.put(Constants.USER_ID , "15");
//        hashMapMain.put(Constants.CONSULTANT_ID , "2");
//        Log.e("checkInboxFlow",">>" + hashMapMain);
//        mController.GetInboxList(hashMapMain,InboxActivity.this);
//
//    }

    @Override
    public void onFetchStart() {

        showLoader(this);
    }

    @Override
    public void onFetchProgress(JSONObject pojo) {

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onFetchProgress(ArrayList<HashMap<String, String>> pojoList, JSONArray jsonArrays) {
        Log.e("checkInboxList",">>" + pojoList);

        inboxAdapter = new InboxAdapter(jsonArrays,pojoList,this);
        recycler_view_inbox.setAdapter(inboxAdapter);

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