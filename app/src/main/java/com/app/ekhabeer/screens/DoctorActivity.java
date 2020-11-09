package com.app.ekhabeer.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ekhabeer.R;
import com.app.ekhabeer.adapters.CategoryAdapter;
import com.app.ekhabeer.adapters.DoctorAdapter;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.BaseActivity;
import com.app.ekhabeer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorActivity extends BaseActivity implements Controller.APICallbackListener {

    RecyclerView recyclerView_doc;
    DoctorAdapter doctorAdapter;
    ArrayList<String> list;
    String type,intent_sub_cat_id;
    ImageView iv_back;
    TextView tv_toolbar_title;
    Controller mController;
    HashMap<String, String> hashMapMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        initView();
    }

    public void initView(){
        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();
        list = new ArrayList<>();

        recyclerView_doc = (RecyclerView) findViewById(R.id.recyclerView_docs);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        intent_sub_cat_id = intent.getStringExtra("sub_cat_id");
        tv_toolbar_title.setText(type);

//        PutData();
        GetSubCategories();

//        doctorAdapter = new DoctorAdapter(type,list,this);
        recyclerView_doc.setHasFixedSize(true);
        recyclerView_doc.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView_doc.setAdapter(doctorAdapter);
    }

    public void GetSubCategories(){

        hashMapMain.put(Constants.GET_CONSULT_LIST , "");
        hashMapMain.put(Constants.SUB_CAT_ID , intent_sub_cat_id);
        Log.e("checkDocFlow",">>" + hashMapMain);
        mController.GetDocList(hashMapMain,DoctorActivity.this);

    }

    @Override
    public void onFetchStart() {

        showLoader(DoctorActivity.this);
    }

    @Override
    public void onFetchProgress(JSONObject pojo) {

    }

    @Override
    public void onFetchProgress(ArrayList<HashMap<String, String>> pojoList, JSONArray jsonArrays) {

        doctorAdapter = new DoctorAdapter(type,pojoList,this);
        recyclerView_doc.setAdapter(doctorAdapter);
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