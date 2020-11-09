package com.app.ekhabeer.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ekhabeer.R;
import com.app.ekhabeer.adapters.CategoryAdapter;
import com.app.ekhabeer.restClient.Controller;
import com.app.ekhabeer.utils.BaseActivity;
import com.app.ekhabeer.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryActivity extends BaseActivity implements Controller.APICallbackListener {

    RecyclerView recyclerView_cat;
    CategoryAdapter categoryAdapter;
    ArrayList<String> list;
    TextView tv_toolbar_title;
    String type,intent_cat_id;
    Controller mController;
    HashMap<String, String> hashMapMain;
    ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cagtegory);

        initView();
    }

    public void initView(){

        if (mController == null) {
            mController = new Controller(this);
        }
        hashMapMain = new HashMap<>();
        list = new ArrayList<>();
        recyclerView_cat = (RecyclerView) findViewById(R.id.recyclerView_cat);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        iv_back = (ImageView) findViewById(R.id.iv_back);
//        PutData();
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        intent_cat_id = intent.getStringExtra("cat_id");
        tv_toolbar_title.setText(type);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        GetSubCategories();
        recyclerView_cat.setHasFixedSize(true);
        recyclerView_cat.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    public void GetSubCategories(){

            hashMapMain.put(Constants.GET_SUB_CATEGORIES , "");
            hashMapMain.put(Constants.CAT_ID , intent_cat_id);
            Log.e("checkLoginFlow",">>" + hashMapMain);
            mController.SubCategoryAPI(hashMapMain,CategoryActivity.this);

    }

    public void PutData(){
        list.add("Addiction");
        list.add("Learning & Difficulties");
        list.add("Geriatric Psyhiatry");
        list.add("Pain Management");
        list.add("Children & Teenagers");
        list.add("Sleep Medicine");
        list.add("General Adivces");
    }

    @Override
    public void onFetchStart() {

        showLoader(CategoryActivity.this);
    }

    @Override
    public void onFetchProgress(JSONObject pojo) {

    }

    @Override
    public void onFetchProgress(ArrayList<HashMap<String, String>> pojoList, JSONArray jsonArrays) {
        Log.e("checkCateFlow",">>" + pojoList);
        categoryAdapter = new CategoryAdapter(type,pojoList,this);
        recyclerView_cat.setAdapter(categoryAdapter);

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